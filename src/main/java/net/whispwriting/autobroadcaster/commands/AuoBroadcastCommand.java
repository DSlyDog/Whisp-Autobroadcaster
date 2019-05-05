package net.whispwriting.autobroadcaster.commands;

import net.whispwriting.autobroadcaster.Autobroadcaster;
import net.whispwriting.autobroadcaster.events.AutoBroadcasterTask;
import net.whispwriting.autobroadcaster.files.BroadcastMessages;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitTask;

import java.util.InputMismatchException;
import java.util.List;
import java.util.TimerTask;

public class AuoBroadcastCommand implements CommandExecutor {

    private BroadcastMessages messages;
    private BukkitTask task;
    private Autobroadcaster plugin;

    public AuoBroadcastCommand(BroadcastMessages msg, BukkitTask t, Autobroadcaster pl){
        messages = msg;
        task = t;
        plugin = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (sender.hasPermission("WhispPlugin.autobroadcaster")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("on")) {
                    if (!messages.get().getBoolean("enabled")) {
                        messages.reload();
                        messages.get().set("enabled", true);
                        messages.save();
                        messages.reload();
                        task = new AutoBroadcasterTask(messages, 0).runTaskTimer(plugin, 0, (messages.get().getInt("interval")*100));
                        sender.sendMessage(ChatColor.GREEN+"Autobroadcasts are now enabled.");
                    } else {
                        sender.sendMessage(ChatColor.AQUA + "AutoBroadcaster is already on.");
                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("off")) {
                    if (messages.get().getBoolean("enabled")) {
                        task.cancel();
                        messages.reload();
                        messages.get().set("enabled", false);
                        messages.save();
                        messages.reload();
                        sender.sendMessage(ChatColor.RED + "AutoBroadcasts are now disabled.");
                    } else {
                        sender.sendMessage(ChatColor.AQUA + "AutoBroadcaster is already off.");
                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("add")) {
                    sender.sendMessage(ChatColor.DARK_AQUA + "/autobroadcaster " + ChatColor.AQUA + "add <message>");
                    return true;
                }else if (args[0].equalsIgnoreCase("remove")) {
                    sender.sendMessage(ChatColor.DARK_AQUA + "/autobroadcaster " + ChatColor.AQUA + "remove <message number>");
                    return true;
                }else if (args[0].equalsIgnoreCase("interval")) {
                    sender.sendMessage(ChatColor.DARK_AQUA + "/autobroadcaster " + ChatColor.AQUA + "interval <minutes>");
                    return true;
                }
            } else if (args.length >= 2){
                if (args[0].equalsIgnoreCase("add")){
                    String messageToAdd = "";
                    for (int i=1; i<args.length; i++){
                        if (i == args.length-1){
                            messageToAdd += args[i];
                        }else {
                            messageToAdd += args[i] + " ";
                        }
                    }
                    List<String> currentMessages = messages.get().getStringList("messages");
                    currentMessages.add(messageToAdd);
                    messages.get().set("messages", currentMessages);
                    messages.save();
                    sender.sendMessage(ChatColor.GREEN+"Message added.");
                    return true;
                } else if (args[0].equalsIgnoreCase("remove")){
                    if (isInteger(args[1])){
                        int indexToRemove = Integer.parseInt(args[1]) - 1;
                        List<String> currentMessages = messages.get().getStringList("messages");
                        try {
                            currentMessages.remove(indexToRemove);
                            messages.get().set("messages", currentMessages);
                            messages.save();
                            sender.sendMessage(ChatColor.GREEN+"Message removed.");
                            return true;
                        }catch(IndexOutOfBoundsException e){
                            sender.sendMessage(ChatColor.RED+"The autobroadcaster messages list does not contain that many messages.");
                            return true;
                        }
                    }else{
                        sender.sendMessage(ChatColor.DARK_AQUA + "/autobroadcaster " + ChatColor.AQUA + "remove <message number>");
                        return true;
                    }
                }else if(args[0].equalsIgnoreCase("interval")){
                    int counter = AutoBroadcasterTask.getCount();
                    if (isInteger(args[1])) {
                        task.cancel();
                        int newInterval = Integer.parseInt(args[1]);
                        messages.get().set("interval", newInterval);
                        messages.save();
                        messages.reload();
                        task = new AutoBroadcasterTask(messages, counter).runTaskTimer(plugin, 0, newInterval * 1200);
                        sender.sendMessage(ChatColor.GREEN+"Interval updated");
                    }else{
                        sender.sendMessage(ChatColor.RED+"Interval must be a number.");
                    }
                }
            } else {
                sender.sendMessage(ChatColor.DARK_AQUA + "----------------" + ChatColor.AQUA + " Autobroadcaster Help " + ChatColor.DARK_AQUA + "----------------");
                sender.sendMessage(ChatColor.AQUA + "/ab" + ChatColor.DARK_AQUA + " - Autobroadcaster command alias");
                sender.sendMessage(ChatColor.AQUA + "/autobroadcaster on" + ChatColor.DARK_AQUA + " - Turn on the autobroadcaster if it is off");
                sender.sendMessage(ChatColor.AQUA + "/autobroadcaster off" + ChatColor.DARK_AQUA + " - Turn off the autobroadcaster if it is on");
                sender.sendMessage(ChatColor.AQUA + "/autobroadcaster add <message>" + ChatColor.DARK_AQUA + " - Add a message to the messages list");
                sender.sendMessage(ChatColor.AQUA + "/autobroadcaster remove <message number>" + ChatColor.DARK_AQUA + " - Remove a message from the broadcaster");
                sender.sendMessage(ChatColor.AQUA + "/autobroadcaster interval <minutes>" + ChatColor.DARK_AQUA + " - Change the number of minutes between broadcasts");
                return true;
            }
        }else{
            sender.sendMessage(ChatColor.DARK_RED + "You do not have access to that command.");
        }
        return true;
    }

    private boolean isInteger(String s){
        try{
            Integer.parseInt(s);
        }catch(InputMismatchException ie){
            return false;
        }catch(NumberFormatException ne) {
            return false;
        }
        return true;
    }
}

