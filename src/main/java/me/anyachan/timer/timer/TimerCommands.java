package me.anyachan.timer.timer;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TimerCommands implements CommandExecutor {

    public static Timer timer;
    public static Bar bar;

    public TimerCommands() {
    }

    public void setVars(Timer plugin) {
        timer = plugin;
        bar=plugin.getBar();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (label.equalsIgnoreCase("timer")) {
            if (!(sender instanceof Player)) {
                return true;
            }

            Player player = (Player) sender;
            if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("start")) {
                    if (args.length == 1) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUsage: /timer start <time> <min/sec>"));
                        return true;
                    }
                    if (args.length == 2) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUsage: /timer start <time> <min/sec>"));
                        return true;
                    }
                    if (isNum(String.valueOf(args[1]))) {
                        if (args[2].equalsIgnoreCase("min") || args[2].equalsIgnoreCase("sec")) {

                            int time = Integer.parseInt(args[1]);
                            if (args[2].equalsIgnoreCase("min"))
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bSet a timer for &6" + time + "&b minutes!"));
                            else {
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bSet a timer for &6" + time + "&b seconds!"));
                            }
                            if (args[2].equalsIgnoreCase("min")) {
                                time = time * 60;
                            }

                            bar.setTime(time);
                            bar.createBar();
                            bar.addPlayer(player);
                            timer.setRunning(true);
                        } else {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUsage: /timer start <time> <min/sec>"));
                            return true;
                        }
                    } else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6" + args[1] + " is not a number!"));
                        return true;
                    }
                }
                else if (args[0].equalsIgnoreCase("stop")) {
                    if (timer.checkStatus()) {
                        timer.setRunning(false);
                        bar.cancelTimer();
                        bar.updateMap(player);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cCancelled counters!"));

                    }
                    else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cThere are no timers running at this time!"));
                        return true;
                    }

                }
                else if (args[0].equalsIgnoreCase("resume")) {

                    if (!timer.checkStatus()) {

                        boolean done = bar.resumeTimer();
                        if (done) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cResuming counters!"));
                            timer.setRunning(true);
                            return true;
                        }
                    }
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cThere are no stopped counters!"));
                }
            }
            else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUsage: /timer <start/stop/resume>"));
                return true;
            }

        }

        return false;
    }

    public boolean isNum(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
