package de.meloneoderso.melonencb.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import static de.meloneoderso.melonencb.Constants.PREFIX;

public class UnknownCommandListener implements Listener {

    @EventHandler
    public static void onCommand(PlayerCommandPreprocessEvent e) {
        String msg = e.getMessage();
        String[] args = msg.split(" ");
        Player p = e.getPlayer();

        if (Bukkit.getServer().getHelpMap().getHelpTopic(args[0]) == null) {
            e.setCancelled(true);

            p.sendMessage(PREFIX + "§cDer Befehl §8[§a" + msg + "§8]§c existiert nicht!");
        }
    }
}
