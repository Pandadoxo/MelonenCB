// -----------------------
// Coded by Pandadoxo
// on 06.04.2021 at 10:30 
// -----------------------

package de.meloneoderso.melonencb.commands;

import de.meloneoderso.melonencb.Constants;
import de.meloneoderso.melonencb.MelonenCB;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResetCommand implements CommandExecutor, TabCompleter {

    private final HashMap<Player, String> toConfirm = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player))
            return true;
        Player p = (Player) sender;
        if (!p.hasPermission("melonencb.reset")) {
            p.sendMessage(Constants.PREFIXNOPERMS);
            return true;
        }

        if (args.length != 0 && !p.hasPermission("melonencb.reset.manage")) {
            p.sendMessage(Constants.PREFIXNOPERMS);
            return true;
        }

        if (args.length == 0) {
            p.sendMessage(Constants.PREFIX + "§f● Reset ●");
            p.sendMessage(Constants.PREFIX + "§7Automatischer Reset: " + (MelonenCB.getResetConfig().isAutoReset() ? "§a✔" : "§c❌"));
            p.sendMessage(Constants.PREFIX + "§7Nächster Reset: §a" + (MelonenCB.getResetConfig().isAutoReset() ? MelonenCB.getResetConfig().getNextResetTime() : "Manuell"));
            p.sendMessage(Constants.PREFIX + "§7Letzter Reset: §a" + MelonenCB.getResetConfig().getLastResetTime());
            return true;
        }

        if (args[0].equalsIgnoreCase("farmwelt") || args[0].equalsIgnoreCase("world")) {
            if (!toConfirm.containsKey(p) || !toConfirm.get(p).equalsIgnoreCase(args[0])) {
                addToConfirm(p, args[0].toLowerCase());
                return true;
            }
            MelonenCB.resetWorld = true;
            Bukkit.broadcastMessage(Constants.PREFIX + "§7Die §aFarm-Welt §7wird in §b10 Sekunden §7resettet");
            Bukkit.getScheduler().runTaskLater(MelonenCB.getInstance(), () -> {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (all.getWorld().getName().equalsIgnoreCase("world2")) {
                        all.teleport(MelonenCB.plotWorld.getSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                    }
                }
                Bukkit.getScheduler().runTaskLater(MelonenCB.getInstance(), Bukkit::shutdown, 5);
            }, 10 * 20);

        }

        if (args[0].equalsIgnoreCase("nether")) {
            if (!toConfirm.containsKey(p) || !toConfirm.get(p).equalsIgnoreCase(args[0])) {
                addToConfirm(p, args[0].toLowerCase());
                return true;
            }
            MelonenCB.resetNether = true;
            Bukkit.broadcastMessage(Constants.PREFIX + "§7Die §aNether-Welt §7wird in §b10 Sekunden §7resettet");
            Bukkit.getScheduler().runTaskLater(MelonenCB.getInstance(), () -> {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (all.getWorld().getName().equalsIgnoreCase("world_nether")) {
                        all.teleport(MelonenCB.plotWorld.getSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                    }
                }
                Bukkit.getScheduler().runTaskLater(MelonenCB.getInstance(), Bukkit::shutdown, 5);
            }, 10 * 20);
        }

        if (args[0].equalsIgnoreCase("end")) {
            if (!toConfirm.containsKey(p) || !toConfirm.get(p).equalsIgnoreCase(args[0])) {
                addToConfirm(p, args[0].toLowerCase());
                return true;
            }
            MelonenCB.resetEnd = true;
            Bukkit.broadcastMessage(Constants.PREFIX + "§7Die §aEnd-Welt §7wird in §b10 Sekunden §7resettet");
            Bukkit.getScheduler().runTaskLater(MelonenCB.getInstance(), () -> {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (all.getWorld().getName().equalsIgnoreCase("world_the_end")) {
                        all.teleport(MelonenCB.plotWorld.getSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                    }
                }
                Bukkit.getScheduler().runTaskLater(MelonenCB.getInstance(), Bukkit::shutdown, 5);
            }, 10 * 20);
        }

        if (args[0].equalsIgnoreCase("*") || args[0].equalsIgnoreCase("all")) {
            if (!toConfirm.containsKey(p) || !toConfirm.get(p).equalsIgnoreCase(args[0])) {
                addToConfirm(p, args[0].toLowerCase());
                return true;
            }
            MelonenCB.resetWorld = true;
            MelonenCB.resetNether = true;
            MelonenCB.resetEnd = true;
            Bukkit.broadcastMessage(Constants.PREFIX + "§7Alle §aWelten §7werden in §b10 Sekunden §7resettet");
            Bukkit.getScheduler().runTaskLater(MelonenCB.getInstance(), () -> {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (!all.getWorld().getUID().equals(MelonenCB.plotWorld.getUID())) {
                        all.teleport(MelonenCB.plotWorld.getSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                    }
                }
                Bukkit.getScheduler().runTaskLater(MelonenCB.getInstance(), Bukkit::shutdown, 5);
            }, 10 * 20);
        }


        return false;
    }

    public void addToConfirm(Player player, String argument) {
        toConfirm.put(player, argument);
        player.sendMessage(Constants.PREFIX + "§7Bitte bestätige innerhalb von §b10 Sekunden");
        Bukkit.getScheduler().runTaskLater(MelonenCB.getInstance(), () -> toConfirm.remove(player, argument), 10 * 20);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player))
            return new ArrayList<>();
        Player p = (Player) sender;
        if (!p.hasPermission("melonencb.reset")) {
            return new ArrayList<>();
        }
        List<String> tocomplete = new ArrayList<>();
        List<String> complete = new ArrayList<>();

        if (args.length == 1) {
            tocomplete.add("farmwelt");
            tocomplete.add("world");
            tocomplete.add("nether");
            tocomplete.add("end");
            tocomplete.add("*");
            tocomplete.add("all");
        }

        for (String tc : tocomplete) {
            if (tc.toLowerCase().startsWith(args[args.length - 1].toLowerCase())) {
                complete.add(tc);
            }
        }
        return complete;
    }
}
  
  