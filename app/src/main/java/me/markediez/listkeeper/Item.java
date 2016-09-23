package me.markediez.listkeeper;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Mark Diez on 9/20/2016.
 */
public class Item {
    public long id;
    public int priority;
    public boolean done;
    public String task;
    public String dueDate;
    public String createdAt;
    public String updatedAt;

    public Item() {
        this.task = "";
        this.priority = 1;
        this.done = false;
        this.dueDate = "";
        this.createdAt = formatDate(new Date());
        this.updatedAt = formatDate(new Date());
    }

    public Item(String task) {
        this();
        this.task = task;
    }

    public String formatDate(Date date) {
        DateFormat df = getDateFormat();

        return df.format(date);
    }

    public static DateFormat getDateFormat() {
        return new SimpleDateFormat("y-M-d HH:mm:ss.SSS");
    }

    public String getReadableDueDate() {
        String prettyString = "";
        try {
            DateFormat df = getDateFormat();
            Date d = df.parse(this.dueDate);

            df = new SimpleDateFormat("dd MMMM yyyy");
            prettyString = df.format(d);
        } catch (ParseException e) {
            e.getStackTrace();
        }

        return prettyString;
    }
}
