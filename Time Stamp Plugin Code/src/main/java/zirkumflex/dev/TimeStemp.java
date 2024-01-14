package zirkumflex.dev;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class TimeStemp extends JavaPlugin {

    private static TimeStemp instance;

    @Override
    public void onEnable() {
        getLogger().info("Zirkumflex_TimeStemp Plugin enabled!");
        instance = this;
        displayPlayerTime();
    }

    @Override
    public void onDisable() {
        getLogger().info("Zirkumflex_TimeStemp Plugin disabled!");
    }

    private void displayPlayerTime() {

        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§6§l"+shortInteger(player.getStatistic(Statistic.TOTAL_WORLD_TIME)/20)));
                });
            }
        };
        runnable.runTaskTimer(TimeStemp.getPlugin(TimeStemp.class), 0,20);
    }

    private String shortInteger(int duration) {

        String string = "";

        int days = 0;
        int hours = 0;
        int minutes = 0;
        int seconds = 0;

        if (duration / 60 / 60 >= 1) {
            hours = duration / 60 / 60;
            duration = duration - ((duration / 60 / 60) * 60 * 60);
        }

        if (duration / 60 >= 1) {
            minutes = duration / 60;
            duration = duration - ((duration / 60) * 60);
        }

        if (duration >= 1) {
            seconds = duration;
        }

        while (hours >= 24) {
            days++;
            hours = hours - 24;
        }

        string = days + "d ";

        if (hours <= 9)
            string = string + "0" + hours + "h ";
        else
            string = string + hours + "h ";

        if (minutes <= 9)
            string = string + "0" + minutes + "m ";
        else
            string = string + minutes + "m ";

        if (seconds <= 9)
            string = string + "0" + seconds + "s";
        else
            string = string + seconds + "s";

        return string;
    }
}
