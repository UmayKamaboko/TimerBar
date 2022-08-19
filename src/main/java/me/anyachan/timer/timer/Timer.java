package me.anyachan.timer.timer;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;

public final class Timer extends JavaPlugin implements Listener {



    public Bar bar = new Bar(this);
    public boolean isRunning = false;
    TimerListener timerListener = new TimerListener();
    TimerCommands timerCommands = new TimerCommands();

    @Override
    public void onEnable() {
        setVars();
        this.getServer().getPluginManager().registerEvents(new TimerListener(),this);
        this.getCommand("timer").setExecutor(new TimerCommands());
        this.saveDefaultConfig();
        if (this.getConfig().contains("data")) {
            this.restoreCounters();
            this.getConfig().set("data", null);
            this.saveConfig();
        }
    }


    @Override
    public void onDisable() {
        if(!bar.getHashMap().isEmpty()) {
            saveCounters();
        }
    }

    public boolean checkStatus() {
        return isRunning;
    }


    public Bar getBar() {
        return bar;
    }

    public void setVars() {
        timerCommands.setVars(this);
        timerListener.setVars(this);
    }

    public void setRunning(boolean run) {
        isRunning = run;
    }

    public void saveCounters() {
        for (Map.Entry<String, List<Float>> entry : bar.getHashMap().entrySet()) {
            this.getConfig().set("data."+ entry.getKey(), entry.getValue());
        }
        this.saveConfig();
    }

    public void restoreCounters() {
        if (this.getConfig().contains("data")) {

            this.getConfig().getConfigurationSection("data").getKeys(false).forEach(key -> {
                List<Float> counterList = this.getConfig().getFloatList("data."+ key);
                bar.getHashMap().put(key,counterList);
                bar.createBar();
            });


        }
    }
}

