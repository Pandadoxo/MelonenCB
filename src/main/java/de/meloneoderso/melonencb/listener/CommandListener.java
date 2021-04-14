package de.meloneoderso.melonencb.listener;

import de.meloneoderso.melonencb.commands.CommandEditor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (e.getPlayer().hasPermission("melonencb.bypass")) {
            return;
        }

        String msg = e.getMessage();
        msg = msg.replace("/", "");

        CommandEditor editor = new CommandEditor(msg);

        if (editor.contains()) {
            e.setCancelled(true);
            editor.send(e.getPlayer());
        }

    }
}
