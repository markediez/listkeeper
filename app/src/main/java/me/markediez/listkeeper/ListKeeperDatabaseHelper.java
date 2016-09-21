package me.markediez.listkeeper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark Diez on 9/20/2016.
 */
public class ListKeeperDatabaseHelper extends SQLiteOpenHelper {
    // Debugging
    private static String TAG = "ListKeeperDatabaseHelper";

    // Singleton Instance
    private static ListKeeperDatabaseHelper sInstance;

    // Database Info
    private static final String DATABASE_NAME = "listKeeperDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_ITEMS = "items";

    // Items Table Columns
    private static final String KEY_ITEM_ID = "id";
    private static final String KEY_ITEM_TASK = "task";
    private static final String KEY_ITEM_CREATED_AT = "createdAt";
    private static final String KEY_ITEM_UPDATED_AT = "updatedAt";


    private ListKeeperDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when db is being configured
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true); // TODO: What does this do?
    }

    // Called when database is created for the first time
    // Must be called explicitly on upgrade
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_ITEMS +
                "(" +
                    KEY_ITEM_ID + " INTEGER PRIMARY KEY," +
                    KEY_ITEM_TASK + " TEXT," +
                    KEY_ITEM_CREATED_AT + " TEXT," +
                    KEY_ITEM_UPDATED_AT + " TEXT" +
                ")";

        db.execSQL(CREATE_ITEMS_TABLE);
    }

    // Called when db needs to be upgraded
    // Hence, when a db with the same name exists on the disk with a different version number
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);

            onCreate(db);
        }
    }

    // Synchronized makes sure that:
    // 2 invocation of synchronized methods on the same object cannot interleave(?) (TODO: What does it mean by interleave?)
    // (Basically) async = false between threads executing synchronized methods in the same object block
    // Establishes a "happens-before" relationship
    public static synchronized ListKeeperDatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ListKeeperDatabaseHelper(context.getApplicationContext());
        }

        return sInstance;
    }

    /* CRUD Operations */

    // Create (and insert) an item to the database
    public long addItem(Item item) {
        // Create or open database
        SQLiteDatabase db = getWritableDatabase(); // TODO: Why don't we call our helper?

        // ID of new item
        long id = -1;

        // We use a transaction in-case the sql statement fails
        // If it fails at any point (even after other execution), no change persists unless we
        // end the transaction successfully
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_ITEM_TASK, item.task);
            values.put(KEY_ITEM_CREATED_AT, item.createdAt);
            values.put(KEY_ITEM_UPDATED_AT, item.updatedAt);

            id = db.insert(TABLE_ITEMS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add an item to database");
        } finally {
            db.endTransaction();
        }

        return id;
    }

    // Reading all items from the database
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String ITEMS_SELECT_QUERY = String.format("SELECT * FROM %s", TABLE_ITEMS);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(ITEMS_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    Item newItem = new Item();
                    newItem.id = cursor.getInt(cursor.getColumnIndex(KEY_ITEM_ID));
                    newItem.task = cursor.getString(cursor.getColumnIndex(KEY_ITEM_TASK));
                    newItem.createdAt = cursor.getString(cursor.getColumnIndex(KEY_ITEM_CREATED_AT));
                    newItem.updatedAt = cursor.getString(cursor.getColumnIndex(KEY_ITEM_UPDATED_AT));

                    items.add(newItem);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get items from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return items;
    }

    // Updating an item's task
    public int updateItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ITEM_TASK, item.task);
        values.put(KEY_ITEM_UPDATED_AT, item.getCurrentDate());

        return db.update(TABLE_ITEMS, values, KEY_ITEM_ID + " = ?", new String[] {Long.toString(item.id)});
    }

    // Delete all items
    public void deleteAllItems() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_ITEMS, null, null);
            db.setTransactionSuccessful(); // TODO: Why?
        } catch(Exception e) {
            Log.d(TAG, "Error while trying to delete all items");
        } finally {
            db.endTransaction();
        }
    }

    // Delete one item
    public void deleteItem(Item item) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_ITEMS, KEY_ITEM_ID + " = ?", new String[] {Long.toString(item.id)});
        } catch(Exception e) {
            Log.d(TAG, "Error while trying to delete an item");
        } finally {
            db.endTransaction();
        }
    }
}
