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
        gdTag.setColor(ContextCompat.getColor(convertView.getContext(), getTagColor(str)));

        return convertView;
    }

    public static int getTagColor(String priority) {
        int color = -1;
        if (priority.equalsIgnoreCase("Low Priority")) {
            color =  R.color.lowPriority;
        } else if (priority.equalsIgnoreCase("Normal Priority")) {
            color = R.color.normalPriority;
        } else {
            color = R.color.highPriority;
        }

        return color;
    }

    public static int getTagColor(int priority) {
        int color = -1;
        if (priority == 0) {
            color =  R.color.lowPriority;
        } else if (priority == 1) {
            color = R.color.normalPriority;
        } else {
            color = R.color.highPriority;
        }

        return color;
    }
}
