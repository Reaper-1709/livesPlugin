package me.reaper_17.livesplugin.events;

import me.reaper_17.livesplugin.LivesPlugin;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataType;

public class PlayerFirstJoinEvent implements Listener {
    private static NamespacedKey LIVES = new NamespacedKey(LivesPlugin.getInstance(), "lives");
    @EventHandler
    public void onPlayerJoinFirstTime(PlayerJoinEvent e){
        if (!e.getPlayer().hasPlayedBefore()){
            Player p = e.getPlayer();
            p.getPersistentDataContainer().set(LIVES, PersistentDataType.INTEGER, 5);
        }
    }

    public static NamespacedKey getLIVES() {
        return LIVES;
    }
}
