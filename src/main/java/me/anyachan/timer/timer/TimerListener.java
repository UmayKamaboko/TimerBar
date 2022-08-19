package me.anyachan.timer.timer;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class TimerListener implements Listener {

    public static Timer timer;
    public static Bar bar;

    public TimerListener() {
    }

    public void setVars(Timer plugin) {
        timer = plugin;
        bar=plugin.getBar();
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!bar.getHashMap().containsKey(event.getPlayer().getUniqueId().toString())) {
            return;
        }
        if (timer.checkStatus())
            return;
        boolean done = bar.resumeTimer();
        if (done) {
            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cResuming counters since you are back!"));
            timer.setRunning(true);

        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        if (!bar.getHashMap().containsKey(event.getPlayer().getUniqueId().toString())) {
            return;
        }
        if (!timer.checkStatus())
            return;

        timer.setRunning(false);
        bar.cancelTimer();
        bar.updateMap(event.getPlayer());

    }





}
