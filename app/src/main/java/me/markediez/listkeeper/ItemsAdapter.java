package me.markediez.listkeeper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

        // Populate data into view
        tvTask.setText(item.task);

        // Return for rendering
        return convertView;
    }
}
