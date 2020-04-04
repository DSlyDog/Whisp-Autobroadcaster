package net.whispwriting.autobroadcaster.events;

import net.whispwriting.autobroadcaster.files.BroadcastMessages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.TimerTask;

public class AutoBroadcasterTask extends TimerTask {
    private static int counter;
    public BroadcastMessages messages;

    public AutoBroadcasterTask(BroadcastMessages msgs, int c) {
        messages = msgs;
        counter = c;
    }

    @Override
    public void run() {
        if (messages.get().getBoolean("enabled")) {
            String prefix;
            if (messages.get().getBoolean("prefix-enabled")) {
                String getPrefix = messages.get().getString("prefix");
                if (messages.get().getString("prefix-color") != null) {
                    if (messages.get().getString("prefix-color").equalsIgnoreCase("aqua")) {
                        prefix = ChatColor.AQUA + getPrefix + " ";
                    } else if (messages.get().getString("prefix-color").equalsIgnoreCase("dark_aqua")) {
                        prefix = ChatColor.DARK_AQUA + getPrefix + " ";
                    } else if (messages.get().getString("prefix-color").equalsIgnoreCase("red")) {
                        prefix = ChatColor.RED + getPrefix + " ";
                    } else if (messages.get().getString("prefix-color").equalsIgnoreCase("dark_red")) {
                        prefix = ChatColor.DARK_RED + getPrefix + " ";
                    } else if (messages.get().getString("prefix-color").equalsIgnoreCase("gold")) {
                        prefix = ChatColor.GOLD + getPrefix + " ";
                    } else if (messages.get().getString("prefix-color").equalsIgnoreCase("green")) {
                        prefix = ChatColor.GREEN + getPrefix + " ";
                    } else if (messages.get().getString("prefix-color").equalsIgnoreCase("light_purple")) {
                        prefix = ChatColor.LIGHT_PURPLE + getPrefix + " ";
                    } else if (messages.get().getString("prefix-color").equalsIgnoreCase("dark_purple")) {
                        prefix = ChatColor.DARK_PURPLE + getPrefix + " ";
                    } else if (messages.get().getString("prefix-color").equalsIgnoreCase("white")) {
                        prefix = ChatColor.WHITE + getPrefix + " ";
                    } else if (messages.get().getString("prefix-color").equalsIgnoreCase("black")) {
                        prefix = ChatColor.BLACK + getPrefix + " ";
                    } else if (messages.get().getString("prefix-color").equalsIgnoreCase("blue")) {
                        prefix = ChatColor.BLUE + getPrefix + " ";
                    } else if (messages.get().getString("prefix-color").equalsIgnoreCase("dark_blue")) {
                        prefix = ChatColor.DARK_BLUE + getPrefix + " ";
                    } else if (messages.get().getString("prefix-color").equalsIgnoreCase("dark_gray")) {
                        prefix = ChatColor.DARK_GRAY + getPrefix + " ";
                    } else if (messages.get().getString("prefix-color").equalsIgnoreCase("gray")) {
                        prefix = ChatColor.GRAY + getPrefix + " ";
                    } else if (messages.get().getString("prefix-color").equalsIgnoreCase("yellow")) {
                        prefix = ChatColor.YELLOW + getPrefix + " ";
                    } else if (messages.get().getString("prefix-color").equalsIgnoreCase("dark_green")) {
                        prefix = ChatColor.DARK_GREEN + getPrefix + " ";
                    } else {
                        Bukkit.getServer().broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "There was an error loading the broadcast. Please check " +
                                "the console for more information.");
                        System.out.println("The color found for the prefix is unsupported. Please look at the documentation and choose one of the supported colors.");
                        return;
                    }
                } else {
                    Bukkit.getServer().broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "There was an error loading the broadcast. Please check " +
                            "the console for more information.");
                    System.out.println("The color given for the prefix is empty. Please list a color or disable the prefix.");
                    return;
                }
            } else {
                prefix = "";
            }
            List<String> messageList = messages.get().getStringList("messages");
            if (messageList.size() > 0) {
                if (messages.get().getString("message-color") != null) {
                    String msg;
                    try {
                        msg = messageList.get(counter);
                    }catch (IndexOutOfBoundsException e){
                        counter = 0;
                        msg = messageList.get(counter);
                    }
                    if (messages.get().getString("message-color").equalsIgnoreCase("aqua")) {
                        Bukkit.getServer().broadcastMessage(prefix + ChatColor.AQUA + msg);
                    } else if (messages.get().getString("message-color").equalsIgnoreCase("dark_aqua")) {
                        Bukkit.getServer().broadcastMessage(prefix + ChatColor.DARK_AQUA + msg);
                    } else if (messages.get().getString("message-color").equalsIgnoreCase("red")) {
                        Bukkit.getServer().broadcastMessage(prefix + ChatColor.RED + msg);
                    } else if (messages.get().getString("message-color").equalsIgnoreCase("dark_red")) {
                        Bukkit.getServer().broadcastMessage(prefix + ChatColor.DARK_RED + msg);
                    } else if (messages.get().getString("message-color").equalsIgnoreCase("gold")) {
                        Bukkit.getServer().broadcastMessage(prefix + ChatColor.GOLD + msg);
                    } else if (messages.get().getString("message-color").equalsIgnoreCase("green")) {
                        Bukkit.getServer().broadcastMessage(prefix + ChatColor.GREEN + msg);
                    } else if (messages.get().getString("message-color").equalsIgnoreCase("light_purple")) {
                        Bukkit.getServer().broadcastMessage(prefix + ChatColor.LIGHT_PURPLE + msg);
                    } else if (messages.get().getString("message-color").equalsIgnoreCase("dark_purple")) {
                        Bukkit.getServer().broadcastMessage(prefix + ChatColor.DARK_PURPLE + msg);
                    } else if (messages.get().getString("message-color").equalsIgnoreCase("white")) {
                        Bukkit.getServer().broadcastMessage(prefix + ChatColor.WHITE + msg);
                    } else if (messages.get().getString("message-color").equalsIgnoreCase("black")) {
                        Bukkit.getServer().broadcastMessage(prefix + ChatColor.BLACK + msg);
                    } else if (messages.get().getString("message-color").equalsIgnoreCase("blue")) {
                        Bukkit.getServer().broadcastMessage(prefix + ChatColor.BLUE + msg);
                    } else if (messages.get().getString("message-color").equalsIgnoreCase("dark_blue")) {
                        Bukkit.getServer().broadcastMessage(prefix + ChatColor.DARK_BLUE + msg);
                    } else if (messages.get().getString("message-color").equalsIgnoreCase("dark_gray")) {
                        Bukkit.getServer().broadcastMessage(prefix + ChatColor.DARK_GRAY + msg);
                    } else if (messages.get().getString("message-color").equalsIgnoreCase("gray")) {
                        Bukkit.getServer().broadcastMessage(prefix + ChatColor.GRAY + msg);
                    } else if (messages.get().getString("message-color").equalsIgnoreCase("yellow")) {
                        Bukkit.getServer().broadcastMessage(prefix + ChatColor.YELLOW + msg);
                    } else if (messages.get().getString("message-color").equalsIgnoreCase("dark_green")) {
                        Bukkit.getServer().broadcastMessage(prefix + ChatColor.DARK_GREEN + msg);
                    } else {
                        Bukkit.getServer().broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "There was an error loading the broadcast. Please check " +
                                "the console for more information.");
                        System.out.println("The color found for the message is unsupported. Please look at the documentation and choose one of the supported colors.");
                        return;
                    }
                } else {
                    Bukkit.getServer().broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "There was an error loading the broadcast. Please check " +
                            "the console for more information.");
                    System.out.println("The color given for messages is empty. Please list a color or disable the prefix.");
                    return;
                }
            } else {
                Bukkit.getServer().broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "There was an error loading the broadcast. Please check " +
                        "the console for more information.");
                System.out.println("The messages list is currently empty. If you wish to stop the broadcasts, please set 'enabled' to false, or use the command " +
                        "/autobroadcaster off (alias: /ab off).");
                return;
            }
            counter = counter + 1;
            if (counter == messageList.size()) {
                counter = 0;
            }
        }
    }
    public static int getCount(){
        return counter;
    }

}
