package me.reaper_17.livesplugin;

import me.reaper_17.livesplugin.events.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class LivesPlugin extends JavaPlugin {
    private static LivesPlugin instance;
    private static ArrayList<UUID> bannedPlayers;
    @Override
    public void onEnable() {
        // Plugin startup logic
        instance= this;

        ItemStack life = new ItemStack(Material.NETHER_STAR);
        ItemMeta lifeMeta = life.getItemMeta();
        lifeMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Life");
        List<String> lifeLore = lifeMeta.getLore();
        lifeLore.add("Right-Click to get a life");
        lifeMeta.setLore(lifeLore);
        life.setItemMeta(lifeMeta);

        ShapedRecipe lifeRecipe = new ShapedRecipe(new NamespacedKey(this, "life-recipe"), life);
        lifeRecipe.shape("AAA", "BCB", "BBB");
        lifeRecipe.setIngredient('A', Material.NETHERITE_INGOT);
        lifeRecipe.setIngredient('B', Material.REDSTONE_BLOCK);
        lifeRecipe.setIngredient('C', Material.BEACON);
        Bukkit.addRecipe(lifeRecipe);

        ItemStack reviveItem = new ItemStack(Material.BEACON);
        ItemMeta reviveMeta = life.getItemMeta();
        reviveMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Revive Item");
        List<String> reviveLore = lifeMeta.getLore();
        reviveLore.add("Right-Click to get a life");
        reviveMeta.setLore(lifeLore);
        reviveItem.setItemMeta(lifeMeta);

        ShapedRecipe reviveRecipe = new ShapedRecipe(new NamespacedKey(this, "revive-recipe"), reviveItem);
        reviveRecipe.shape("AAA", "ABA", "CCC");
        reviveRecipe.setIngredient('A', Material.DIAMOND);
        reviveRecipe.setIngredient('B', Material.NETHER_STAR);
        reviveRecipe.setIngredient('C', Material.OBSIDIAN);
        Bukkit.addRecipe(reviveRecipe);

        String bannedPlayers = getConfig().getString("ban-string");
        getBanList(Objects.requireNonNull(bannedPlayers));
        getConfig().options().copyDefaults();
        saveConfig();
        getServer().getPluginManager().registerEvents(new PlayerFirstJoinEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerDieEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerLifeClickEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerUnbanGUIOpenEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerUnbanGUIClickEvent(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getConfig().set("ban-string", persistBanList());
        saveConfig();
    }

    public static ArrayList<UUID> getBannedPlayers() {
        return bannedPlayers;
    }

    public static LivesPlugin getInstance() {
        return instance;
    }

    public static String persistBanList(){
        StringBuilder s = new StringBuilder();
        for (UUID u : bannedPlayers){
            Player p = Bukkit.getPlayer(u);
            s.append(Objects.requireNonNull(p).getName()).append(", ");
        }
        return s.toString();
    }

    public static void getBanList(String banListString){
        String[] banPlayers = banListString.split(", ");
        for (String s : banPlayers){
            bannedPlayers.add(UUID.fromString(s));
        }
    }
}
