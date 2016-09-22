package me.markediez.listkeeper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Mark Diez on 9/21/2016.
 */

public class PriorityAdapter extends ArrayAdapter<String> {
    public PriorityAdapter(Context context, List<String> priorities) {
        super(context, 0, priorities);
    }

    public View getView(int pos, View convertView, ViewGroup parent) {
        return initView(pos, convertView, parent);
    }

    @Override
    public View getDropDownView(int pos, View convertView, ViewGroup parent) {
        return initView(pos, convertView, parent);
    }

    private View initView(int pos, View convertView, ViewGroup parent) {
        String str = getItem(pos);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_priority, parent, false);
        }

        TextView tvPriority = (TextView) convertView.findViewById(R.id.tvPriority);
        tvPriority.setText(str);

        return convertView;
    }
}
