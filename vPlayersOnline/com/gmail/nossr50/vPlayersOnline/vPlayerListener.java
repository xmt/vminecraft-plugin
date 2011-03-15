package com.gmail.nossr50.vPlayersOnline;

import java.util.Properties;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.ChatColor;
import org.bukkit.World;

/**
 * Handle events for all Player related events
 * @author nossr50
 */
public class vPlayerListener extends PlayerListener {
    private final vPlayersOnline plugin;
    private final String PlayersOnline;
    private final String PlayerList;
    private final String TotalPlayers;
    private final String _1POnline;

    public vPlayerListener(vPlayersOnline instance, Properties config) {
        plugin = instance;
        PlayersOnline = parseColors(config.getProperty("PlayersOnline"));
        PlayerList = parseColors(config.getProperty("PlayerList"));
        TotalPlayers = parseColors(config.getProperty("TotalPlayers"));
        _1POnline = parseColors(config.getProperty("1POnline"));
    }

    //Function to count the players
    private int playerCount(){
    	Player players[] = plugin.getServer().getOnlinePlayers();
        return players.length;
    }

    private static String parseColors(String str) {
        final StringBuilder sb = new StringBuilder();

        for (int i = 0; i < str.length()-1; ++i) {
            char c = str.charAt(i);
            
            if (c == '&') {
                char next = str.charAt(i+1);
                if (next == '&') {
                    // literal &
                    ++i;
                } else {
                    try {
                        int color = Integer.parseInt(String.valueOf(next), 16);
                        sb.append(ChatColor.getByCode(color));
                        ++i;
                        continue;
                    } catch (NumberFormatException e) {}
                }
            }

            sb.append(c);
        }
        sb.append(str.charAt(str.length()-1));

        return sb.toString();
    }

    private String getPlayerName(Player player) {
        String name = player.getName();
        if (plugin.Permissions != null) {
            final World world = player.getWorld();
            final String w = world.getName();
            final String group = plugin.Permissions.getGroup(w, name);

            name =    plugin.Permissions.getGroupPrefix(w, group)
                    + name
                    + plugin.Permissions.getGroupSuffix(w, group)
                    + ChatColor.WHITE;
        }
        return name;
    }

    //Message to be sent when a player joins
    @Override
    public void onPlayerJoin(PlayerEvent event) {
        Player player = event.getPlayer();
        int count = playerCount();

        if (count == 1) {
            player.sendMessage(_1POnline);
        } else {
            player.sendMessage(String.format(PlayersOnline, count));
        }
    }

    //Message to be sent when a player uses /list
    @Override
    public void onPlayerCommandPreprocess(PlayerChatEvent event) {
    	String[] split = event.getMessage().split(" ");
        Player player = event.getPlayer();
        if(split[0].equalsIgnoreCase("/list") || split[0].equalsIgnoreCase("/who")){
            event.setCancelled(true);

            int count = playerCount();
            String tempList = "";
            int x = 0;
            for(Player p : plugin.getServer().getOnlinePlayers())
            {
            	if(p != null && x+1 >= count) {
            		tempList += getPlayerName(p);
            		x++;
            	}
            	if(p != null && x < count) {
            		tempList += getPlayerName(p) +", ";
            		x++;
            	}
            }
            //Output the player list
            player.sendMessage(String.format(PlayerList, tempList));
            player.sendMessage(String.format(TotalPlayers, count));
        }
    }
    
}
