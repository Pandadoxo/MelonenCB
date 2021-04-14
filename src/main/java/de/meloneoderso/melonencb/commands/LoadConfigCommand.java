// -----------------------
// Coded by Pandadoxo
// on 06.04.2021 at 12:31 
// -----------------------

package de.meloneoderso.melonencb.commands;

import de.meloneoderso.melonencb.Constants;
import de.meloneoderso.melonencb.MelonenCB;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LoadConfigCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player))
            return true;
        Player p = (Player) sender;
        if (!p.hasPermission("melonencb.loadconfig")) {
            p.sendMessage(Constants.PREFIXNOPERMS);
            return true;
        }

        MelonenCB.getFilesUtil().load();
        p.sendMessage(Constants.PREFIX + "§aDie Config wurde reloaded");
        return false;
    }


}