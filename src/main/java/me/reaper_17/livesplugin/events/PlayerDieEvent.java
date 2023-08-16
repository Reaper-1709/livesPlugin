package me.reaper_17.livesplugin.events;

import me.reaper_17.livesplugin.LivesPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.persistence.PersistentDataType;

public class PlayerDieEvent implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        Player p = e.getEntity();
        if (p.getPersistentDataContainer().get(new NamespacedKey(LivesPlugin.getInstance(), "lives"), PersistentDataType.INTEGER) > 0){
            p.getPersistentDataContainer().set(new NamespacedKey(LivesPlugin.getInstance(), "lives"), PersistentDataType.INTEGER,(p.getPersistentDataContainer().get(new NamespacedKey(LivesPlugin.getInstance(), "lives"), PersistentDataType.INTEGER) - 1));
            p.sendMessage(ChatColor.RED + "You died and lost a life. You have " + String.valueOf(p.getPersistentDataContainer().get(new NamespacedKey(LivesPlugin.getInstance(), "lives"), PersistentDataType.INTEGER)) + " lives");
        }
        else {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + p.getName());
            LivesPlugin.getBannedPlayers().add(p.getUniqueId());
        }
    }
}
