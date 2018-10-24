package org.selflearning.Clip;

import java.time.LocalDateTime;

public class Alarm {
    private int hour;
    private int minute;

    private int currentHour;
    private int currentMinute;

    public Alarm() {}

    public Alarm(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public void setAlarm(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public boolean triggerAlarm(int hour, int minute) {
        currentHour = LocalDateTime.now().getHour();
        currentMinute = LocalDateTime.now().getMinute();



        if (hour == currentHour && minute == currentMinute) {
            return true;
        }

        return false;
    }
}
