package me.reaper_17.livesplugin.events;

import me.reaper_17.livesplugin.utils.UnBanGUI;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerUnbanGUIOpenEvent implements Listener {

    @EventHandler
    public void onPlayerOpenUnbanGUI(PlayerInteractEvent e){
        if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.LIGHT_PURPLE + "Revive Item")){
            e.getPlayer().openInventory(UnBanGUI.getUnbanGUI(e.getPlayer()));
            e.setCancelled(true);
        }
    }
}
