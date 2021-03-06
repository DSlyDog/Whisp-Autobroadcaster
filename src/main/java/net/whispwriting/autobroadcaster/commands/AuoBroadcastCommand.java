package net.whispwriting.autobroadcaster.commands;

import net.whispwriting.autobroadcaster.Autobroadcaster;
import net.whispwriting.autobroadcaster.events.AutoBroadcasterTask;
import net.whispwriting.autobroadcaster.files.BroadcastMessages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitTask;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AuoBroadcastCommand implements CommandExecutor {

    private BroadcastMessages messages;
    private AutoBroadcasterTask task;
    private Autobroadcaster plugin;

    public AuoBroadcastCommand(BroadcastMessages msg, AutoBroadcasterTask t, Autobroadcaster pl){
        messages = msg;
        task = t;
        plugin = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (sender.hasPermission("WhispPlugin.autobroadcaster")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("on")) {
                    abOn(sender);
                    return true;
                } else if (args[0].equalsIgnoreCase("off")) {
                    abOff(sender);
                    return true;
                } else if (args[0].equalsIgnoreCase("add")) {
                    abAddOneElem(sender);
                    return true;
                }else if (args[0].equalsIgnoreCase("remove")) {
                    abRemoveOneElem(sender);
                    return true;
                }else if (args[0].equalsIgnoreCase("interval")) {
                    abIntervalOneElem(sender);
                    return true;
                }
            } else if (args.length >= 2){
                if (args[0].equalsIgnoreCase("add")){
                    abAdd(sender, args);
                    return true;
                } else if (args[0].equalsIgnoreCase("remove")){
                    abRemove(sender, args);
                    return true;
                }else if(args[0].equalsIgnoreCase("interval")){
                    abInterval(sender, args);
                    return true;
                }else if (args[0].equalsIgnoreCase("prefixcolor")){
                    abPrefixColor(sender, args);
                    return true;
                }else if (args[0].equalsIgnoreCase("messagecolor")) {
                    abMessageColor(sender, args);
                    return true;
                }
            } else {
                abHelp(sender);
                return true;
            }
        }else{
            sender.sendMessage(ChatColor.DARK_RED + "You do not have access to that command.");
        }
        return true;
    }





    // helper methods
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

    private void abOn(CommandSender sender) {
        if (!messages.get().getBoolean("enabled")) {
            messages.reload();
            messages.get().set("enabled", true);
            messages.save();
            messages.reload();
            task = new AutoBroadcasterTask(messages, 0);
            Bukkit.getScheduler().runTaskTimer(plugin, task, 0, messages.get().getInt("interval") * 20 * 60);
            sender.sendMessage(ChatColor.GREEN + "Autobroadcasts are now enabled.");
        } else {
            sender.sendMessage(ChatColor.AQUA + "AutoBroadcaster is already on.");
        }
    }

    private void abOff(CommandSender sender) {
        if (messages.get().getBoolean("enabled")) {
            Bukkit.getScheduler().cancelTasks(plugin);
            messages.get().set("enabled", false);
            messages.save();
            messages.reload();
            sender.sendMessage(ChatColor.RED + "AutoBroadcasts are now disabled.");
        } else {
            sender.sendMessage(ChatColor.AQUA + "AutoBroadcaster is already off.");
        }
    }

    private void abAddOneElem(CommandSender sender) {
        sender.sendMessage(ChatColor.DARK_AQUA + "/autobroadcaster " + ChatColor.AQUA + "add <message>");
    }

    private void abRemoveOneElem(CommandSender sender) {
        sender.sendMessage(ChatColor.DARK_AQUA + "/autobroadcaster " + ChatColor.AQUA + "remove <message number>");
    }

    private void abIntervalOneElem(CommandSender sender) {
        sender.sendMessage(ChatColor.DARK_AQUA + "/autobroadcaster " + ChatColor.AQUA + "interval <minutes>");
    }

    private void abAdd(CommandSender sender, String[] args) {
        String messageToAdd = "";
        for (int i = 1; i < args.length; i++) {
            if (i == args.length - 1) {
                messageToAdd += args[i];
            } else {
                messageToAdd += args[i] + " ";
            }
        }
        List<String> currentMessages = messages.get().getStringList("messages");
        currentMessages.add(messageToAdd);
        messages.get().set("messages", currentMessages);
        messages.save();
        sender.sendMessage(ChatColor.GREEN + "Message added.");
    }

    private void abRemove(CommandSender sender, String[] args){
        if (isInteger(args[1])){
            int indexToRemove = Integer.parseInt(args[1]) - 1;
            List<String> currentMessages = messages.get().getStringList("messages");
            try {
                currentMessages.remove(indexToRemove);
                messages.get().set("messages", currentMessages);
                messages.save();
                sender.sendMessage(ChatColor.GREEN+"Message removed.");
            }catch(IndexOutOfBoundsException e){
                sender.sendMessage(ChatColor.RED+"The autobroadcaster messages list does not contain that many messages.");
            }
        }else{
            sender.sendMessage(ChatColor.DARK_AQUA + "/autobroadcaster " + ChatColor.AQUA + "remove <message number>");
        }
    }

    private void abInterval (CommandSender sender, String[] args){
        int counter = AutoBroadcasterTask.getCount();
        if (isInteger(args[1])) {
            task.cancel();
            int newInterval = Integer.parseInt(args[1]);
            messages.get().set("interval", newInterval);
            messages.save();
            messages.reload();
            Bukkit.getScheduler().runTaskTimer(plugin, task,0, messages.get().getInt("interval") * 20 * 60);
            sender.sendMessage(ChatColor.GREEN+"Interval updated");
        }else{
            sender.sendMessage(ChatColor.RED+"Interval must be a number.");
        }
    }

    private void abPrefixColor(CommandSender sender, String[] args){
        messages.get().set("prefix-color", args[1]);
        sender.sendMessage(ChatColor.GREEN+"Prefix color updated.");
    }

    private void abMessageColor(CommandSender sender, String[] args){
        messages.get().set("message-color", args[1]);
        sender.sendMessage(ChatColor.GREEN + "Message color updated.");
    }

    private void abHelp(CommandSender sender){
        sender.sendMessage(ChatColor.DARK_AQUA + "----------------" + ChatColor.AQUA + " Autobroadcaster Help " + ChatColor.DARK_AQUA + "----------------");
        sender.sendMessage(ChatColor.AQUA + "/ab" + ChatColor.DARK_AQUA + " - Autobroadcaster command alias");
        sender.sendMessage(ChatColor.AQUA + "/autobroadcaster on" + ChatColor.DARK_AQUA + " - Turn on the autobroadcaster if it is off");
        sender.sendMessage(ChatColor.AQUA + "/autobroadcaster off" + ChatColor.DARK_AQUA + " - Turn off the autobroadcaster if it is on");
        sender.sendMessage(ChatColor.AQUA + "/autobroadcaster add <message>" + ChatColor.DARK_AQUA + " - Add a message to the messages list");
        sender.sendMessage(ChatColor.AQUA + "/autobroadcaster remove <message number>" + ChatColor.DARK_AQUA + " - Remove a message from the broadcaster");
        sender.sendMessage(ChatColor.AQUA + "/autobroadcaster interval <minutes>" + ChatColor.DARK_AQUA + " - Change the number of minutes between broadcasts");
        sender.sendMessage(ChatColor.AQUA + "/autobroadcaster prefixcolor <color>" + ChatColor.DARK_AQUA + " - Change the color of the prefix.");
        sender.sendMessage(ChatColor.AQUA + "/autobroadcaster messagecolor <color>" + ChatColor.DARK_AQUA + " - Change the color of the messages.");
    }
}

