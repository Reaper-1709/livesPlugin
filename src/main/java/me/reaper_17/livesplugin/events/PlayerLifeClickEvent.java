package me.reaper_17.livesplugin.events;

import me.reaper_17.livesplugin.LivesPlugin;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class PlayerLifeClickEvent implements Listener {

    @EventHandler
    public void onPlayerConsumeLife(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.LIGHT_PURPLE + "Life")) {
                Player p = e.getPlayer();
                p.getInventory().remove(p.getInventory().getItemInMainHand());
                playChimes(p);
                p.getPersistentDataContainer().set(PlayerFirstJoinEvent.getLIVES(), PersistentDataType.INTEGER, (p.getPersistentDataContainer().get(PlayerFirstJoinEvent.getLIVES(), PersistentDataType.INTEGER) + 1));
                p.sendMessage(ChatColor.GREEN + "You now have " + String.valueOf(p.getPersistentDataContainer().get(PlayerFirstJoinEvent.getLIVES(), PersistentDataType.INTEGER)) + " lives");
            }
        }
    }

    public static void playChimes(Player p) {
        new BukkitRunnable() {
            @Override
            public void run() {
                p.playSound(p, Sound.BLOCK_NOTE_BLOCK_CHIME, 1.0f, 1.0f);
            }
        }.runTaskLater(LivesPlugin.getInstance(), 10);

        new BukkitRunnable() {
            @Override
            public void run() {
                p.playSound(p, Sound.BLOCK_NOTE_BLOCK_CHIME, 1.0f, 2.0f);
            }
        }.runTaskLater(LivesPlugin.getInstance(), 10);

        new BukkitRunnable() {
            @Override
            public void run() {
                p.playSound(p, Sound.BLOCK_NOTE_BLOCK_CHIME, 1.0f, 3.0f);
            }
        }.runTaskLater(LivesPlugin.getInstance(), 10);
    }
}