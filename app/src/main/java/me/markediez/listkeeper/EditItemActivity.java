package me.markediez.listkeeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

// TODO: Add priority functionality
public class EditItemActivity extends AppCompatActivity {
    EditText item;
    Spinner spPriority;
    List<String> priorities;
    PriorityAdapter priorityAdapter;
    DatePicker dpDueDate;
    CheckBox cbDueDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        item = (EditText) findViewById(R.id.etItem);
        item.setText(getIntent().getStringExtra("itemToEdit"));
        setupSpinner();
        setupDueDate(getIntent().getStringExtra("itemDueDate"));
        spPriority.setSelection(getIntent().getIntExtra("itemPriority", 1));
    }

    public void setupDueDate(String dueDate) {
        dpDueDate = (DatePicker)findViewById(R.id.dpDueDate);
        cbDueDate = (CheckBox) findViewById(R.id.cbDueDate);


        if (!dueDate.equals("")) {
            // Work around because it does not work with .setChecked(true) alone
            // https://www.bountysource.com/issues/26094723-checkbox-setchecked-not-working
            cbDueDate.post(new Runnable() {
                @Override
                public void run() {
                    cbDueDate.setChecked(true);
                }
            });

            try {
                DateFormat df = Item.getDateFormat();
                Date date = df.parse(dueDate);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);

                dpDueDate.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            dpDueDate.setVisibility(View.INVISIBLE);
            cbDueDate.setChecked(false);
        }
    }

    public void setupSpinner() {
        spPriority = (Spinner) findViewById(R.id.spPriority);
        priorities = Arrays.asList(getResources().getStringArray(R.array.priority_array));
        priorityAdapter = new PriorityAdapter(getApplicationContext(), priorities);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_list_item_2);

        spPriority.setAdapter(priorityAdapter);
    }

    public void toggleDueDate(View v) {
        if (cbDueDate.isChecked()) {
            // due date will be enabled
            dpDueDate.setVisibility(View.VISIBLE);
        } else {
            // due date will be disabled
            dpDueDate.setVisibility(View.INVISIBLE);
        }
    }

    public void onSave(View v) {
        // yyyy-MM-dd HH:mm:SS.sss format
        // +1 on month is necessary to get proper month
        String newDueDate = "";
        if(cbDueDate.isChecked()) {
            newDueDate = dpDueDate.getYear() + "-" + (dpDueDate.getMonth() + 1) + "-" + dpDueDate.getDayOfMonth() + " 00:00:00.000";
        }

        Intent data = new Intent();
        data.putExtra("editedItem", item.getText().toString());
        data.putExtra("itemPosition", getIntent().getIntExtra("itemPosition", -1));
        data.putExtra("itemPriority", spPriority.getSelectedItemPosition());
        data.putExtra("delete", false);
        data.putExtra("itemDueDate", newDueDate);

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
