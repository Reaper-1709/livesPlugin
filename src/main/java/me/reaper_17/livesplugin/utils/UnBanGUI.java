package me.reaper_17.livesplugin.utils;

import me.reaper_17.livesplugin.LivesPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.UUID;

public class UnBanGUI {
    public static Inventory getUnbanGUI(InventoryHolder holder){
        Inventory unbanGUI = Bukkit.createInventory(holder, 45, "unban");
        for (UUID u : LivesPlugin.getBannedPlayers()){
            Player p = Bukkit.getPlayer(u);

            ItemStack i = new ItemStack(Material.PLAYER_HEAD);
            ItemMeta m = i.getItemMeta();
            ArrayList<String> lore = (ArrayList<String>) m.getLore();
            m.setDisplayName(ChatColor.GREEN + p.getName());
            lore.add(ChatColor.YELLOW + "Click to revive this player");
            m.setLore(lore);
            i.setItemMeta(m);

            unbanGUI.addItem(i);
        }
        return unbanGUI;
    }
}
