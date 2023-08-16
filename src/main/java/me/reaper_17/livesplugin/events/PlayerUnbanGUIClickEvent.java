package me.reaper_17.livesplugin.events;

import me.reaper_17.livesplugin.LivesPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class PlayerUnbanGUIClickEvent implements Listener {
    public static Player p;
    @EventHandler
    public void onPlayerUnbanGUIClick(InventoryClickEvent e){
        InventoryView inv = e.getWhoClicked().getOpenInventory();
        if (inv.getTitle().equalsIgnoreCase("unban")){
            String playerName = e.getCurrentItem().getItemMeta().getDisplayName();
            p = Bukkit.getPlayer(playerName);
            e.getWhoClicked().closeInventory();
            Inventory confirmInv = Bukkit.createInventory(e.getWhoClicked(), 27, "confirm");
            ItemStack confirm = new ItemStack(Material.GREEN_TERRACOTTA);
            ItemMeta m1 = confirm.getItemMeta();
            m1.setDisplayName(ChatColor.GREEN + "CONFIRM");
            confirm.setItemMeta(m1);
            ItemStack cancel = new ItemStack(Material.RED_TERRACOTTA);
            ItemMeta m2 = cancel.getItemMeta();
            m2.setDisplayName(ChatColor.RED + "CANCEL");
            cancel.setItemMeta(m2);
            confirmInv.setItem(12, confirm);
            confirmInv.setItem(14, cancel);
            e.getWhoClicked().openInventory(confirmInv);
        }
    }

    @EventHandler
    public void onPlayerConfirmGUIClick(InventoryClickEvent e){
        InventoryView inv = e.getWhoClicked().getOpenInventory();
        if (inv.getTitle().equalsIgnoreCase("confirm")){
            if (e.getCurrentItem().getType() == Material.GREEN_TERRACOTTA){
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pardon " + p.getName());
                LivesPlugin.getBannedPlayers().remove(p.getUniqueId());
                p.getPersistentDataContainer().set(PlayerFirstJoinEvent.getLIVES(), PersistentDataType.INTEGER, 1);
                PlayerLifeClickEvent.playChimes(p);
                e.getWhoClicked().closeInventory();
                e.getWhoClicked().sendMessage("Revived successfully");
            }
            else if (e.getCurrentItem().getType() == Material.RED_TERRACOTTA){
                Player p = (Player) e.getWhoClicked();
                p.playSound(p, Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                e.getWhoClicked().closeInventory();
            }
            else {
                //do nothing
            }
        }
    }
}
