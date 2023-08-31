package com.creatingreal.wallstreetmc.util;

import com.creatingreal.wallstreetmc.Core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Settings {
    
    public static String MARKET_HOURS_MODE = "ALWAYS_OPEN";
    public static long MARKET_START = 0L;
    public static long MARKET_END = 1000L;

    public static boolean ANNOUNCEMENT_OPEN_ENABLED = false;
    public static List<Long> ANNOUNCEMENT_OPEN_TICKSBEFOREOPEN = new ArrayList<>();
    public static List<String> ANNOUNCEMENT_OPEN_MARKETOPEN = new ArrayList<>();
    public static List<String> ANNOUNCEMENT_OPEN_ANNOUNCEMENTMESSAGE = new ArrayList<>();

    public static boolean ANNOUNCEMENT_CLOSE_ENABLED = false;
    public static List<Long> ANNOUNCEMENT_CLOSE_TICKSBEFORECLOSE = new ArrayList<>();
    public static List<String> ANNOUNCEMENT_CLOSE_MARKETCLOSE = new ArrayList<>();
    public static List<String> ANNOUNCEMENT_CLOSE_ANNOUNCEMENTMESSAGE = new ArrayList<>();
    
    public static void loadListLong(String path, List<Long> list){
        for(String key : Core.getInstance().getConfig().getStringList(path)){
            list.add(Long.parseLong(key));
        }
    }

    public static void loadListInt(String path, List<Integer> list){
        for(String key : Core.getInstance().getConfig().getStringList(path)){
            list.add(Integer.parseInt(key));
        }
    }
    
    public static void loadListString(String path, List<String> list){
        for(String key : Core.getInstance().getConfig().getStringList(path)){
            key = key.replace("&", "§");
            list.add(key);
        }
    }
    
    public static void loadSettings(){
        MARKET_HOURS_MODE = Core.getInstance().getConfig().getString("market.hours.mode");
        MARKET_START = Core.getInstance().getConfig().getLong("market.hours.custom-settings.start");
        MARKET_END = Core.getInstance().getConfig().getLong("market.hours.custom-settings.end");
        
        ANNOUNCEMENT_OPEN_ENABLED = Core.getInstance().getConfig().getBoolean("market.hours.announcements.open.enabled");
        loadListLong("market.hours.announcements.open.ticks-before-open", ANNOUNCEMENT_OPEN_TICKSBEFOREOPEN);
        loadListString("market.hours.announcements.open.open-message", ANNOUNCEMENT_OPEN_MARKETOPEN);
        loadListString("market.hours.announcements.open.announcement-message", ANNOUNCEMENT_OPEN_ANNOUNCEMENTMESSAGE);

        ANNOUNCEMENT_CLOSE_ENABLED = Core.getInstance().getConfig().getBoolean("market.hours.announcements.close.enabled");
        loadListLong("market.hours.announcements.close.ticks-before-close", ANNOUNCEMENT_CLOSE_TICKSBEFORECLOSE);
        loadListString("market.hours.announcements.close.close-message", ANNOUNCEMENT_CLOSE_MARKETCLOSE);
        loadListString("market.hours.announcements.close.announcement-message", ANNOUNCEMENT_CLOSE_ANNOUNCEMENTMESSAGE);
    }

}
