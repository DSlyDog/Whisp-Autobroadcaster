package net.whispwriting.autobroadcaster.files;

import net.whispwriting.autobroadcaster.Autobroadcaster;
import java.util.Arrays;
import java.util.List;

public class BroadcastMessages extends AbstractFiles {
    public BroadcastMessages(Autobroadcaster pl) {
        super(pl, "messages.yml");
    }
    public void createConfig() {
        List<String> defaultMessages = Arrays.asList("Welcome to the server", "This plugin was coded by WhisptheFox/Zippitey2");
        boolean enabled = true;
        boolean prefix = true;
        String prefixString = "[Autobroadcaster]";
        String prefixColor = "aqua";
        String messageColor = "dark_aqua";
        int intervalNum = 1;
        config.addDefault("enabled", enabled );
        config.addDefault("prefix-enabled", prefix);
        config.addDefault("prefix", prefixString);
        config.addDefault("prefix-color", prefixColor);
        config.addDefault("message-color", messageColor);
        config.addDefault("interval", intervalNum);
        config.addDefault("messages", defaultMessages);
    }
}
