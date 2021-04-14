// -----------------------
// Coded by Pandadoxo
// on 09.04.2021 at 12:09 
// -----------------------

package de.meloneoderso.melonencb.commands;

import de.meloneoderso.melonencb.Constants;
import de.meloneoderso.melonencb.MelonenCB;
import de.meloneoderso.melonencb.config.SpawnBlock;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Orientable;
import org.bukkit.block.data.Rotatable;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CreateSpawnCmd implements CommandExecutor, TabCompleter {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player))
            return true;
        Player p = (Player) sender;
        if (!p.hasPermission("melonencb.createspawn")) {
            p.sendMessage(Constants.PREFIXNOPERMS);
            return true;
        }

        if (args.length != 6) {
            p.sendMessage(Constants.PREFIX + "§7Falscher Syntax! Benutze §e/createspawn X1 Y1 Z1 X2 Y2 Z2");
            return true;
        }

        try {
            int X1 = Integer.parseInt(args[0]);
            int Y1 = Integer.parseInt(args[1]);
            int Z1 = Integer.parseInt(args[2]);
            int X2 = Integer.parseInt(args[3]);
            int Y2 = Integer.parseInt(args[4]);
            int Z2 = Integer.parseInt(args[5]);

            int minX = Math.min(X1, X2);
            int minY = Math.min(Y1, Y2);
            int minZ = Math.min(Z1, Z2);
            int maxX = Math.max(X1, X2);
            int maxY = Math.max(Y1, Y2);
            int maxZ = Math.max(Z1, Z2);

            List<SpawnBlock> spawnBlocks = new ArrayList<>();

            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    for (int x = minX; x <= maxX; x++) {
                        Block block = p.getWorld().getBlockAt(x, y, z);
                        if (block.getType().isAir() && y == minY) continue;
                        spawnBlocks.add(toSpawnBlock(block, x - minX, y - minY, z - minZ));
                    }
                }
            }

            MelonenCB.getResetConfig().setSpawnBlocks(spawnBlocks);
            MelonenCB.getResetConfig().setSpawnSizeX(maxX - minX);
            MelonenCB.getResetConfig().setSpawnSizeZ(maxZ - minZ);
            MelonenCB.getFilesUtil().save();
            p.sendMessage(Constants.PREFIX + "§7Das Spawngebäude wurde aktuallisiert");
        } catch (NumberFormatException ignored) {
            p.sendMessage(Constants.PREFIX + "§7Mindestens einer der angegebenen Argumente ist keine Zahl!");
            return true;
        }


        return false;
    }

    public SpawnBlock toSpawnBlock(Block block, int x, int y, int z) {
        Material material = block.getType();
        BlockFace blockFace = null;
        int faceType = 0;
        if (block.getState().getBlockData() instanceof Directional) {
            blockFace = ((Directional) block.getState().getBlockData()).getFacing();
            faceType = 1;
        } else if (block.getState().getBlockData() instanceof Rotatable) {
            blockFace = ((Rotatable) block.getState().getBlockData()).getRotation();
            faceType = 2;
        } else if (block.getState().getBlockData() instanceof Orientable) {
            faceType = 3;
            return new SpawnBlock(material, ((Orientable) block.getState().getBlockData()).getAxis(), faceType, x, y, z);
        }
        return new SpawnBlock(material, blockFace, faceType, x, y, z);
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player))
            return new ArrayList<>();
        Player p = (Player) sender;
        if (!p.hasPermission("melonencb.createspawn")) {
            return new ArrayList<>();
        }
        List<String> tocomplete = new ArrayList<>();
        List<String> complete = new ArrayList<>();

        Block b = ((Player) sender).getTargetBlockExact(5);
        if (b != null) {
            tocomplete.add(b.getX() + " " + b.getY() + " " + b.getZ());
        }

        for (String tc : tocomplete) {
            if (tc.toLowerCase().startsWith(args[args.length - 1].toLowerCase())) {
                complete.add(tc);
            }
        }
        return complete;
    }
}