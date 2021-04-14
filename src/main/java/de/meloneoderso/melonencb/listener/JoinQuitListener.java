// -----------------------
// Coded by Pandadoxo
// on 13.04.2021 at 14:33 
// -----------------------

package de.meloneoderso.melonencb.listener;

import de.meloneoderso.melonencb.Constants;
import de.meloneoderso.melonencb.MelonenCB;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinQuitListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (MelonenCB.getInstance().isSentAlert()) {
            Bukkit.getScheduler().runTaskLater(MelonenCB.getInstance(), () -> {
                event.getPlayer().sendMessage(Constants.PREFIX + "§cDie Farmwelten werden in §akürze §czurückgesetzt! Bitte sammel all deine" +
                        " wichtigen Items ein und bereite dich darauf vor");
                event.getPlayer().sendMessage(Constants.PREFIX + "§cGebe für genauere Infos §e/reset §cein");
            }, 10);
        }
    }

}
