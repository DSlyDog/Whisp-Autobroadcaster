package net.whispwriting.autobroadcaster.files;

import net.whispwriting.autobroadcaster.Autobroadcaster;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class AbstractFiles {

    protected Autobroadcaster plugin;
    private File file;
    protected FileConfiguration config;

    public AbstractFiles(Autobroadcaster pl, String filename){
        plugin = pl;
        file = new File(pl.getDataFolder(), filename);
        if (!file.exists()){
            try{
                file.createNewFile();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }
    public void save(){
        try{
            config.save(file);
        }catch(IOException e){
            System.out.println("Could not save file");
        }
    }
    public FileConfiguration get(){
        return config;
    }

    public void reload(){
        config = YamlConfiguration.loadConfiguration(file);
    }
}