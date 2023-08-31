package com.creatingreal.wallstreetmc.tasks;

import com.creatingreal.wallstreetmc.Core;
import com.creatingreal.wallstreetmc.util.Settings;
import com.creatingreal.wallstreetmc.util.UtilTime;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class HoursTimer extends BukkitRunnable {
    
    @Override
    public void run() {

        long marketOpenTicks = Settings.MARKET_START;
        long marketCloseTicks = Settings.MARKET_END;

        long currentTimeTicks = Core.getInstance().getServer().getWorld("world").getTime();

        for(int i = 0; i < Settings.ANNOUNCEMENT_OPEN_TICKSBEFOREOPEN.size(); i++){

            if(marketOpenTicks - currentTimeTicks <= 0 && marketOpenTicks - Settings.ANNOUNCEMENT_OPEN_TICKSBEFOREOPEN.get(i) <= 0 && !Core.getInstance().isMarketOpen){
                long difference = Settings.ANNOUNCEMENT_OPEN_TICKSBEFOREOPEN.get(i) - marketOpenTicks;

                if(24000 - difference == currentTimeTicks){
                    for (String msg : Settings.ANNOUNCEMENT_OPEN_ANNOUNCEMENTMESSAGE) {
                        Bukkit.broadcastMessage("" + msg.replace("&", "§").replace("{time}", UtilTime.getMinutesSecondsFromLong(Settings.ANNOUNCEMENT_OPEN_TICKSBEFOREOPEN.get(i))));
                    }
                }
            }else if(marketOpenTicks - currentTimeTicks > 0 && marketOpenTicks - Settings.ANNOUNCEMENT_OPEN_TICKSBEFOREOPEN.get(i) > 0 && !Core.getInstance().isMarketOpen){

                long difference = Math.abs(marketOpenTicks - currentTimeTicks);

                if(difference == Settings.ANNOUNCEMENT_OPEN_TICKSBEFOREOPEN.get(i)) {
//                    Bukkit.broadcastMessage("Normal World Time: " + currentTimeTicks);
//                    Bukkit.broadcastMessage("Normal Market Open Time: " + marketOpenTicks);
//                    Bukkit.broadcastMessage("Normal Difference: " + Math.abs(marketOpenTicks - currentTimeTicks));
                    for (String msg : Settings.ANNOUNCEMENT_OPEN_ANNOUNCEMENTMESSAGE) {
                        Bukkit.broadcastMessage("" + msg.replace("&", "§").replace("{time}", UtilTime.getMinutesSecondsFromLong(difference)));

                    }
                }

            }
            else if(marketOpenTicks == currentTimeTicks && !Core.getInstance().isMarketOpen){
                Core.getInstance().isMarketOpen = true;
                for(String msg : Settings.ANNOUNCEMENT_OPEN_MARKETOPEN){
                    Bukkit.broadcastMessage(msg.replace("&", "§"));
                }
            }
            else if(Settings.ANNOUNCEMENT_OPEN_TICKSBEFOREOPEN.get(i) - marketOpenTicks == 0 && currentTimeTicks == 0 && !Core.getInstance().isMarketOpen){
                for (String msg : Settings.ANNOUNCEMENT_OPEN_ANNOUNCEMENTMESSAGE) {
                    Bukkit.broadcastMessage("" + msg.replace("&", "§").replace("{time}", UtilTime.getMinutesSecondsFromLong(Settings.ANNOUNCEMENT_OPEN_TICKSBEFOREOPEN.get(i))));
                }
            }

        }

        for(int i = 0; i < Settings.ANNOUNCEMENT_CLOSE_TICKSBEFORECLOSE.size(); i++) {

            if (marketCloseTicks - currentTimeTicks <= 0 && marketCloseTicks - Settings.ANNOUNCEMENT_CLOSE_TICKSBEFORECLOSE.get(i) <= 0 && Core.getInstance().isMarketOpen) {
                long difference = Settings.ANNOUNCEMENT_CLOSE_TICKSBEFORECLOSE.get(i) - marketCloseTicks;

                if (24000 - difference == currentTimeTicks) {
                    for (String msg : Settings.ANNOUNCEMENT_CLOSE_ANNOUNCEMENTMESSAGE) {
                        Bukkit.broadcastMessage("" + msg.replace("&", "§").replace("{time}", UtilTime.getMinutesSecondsFromLong(Settings.ANNOUNCEMENT_CLOSE_TICKSBEFORECLOSE.get(i))));
                    }
                }
            } else if (marketCloseTicks - currentTimeTicks > 0 && marketCloseTicks - Settings.ANNOUNCEMENT_CLOSE_TICKSBEFORECLOSE.get(i) > 0 && Core.getInstance().isMarketOpen) {

                long difference = Math.abs(marketCloseTicks - currentTimeTicks);

                if (difference == Settings.ANNOUNCEMENT_CLOSE_TICKSBEFORECLOSE.get(i)) {
//                    Bukkit.broadcastMessage("Normal World Time: " + currentTimeTicks);
//                    Bukkit.broadcastMessage("Normal Market Close Time: " + marketCloseTicks);
//                    Bukkit.broadcastMessage("Normal Difference: " + Math.abs(marketCloseTicks - currentTimeTicks));
                    for (String msg : Settings.ANNOUNCEMENT_CLOSE_ANNOUNCEMENTMESSAGE) {
                        Bukkit.broadcastMessage("" + msg.replace("&", "§").replace("{time}", UtilTime.getMinutesSecondsFromLong(difference)));
                    }
                }
            } else if (marketCloseTicks == currentTimeTicks && Core.getInstance().isMarketOpen) {
                Core.getInstance().isMarketOpen = false;
                for (String msg : Settings.ANNOUNCEMENT_CLOSE_MARKETCLOSE) {
                    Bukkit.broadcastMessage(msg.replace("&", "§"));
                }
            } else if (Settings.ANNOUNCEMENT_CLOSE_TICKSBEFORECLOSE.get(i) - marketCloseTicks == 0 && currentTimeTicks == 0 && Core.getInstance().isMarketOpen) {
                for (String msg : Settings.ANNOUNCEMENT_CLOSE_ANNOUNCEMENTMESSAGE) {
                    Bukkit.broadcastMessage("" + msg.replace("&", "§").replace("{time}", UtilTime.getMinutesSecondsFromLong(Settings.ANNOUNCEMENT_CLOSE_TICKSBEFORECLOSE.get(i))));
                }
            }
        }
        
    }
    
}
