package me.markediez.listkeeper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Mark Diez on 9/20/2016.
 */
public class Item {
    public int id;
    public String task;
    public String createdAt;
    public String updatedAt;

    public Item() {
        this.task = "";
        this.createdAt = getCurrentDate();
        this.updatedAt = getCurrentDate();
    }

    // Get current time for updatedAt and createdAt
    public String getCurrentDate() {
        Date currDate = new Date();
        DateFormat df = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS.SSS");

        return df.format(currDate);
    }
}
