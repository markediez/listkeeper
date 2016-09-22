package me.markediez.listkeeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// TODO: Add priority functionality
public class EditItemActivity extends AppCompatActivity {
    EditText item;
    Spinner spPriority;
    List<String> priorities;
    PriorityAdapter priorityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        item = (EditText) findViewById(R.id.etItem);
        item.setText(getIntent().getStringExtra("itemToEdit"));
        setupSpinner();
        spPriority.setSelection(getIntent().getIntExtra("itemPriority", 1));
    }

    public void setupSpinner() {
        spPriority = (Spinner) findViewById(R.id.spPriority);
        priorities = Arrays.asList(getResources().getStringArray(R.array.priority_array));
        priorityAdapter = new PriorityAdapter(getApplicationContext(), priorities);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_list_item_2);

        spPriority.setAdapter(priorityAdapter);
    }

    public void onSave(View v) {
        Intent data = new Intent();
        data.putExtra("editedItem", item.getText().toString());
        data.putExtra("itemPosition", getIntent().getIntExtra("itemPosition", -1));
        data.putExtra("itemPriority", spPriority.getSelectedItemPosition());
        data.putExtra("delete", false);

        setResult(RESULT_OK, data);
        finish();
    }

    public void onDelete(View v) {
        Intent data = new Intent();
        data.putExtra("itemPosition", getIntent().getIntExtra("itemPosition", -1));
        data.putExtra("delete", true);

        setResult(RESULT_OK, data);
        finish();
    }
}
