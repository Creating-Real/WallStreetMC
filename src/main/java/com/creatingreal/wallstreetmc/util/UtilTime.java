package com.creatingreal.wallstreetmc.util;

public class UtilTime {

    public static String getMinutesSecondsFromLong(long time){
        // were given ticks so multiply by 20 to get seconds
        long seconds = time / 20;
        // convert seconds to minutes
        long minutes = seconds / 60;
        // get the remainder seconds
        long remainderSeconds = seconds % 60;
        // return the formatted string
        String minutesFormatted = "";
        if(minutes < 10){
            minutesFormatted = "0" + minutes;
        } else {
            minutesFormatted = "" + minutes;
        }

        String secondsFormatted = "";
        if(remainderSeconds < 10){
            secondsFormatted = "0" + remainderSeconds;
        } else {
            secondsFormatted = "" + remainderSeconds;
        }

        return minutesFormatted + "m" + secondsFormatted + "s";
    }

}
