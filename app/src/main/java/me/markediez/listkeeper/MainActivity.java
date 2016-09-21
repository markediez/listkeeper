package me.markediez.listkeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListKeeperDatabaseHelper db;
    ArrayList<Item> items;
    ItemsAdapter itemsAdapter;
    ListView lvItems;
    // TODO: Switch ArrayList<String> to ArrayList<Item> and figure out how to make an adapter with custom class
    private final int REQUEST_CODE_EDIT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = ListKeeperDatabaseHelper.getInstance(getApplicationContext());
        lvItems = (ListView)findViewById(R.id.lvItems);
        readItems();
        itemsAdapter = new ItemsAdapter(this, items);

        lvItems.setAdapter(itemsAdapter);

        setupListViewListener();
    }

    public void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                db.deleteItem(items.get(pos));
                items.remove(pos);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                i.putExtra("itemToEdit", items.get(pos).task);
                i.putExtra("itemPosition", pos);
                startActivityForResult(i, REQUEST_CODE_EDIT);
            }
        });
    }

    private void readItems() {
        items = new ArrayList<Item>();
        for (Item i : db.getAllItems()) {
            items.add(i);
        }
    }

    // **********************************************************************
    // Functions related to when a subactivity returns data
    // **********************************************************************
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case REQUEST_CODE_EDIT:
                if (resultCode == RESULT_OK) {
                    items.get(data.getExtras().getInt("itemPosition")).task = data.getExtras().getString("editedItem");
                    db.updateItem(items.get(data.getExtras().getInt("itemPosition")));
                    itemsAdapter.notifyDataSetChanged();
                }

                break;
            default:;
        }
    }

    // **********************************************************************
    // Declarations for the following functions exist on the activity xml
    // **********************************************************************
    public void onAddItem(View v) {
        EditText etNewItem = (EditText)findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        Item newItem = new Item(itemText);
        long id = db.addItem(newItem);
        newItem.id = id;

        itemsAdapter.add(newItem);
        etNewItem.setText("");
    }
}


