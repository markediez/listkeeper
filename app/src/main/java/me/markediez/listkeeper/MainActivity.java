package me.markediez.listkeeper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListKeeperDatabaseHelper db;
    ArrayList<Item> items;
    ItemsAdapter itemsAdapter;
    ListView lvItems;

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
        // Edit
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                i.putExtra("itemToEdit", items.get(pos).task);
                i.putExtra("itemPosition", pos);
                i.putExtra("itemPriority", items.get(pos).priority);
                i.putExtra("itemDueDate", items.get(pos).dueDate);
                startActivityForResult(i, REQUEST_CODE_EDIT);

                return true;
            }
        });

        // Mark as done
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                TextView task = (TextView)view.findViewById(R.id.tvTask);
                TextView dueDate = (TextView) view.findViewById(R.id.tvDueDate);
                CheckBox cbTask = (CheckBox)view.findViewById(R.id.cbTask);
                ImageView ivTag = (ImageView)view.findViewById(R.id.ivTag);
                RelativeLayout container = (RelativeLayout) view.findViewById(R.id.llListView);

                // TODO: Not quite sure how:
                // ~ Paint.STRIKE_THRU_TEXT_FLAG works to removee strike through
                // a bitwise operator is valid in an argument that takes an int
                // http://stackoverflow.com/questions/18881817/removing-strikethrough-from-textview
                if (task.getPaintFlags() == (task.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG)) {
                    items.get(pos).done = false;
                } else {
                    // Set as done
                    items.get(pos).done = true;
                }

                toggleTask(task, dueDate, cbTask, ivTag, pos, container, view.getContext());
                db.updateItem(items.get(pos));
            }
        });
    }

    private void toggleTask(TextView task, TextView dueDate, CheckBox cbTask, ImageView ivTag, int position,  RelativeLayout container, Context context) {
        if (task.getPaintFlags() == (task.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG)) {
            task.setPaintFlags(task.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
            task.setAlpha(1.0f);
            dueDate.setAlpha(1.0f);
            ivTag.setAlpha(1.0f);
            ivTag.setBackgroundColor(ContextCompat.getColor(context, PriorityAdapter.getTagColor(items.get(position).priority)));

            container.setBackgroundColor(ContextCompat.getColor(context, R.color.taskIncomplete));
            cbTask.setChecked(false);
        } else {
            // Set as done
            task.setPaintFlags(task.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            task.setAlpha(0.5f);
            dueDate.setAlpha(0.5f);
            ivTag.setAlpha(0.9f);
            ivTag.setBackgroundColor(ContextCompat.getColor(context, R.color.checkbox));
            container.setBackgroundColor(ContextCompat.getColor(context, R.color.taskComplete));
            cbTask.setChecked(true);
        }
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
                    int position = data.getExtras().getInt("itemPosition");

                    if (data.getExtras().getBoolean("delete")) {
                        db.deleteItem(items.get(position));
                        items.remove(position);
                        itemsAdapter.notifyDataSetChanged();
                    } else {
                        items.get(position).task = data.getExtras().getString("editedItem");
                        items.get(position).priority = data.getExtras().getInt("itemPriority");
                        items.get(position).dueDate = data.getExtras().getString("itemDueDate");
                        db.updateItem(items.get(data.getExtras().getInt("itemPosition")));
                        itemsAdapter.notifyDataSetChanged();
                    }
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


