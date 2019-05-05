package net.whispwriting.autobroadcaster;

import net.whispwriting.autobroadcaster.commands.AuoBroadcastCommand;
import net.whispwriting.autobroadcaster.events.AutoBroadcasterTask;
import net.whispwriting.autobroadcaster.files.BroadcastMessages;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class Autobroadcaster extends JavaPlugin {

    private BroadcastMessages messages = new BroadcastMessages((this));
    private BukkitTask task;

    @Override
    public void onEnable() {
        messages.createConfig();
        messages.get().options().copyDefaults(true);
        messages.save();
        if (messages.get().getBoolean("enabled")){
            task = new AutoBroadcasterTask(messages, 0).runTaskTimer(this, 0, (messages.get().getInt("interval")*1200));
        }
        this.getCommand("autobroadcaster").setExecutor(new AuoBroadcastCommand(messages, task, this));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
