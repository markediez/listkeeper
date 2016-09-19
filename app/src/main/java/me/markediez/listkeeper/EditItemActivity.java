package me.markediez.listkeeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    EditText item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        item = (EditText) findViewById(R.id.etItem);
        item.setText(getIntent().getStringExtra("itemToEdit"));
    }

    public void onSave(View v) {
        Intent data = new Intent();
        data.putExtra("editedItem", item.getText().toString());
        data.putExtra("itemPosition", getIntent().getIntExtra("itemPosition", -1));
        setResult(RESULT_OK, data);
        finish();
    }
}
