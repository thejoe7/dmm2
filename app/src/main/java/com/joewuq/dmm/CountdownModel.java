package com.joewuq.dmm;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.UUID;

/**
 * Created by Joe Wu on 1/3/15.
 */
public class CountdownModel {
    public static final String TAG = CountdownModel.class.getName();

    public enum RepeatMode {
        @SerializedName("0")
        NONE,
        @SerializedName("1")
        MONTHLY,
        @SerializedName("2")
        YEARLY,
        @SerializedName("3")
        CUSTOM
    }

    public enum ThemeColor {
        @SerializedName("0")
        RED,
        @SerializedName("1")
        YELLOW,
        @SerializedName("2")
        GREEN,
        @SerializedName("3")
        BLUE,
        @SerializedName("4")
        PURPLE,
        @SerializedName("5")
        CUSTOM
    }

    private String uuid;
    private String title;
    private String description;
    private ThemeColor themeColor;
    private DateTime date;
    private RepeatMode repeatMode;
    private int repeatInterval;

    public CountdownModel() {
        this.uuid = UUID.randomUUID().toString();
        this.title = "";
        this.description = "";
        this.themeColor = ThemeColor.RED;
        this.date = DateTime.now().withTime(0, 0, 0, 0);
        this.repeatMode = RepeatMode.NONE;
        this.repeatInterval = -1;
    }

    public String getUuid() {
        return uuid;
    }

    public String getTitle() {
        return title;
    }

    public CountdownModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CountdownModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public ThemeColor getThemeColor() {
        return themeColor;
    }

    public CountdownModel setThemeColor(ThemeColor themeColor) {
        this.themeColor = themeColor;
        return this;
    }

    public DateTime getDate() {
        return date;
    }

    public CountdownModel setDate(DateTime date) {
        this.date = date;
        return this;
    }

    public RepeatMode getRepeatMode() {
        return repeatMode;
    }

    public int getRepeatInterval() {
        return repeatInterval;
    }

    public CountdownModel setRepeatMode(RepeatMode repeatMode) {
        return setRepeatMode(repeatMode, -1);
    }

    public CountdownModel setRepeatMode(RepeatMode repeatMode, int repeatInterval) {
        this.repeatMode = repeatMode;
        this.repeatInterval = repeatInterval;
        return this;
    }

    public DateTime getNextDate() {
        if (repeatMode == RepeatMode.NONE) {
            return date;
        } else {
            DateTime nextDate;
            DateTime today = DateTime.now().withTime(0, 0, 0, 0);
            if (repeatMode == RepeatMode.MONTHLY) {
                nextDate = date.withYear(today.getYear()).withMonthOfYear(today.getMonthOfYear());
                if (!isValidCountdownTarget(today, nextDate)) {
                    nextDate = nextDate.plusMonths(1);
                }
            } else if (repeatMode == RepeatMode.YEARLY) {
                nextDate = date.withYear(today.getYear());
                if (!isValidCountdownTarget(today, nextDate)) {
                    nextDate = nextDate.plusYears(1);
                }
            } else {
                if (repeatInterval <= 0) {
                    Log.w(TAG, String.format("getNextDate() with invalid repeatInterval (%d)", repeatInterval));
                    nextDate = date;
                } else if (isValidCountdownTarget(today, date)) {
                    nextDate = date;
                } else {
                    int diff = Days.daysBetween(date, today).getDays();
                    nextDate = today.plusDays(repeatInterval - diff % repeatInterval);
                }
            }
            return nextDate;
        }
    }

    // if we need to generate a new countdown target
    private boolean isValidCountdownTarget(DateTime d, DateTime target) {
        // on the exact day, use the next repeated date as the new target
        return d.isBefore(target);
    }

    public int getDaysDiff(DateTime d) {
        return getDaysDiff(d, null);
    }

    public int getDaysDiff(DateTime d, DateTime target) {
        if (target == null) {
            target = getNextDate();
        }
        return Days.daysBetween(d.withTime(0, 0, 0, 0), target).getDays();
    }

    public boolean isPast() {
        return getDaysDiff(DateTime.now().withTime(0, 0, 0, 0)) < 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof CountdownModel)) return false;
        if (o == this) return true;
        CountdownModel countdownModel = (CountdownModel) o;
        return this.uuid.equals(countdownModel.uuid);
    }
}
