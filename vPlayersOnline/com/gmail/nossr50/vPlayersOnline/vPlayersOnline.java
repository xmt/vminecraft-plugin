package com.gmail.nossr50.vPlayersOnline;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import java.util.Properties;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;

/**
 * vPlayersOnline for Bukkit
 *
 * @author nossr50
 */
public class vPlayersOnline extends JavaPlugin {
    private PluginDescriptionFile pdfFile;
    private vPlayerListener playerListener;

    public PermissionHandler Permissions;

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        pdfFile = this.getDescription();
        Config.name = pdfFile.getName();

        Properties config = Config.loadConfig();
        playerListener = new vPlayerListener(this, config);

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_DROP_ITEM, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_COMMAND_PREPROCESS, playerListener, Priority.Normal, this);

        setupPermissions();
        
        //Displays a message when plugin is loaded
        System.out.println(Config.name + " version " + pdfFile.getVersion() + " is enabled!");
    }

    @Override
    public void onDisable() {
        System.out.println(Config.name + " disabled.");
    }

    private void setupPermissions() {
        Plugin plugin = this.getServer().getPluginManager().getPlugin("Permissions");

        if (Permissions == null) {
            if (plugin != null) {
                System.out.println(Config.name + ": Found Permissions.");
                Permissions = ((Permissions)plugin).getHandler();
            }
        }
    }
}
