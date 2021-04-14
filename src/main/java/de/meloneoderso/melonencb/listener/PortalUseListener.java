// -----------------------
// Coded by Pandadoxo
// on 09.04.2021 at 13:11 
// -----------------------

package de.meloneoderso.melonencb.listener;

import de.meloneoderso.melonencb.MelonenCB;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class PortalUseListener implements Listener {

    private static final HashMap<Entity, BukkitTask> inPortal = new HashMap<>();

    @EventHandler
    public void onPortalEnter(EntityPortalEnterEvent event) {
        if (event.getLocation().getBlock().getType().equals(Material.NETHER_PORTAL)) {
            if (inPortal.containsKey(event.getEntity())) return;
            inPortal.put(event.getEntity(), new BukkitRunnable() {
                @Override
                public void run() {
                    if (event.getLocation().getWorld().getName().equalsIgnoreCase("world_nether")) {
                        event.getEntity().teleport(MelonenCB.plotWorld.getSpawnLocation());
                    } else {
                        event.getEntity().teleport(Bukkit.getWorld("world_nether").getSpawnLocation());
                    }
                }
            }.runTaskLater(MelonenCB.getInstance(), event.getEntity() instanceof Player &&
                    ((Player) event.getEntity()).getGameMode().equals(GameMode.CREATIVE) ? 0 : 60));
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!inPortal.containsKey(event.getPlayer())) return;
        if (event.getTo().getBlock().getType().equals(Material.NETHER_PORTAL)) return;
        inPortal.get(event.getPlayer()).cancel();
        inPortal.remove(event.getPlayer());
    }

    @EventHandler
    public void onPortalCreate(PortalCreateEvent event) {
        if (event.getReason().equals(PortalCreateEvent.CreateReason.NETHER_PAIR)) {
            event.setCancelled(true);
        }
    }


    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL)) {
            if (event.getFrom().getWorld().getName().equalsIgnoreCase("world_the_nether")) {
                event.setTo(MelonenCB.plotWorld.getSpawnLocation());
            } else {
                event.setTo(Bukkit.getWorld("world_the_end").getSpawnLocation());
            }
        }
    }


}
