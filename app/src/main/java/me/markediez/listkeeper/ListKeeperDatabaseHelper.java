package me.markediez.listkeeper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mark Diez on 9/20/2016.
 */
public class ListKeeperDatabaseHelper extends SQLiteOpenHelper {
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
        db.setForeignKeyConstraintsEnabled(true);
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
    // 2 invocation of synchronized methods on the same object cannot interleave(?)
    // (Basically) async = false between threads executing synchronized methods in the same object block
    // Establishes a "happens-before" relationship
    public static synchronized ListKeeperDatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ListKeeperDatabaseHelper(context.getApplicationContext());
        }

        return sInstance;
    }
}
