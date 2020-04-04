package net.whispwriting.autobroadcaster;

import net.whispwriting.autobroadcaster.commands.AuoBroadcastCommand;
import net.whispwriting.autobroadcaster.events.AutoBroadcasterTask;
import net.whispwriting.autobroadcaster.files.BroadcastMessages;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.Timer;

public final class Autobroadcaster extends JavaPlugin {

    private BroadcastMessages messages = new BroadcastMessages((this));
    private AutoBroadcasterTask task;
    private Timer timer;

    @Override
    public void onEnable() {
        messages.createConfig();
        messages.get().options().copyDefaults(true);
        messages.save();
        if (messages.get().getBoolean("enabled")){
            task = new AutoBroadcasterTask(messages, 0);
            Bukkit.getScheduler().runTaskTimer(this, task, 5, messages.get().getInt("interval") * 20 * 60);
        }
        this.getCommand("autobroadcaster").setExecutor(new AuoBroadcastCommand(messages, task, this));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
