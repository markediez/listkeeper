package me.markediez.listkeeper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Mark Diez on 9/21/2016.
 */

public class PriorityAdapter extends ArrayAdapter<String> {
    private ShapeDrawable tag;

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

        ImageView ivTag = (ImageView)convertView.findViewById(R.id.ivTag);
        GradientDrawable gdTag = (GradientDrawable) ivTag.getDrawable();

        // TODO: Clean up. Maybe get the list of colors in an array?
        if (str.equalsIgnoreCase("Low Priority")) {
            gdTag.setColor(ContextCompat.getColor(convertView.getContext(), R.color.lowPriority));
        } else if (str.equalsIgnoreCase("Normal Priority")) {
            gdTag.setColor(ContextCompat.getColor(convertView.getContext(), R.color.normalPriority));
        } else {
            gdTag.setColor(ContextCompat.getColor(convertView.getContext(), R.color.highPriority));
        }

        return convertView;
    }
}
