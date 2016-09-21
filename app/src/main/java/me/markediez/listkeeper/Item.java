package me.markediez.listkeeper;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Mark Diez on 9/20/2016.
 */
public class Item {
    public long id;
    public boolean done;
    public String task;
    public String createdAt;
    public String updatedAt;

    public Item() {
        this.task = "";
        this.done = false;
        this.createdAt = getCurrentDate();
        this.updatedAt = getCurrentDate();
    }

    public Item(String task) {
        this();
        this.task = task;
    }

    public Item(String task, boolean done) {
        this();
        this.task = task;
        this.done = done;
    }

    // Get current time for updatedAt and createdAt
    public String getCurrentDate() {
        Date currDate = new Date();
        DateFormat df = new SimpleDateFormat("y-M-d HH:mm:ss.SSS");

        return df.format(currDate);
    }
}
