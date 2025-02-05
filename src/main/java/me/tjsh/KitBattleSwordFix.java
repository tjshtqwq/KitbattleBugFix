package me.tjsh;

import me.wazup.kitbattle.Kit;
import me.wazup.kitbattle.Kitbattle;
import me.wazup.kitbattle.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
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
                    if (p.hasPermission("kbsf.fix")) {
                        for (ItemStack i : p.getInventory().getContents()) {
                            if (i.getType() == Material.BOWL) {
                                p.getInventory().remove(i);
                            }
                        }
                    }
                }
            }
        };
        a.runTaskTimer(this, 0, 1);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        HandlerList.unregisterAll((Plugin) this);
    }


    // mvn install:install-file -Dfile=C:/Users/Temper/Pictures/[K]KitBattle.jar -DgroupId=me.wazup.kitbattle -DartifactId=kitbattle -Dversion=1.0.0 -Dpackaging=jar
}
