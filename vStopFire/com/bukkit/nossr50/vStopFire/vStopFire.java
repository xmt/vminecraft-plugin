package com.bukkit.nossr50.vStopFire;

import java.io.File;
import org.bukkit.Server;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * vStopFire for Bukkit
 *
 * @author nossr50
 */
public class vStopFire extends JavaPlugin {
    private final vPlayerListener playerListener = new vPlayerListener(this);
    private final vBlockListener blockListener = new vBlockListener(this);
    private final String name = "vStopFire";

    public void onEnable() {
    	getServer().getPluginManager().registerEvent(Event.Type.BLOCK_IGNITE, blockListener, Priority.Normal, this);
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
    }
    public void onDisable() {
        System.out.println("vStopFire disabled!");
    }
}
