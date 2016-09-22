package me.markediez.listkeeper;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mark Diez on 9/20/2016.
 */
public class ItemsAdapter extends ArrayAdapter<Item> {
    public ItemsAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
    }

    // TODO: This is the naive approach as indicated in the cliffnotes, what is the proper approach?
    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        // Get item from current position
        Item item = getItem(pos);

        // Check if we can recycle a previous view, else inflate a new view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_item, parent, false);
        }

        // Lookup view for data population
        TextView tvTask = (TextView) convertView.findViewById(R.id.tvTask);
        CheckBox cbTask = (CheckBox) convertView.findViewById(R.id.cbTask);

        // Populate data into view
        tvTask.setText(item.task);

        // If the item is done, strikethrough
        // The else part may seem redundant but without it the listview recycles the strikthrough
        if (item.done) {
            convertView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.taskComplete));
            tvTask.setPaintFlags(tvTask.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tvTask.setAlpha(0.5f);
            cbTask.setChecked(true);
        } else {
            convertView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.taskIncomplete));
            tvTask.setPaintFlags(tvTask.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
            tvTask.setAlpha(1.0f);
            cbTask.setChecked(false);
        }

        // Return for rendering
        return convertView;
    }
}
