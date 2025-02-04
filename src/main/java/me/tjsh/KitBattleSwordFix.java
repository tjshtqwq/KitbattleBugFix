package me.tjsh;

import me.wazup.kitbattle.Kit;
import me.wazup.kitbattle.Kitbattle;
import me.wazup.kitbattle.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;


public final class KitBattleSwordFix extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this, this);
        BukkitRunnable a = new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : getServer().getOnlinePlayers()) {
                    PlayerData data = Kitbattle.kitbattleApi.getPlayerData(p);
                    if (data == null) return;
                    Kit kit = data.getKit();
                    if (kit == null) return;
                    if (kit.getItems()[0] == null) return;
                    ItemStack firstKitItem = kit.getItems()[0];
                    if (p.hasPermission("kbsf.debug")) {
                        p.sendMessage("Kit " + firstKitItem.getType().toString());
                    }
                    ItemStack[] kitItems = kit.getItems();
                    ItemStack[] playerItems = p.getInventory().getContents();
                    int i = 0;
                    for (ItemStack kitItem : kitItems) {
                        boolean found = false;
                        for (ItemStack playerItem : playerItems) {
                            if (kitItem.getType().toString().contains("STEW") || kitItem.getType().toString().contains("POTION") || kitItem.getType().toString().contains("ARROW"))
                                continue;
                            if (playerItem.getType() == kitItem.getType()) {
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            p.getInventory().setItem(i, kitItem);
                        }
                        i++;
                    }
                }
            }
        };
        a.runTaskTimer(this, 0, 1);
    }
    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        e.setCancelled(true);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    // mvn install:install-file -Dfile=C:/Users/Temper/Pictures/[K]KitBattle.jar -DgroupId=me.wazup.kitbattle -DartifactId=kitbattle -Dversion=1.0.0 -Dpackaging=jar
}
