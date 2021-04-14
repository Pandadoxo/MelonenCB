// -----------------------
// Coded by Pandadoxo
// on 08.04.2021 at 22:02 
// -----------------------

package de.meloneoderso.melonencb.listener;

import de.meloneoderso.melonencb.MelonenCB;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class SpawnProtectionListener implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player p = event.getPlayer();
        if (p.hasPermission("melonencb.ignorespawnprot")) return;
        event.setCancelled(shouldCancel(event.getBlock().getLocation()));
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();
        if (p.hasPermission("melonencb.ignorespawnprot")) return;
        event.setCancelled(shouldCancel(event.getBlock().getLocation()));
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        event.setCancelled(shouldCancel(event.getEntity().getLocation()));
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        event.setCancelled(shouldCancel(event.getEntity().getLocation()));
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        event.setCancelled(shouldCancel(event.getEntity().getLocation()));
    }

    @EventHandler
    public void onExplode(BlockExplodeEvent event) {
        event.setCancelled(shouldCancel(event.getBlock().getLocation()));
    }

    @EventHandler
    public void onBurn(BlockBurnEvent event) {
        event.setCancelled(shouldCancel(event.getBlock().getLocation()));
    }

    @EventHandler
    public void onIgnite(BlockIgniteEvent event) {
        event.setCancelled(shouldCancel(event.getBlock().getLocation()));
    }


    public boolean shouldCancel(Location location) {
        World world = location.getWorld();
        if (world.getName().equalsIgnoreCase("world2") || world.getName().equalsIgnoreCase("world_nether") ||
                world.getName().equalsIgnoreCase("world_the_end")) {

            Location spawn = world.getSpawnLocation();
            Location blockLoc = location.getBlock().getLocation();
            int minX = spawn.getBlockX() - MelonenCB.getResetConfig().getSpawnRadius();
            int maxX = spawn.getBlockX() + MelonenCB.getResetConfig().getSpawnRadius();
            int minZ = spawn.getBlockZ() - MelonenCB.getResetConfig().getSpawnRadius();
            int maxZ = spawn.getBlockZ() + MelonenCB.getResetConfig().getSpawnRadius();

            return blockLoc.getX() >= minX && blockLoc.getX() <= maxX && blockLoc.getZ() >= minZ && blockLoc.getZ() <= maxZ;
        }
        return false;
    }

}
