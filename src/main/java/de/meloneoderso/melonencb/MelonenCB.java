package de.meloneoderso.melonencb;

import com.earth2me.essentials.Essentials;
import de.meloneoderso.melonencb.commands.CommandEditor;
import de.meloneoderso.melonencb.commands.CreateSpawnCmd;
import de.meloneoderso.melonencb.commands.LoadConfigCommand;
import de.meloneoderso.melonencb.commands.ResetCommand;
import de.meloneoderso.melonencb.config.ResetConfig;
import de.meloneoderso.melonencb.config.SpawnBlock;
import de.meloneoderso.melonencb.listener.*;
import de.meloneoderso.melonencb.utils.FilesUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Orientable;
import org.bukkit.block.data.Rotatable;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.Calendar;

import static de.meloneoderso.melonencb.Constants.PREFIXNOPERMS;

public final class MelonenCB extends JavaPlugin {

    public static boolean reset;
    public static boolean resetWorld;
    public static boolean resetNether;
    public static boolean resetEnd;
    public static World plotWorld;

    private static MelonenCB instance;
    private static ResetConfig resetConfig;
    private static FilesUtil filesUtil;


    private boolean sentAlert;

    public static MelonenCB getInstance() {
        return instance;
    }

    public static ResetConfig getResetConfig() {
        return resetConfig;
    }

    public static void setResetConfig(ResetConfig resetConfig) {
        MelonenCB.resetConfig = resetConfig;
    }

    public static FilesUtil getFilesUtil() {
        return filesUtil;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        resetConfig = new ResetConfig();
        filesUtil = new FilesUtil();

        plotWorld = Bukkit.getWorld("citybuild");
        if (plotWorld == null) {
            if (Arrays.stream(Bukkit.getWorldContainer().listFiles()).noneMatch(f -> f.isDirectory() && f.getName().equalsIgnoreCase("citybuild"))) {
                Bukkit.getConsoleSender().sendMessage("§4§lPlotwelt ist nicht geladen");
                Bukkit.getPluginManager().disablePlugin(this);
                return;
            } else {
                plotWorld = Bukkit.createWorld(WorldCreator.name("citybuild"));
            }
        }

        World world = Bukkit.getWorld("world2");
        World world_nether = Bukkit.getWorld("world_nether");
        World world_the_end = Bukkit.getWorld("world_the_end");

        if (world == null) {
            if (Arrays.stream(Bukkit.getWorldContainer().listFiles()).noneMatch(f -> f.isDirectory() && f.getName().equalsIgnoreCase("citybuild"))) {
                world = Bukkit.createWorld(WorldCreator.name("world2"));
            } else {
                world = new WorldCreator("world2").environment(World.Environment.NORMAL).createWorld();
            }
        }


        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new CommandListener(), this);
        manager.registerEvents(new UnknownCommandListener(), this);
        manager.registerEvents(new JoinQuitListener(), this);
        manager.registerEvents(new PortalUseListener(), this);
        manager.registerEvents(new SpawnProtectionListener(), this);

        getCommand("reset").setExecutor(new ResetCommand());
        getCommand("loadConfig").setExecutor(new LoadConfigCommand());
        getCommand("createSpawn").setExecutor(new CreateSpawnCmd());

        //CommandBlock
        new CommandEditor("about").add(PREFIXNOPERMS);
        new CommandEditor("version").add(PREFIXNOPERMS);
        new CommandEditor("ver").add(PREFIXNOPERMS);


        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        if (resetConfig.getLastReset() == 0L) {
            resetConfig.setLastReset(c.getTimeInMillis());
        }


        World finalWorld = world;
        Bukkit.getScheduler().runTaskLater(this, () -> {
            buildSpawn(finalWorld);
            buildSpawn(world_nether);

            Essentials es = Essentials.getPlugin(Essentials.class);
            try {
                es.getWarps().setWarp("Farmwelt", finalWorld.getSpawnLocation().clone().add(.5, 0, .5));
                es.getWarps().setWarp("Nether", world_nether.getSpawnLocation().clone().add(.5, 0, .5));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 60);

        Bukkit.getScheduler().runTaskTimer(instance, this::tick, 0, 1);
    }

    public boolean isSentAlert() {
        return sentAlert;
    }

    public void tick() {
        if (System.currentTimeMillis() + 15 * 60 * 1000 >= resetConfig.getNextReset()) {
            if (!sentAlert) {
                sentAlert = true;
                Bukkit.broadcastMessage(Constants.PREFIX + "§cDie Farmwelten werden in §a15 Minuten §czurückgesetzt! Bitte sammelt all eure" +
                        " wichtigen Items ein und bereitet euch darauf vor");
            }
            if (System.currentTimeMillis() >= resetConfig.getNextReset()) {
                Bukkit.broadcastMessage(Constants.PREFIX + "§cDie Farmwelten werden nun zurückgesetzt!");
                resetWorld = true;
                resetNether = true;
                resetEnd = true;
                for (Player all : Bukkit.getOnlinePlayers()) {
                    all.teleport(MelonenCB.plotWorld.getSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                }
                Bukkit.getScheduler().runTaskLater(MelonenCB.getInstance(), Bukkit::shutdown, 20);
                Bukkit.shutdown();
            }
        } else {
            sentAlert = false;
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        reset();
        filesUtil.save();
    }

    private void reset() {
        File[] filesInMainDirectory = Bukkit.getWorldContainer().listFiles();
        for (File file : filesInMainDirectory) {
            if ((resetWorld && file.getName().equalsIgnoreCase("world2")) ||
                    (resetNether && file.getName().equalsIgnoreCase("world_nether")) ||
                    (resetEnd && file.getName().equalsIgnoreCase("world_the_end"))) {
                World w = Bukkit.getWorld(file.getName());
                if (w != null) {
                    w.setAutoSave(false);
                    Bukkit.unloadWorld(w, false);
                    deleteWorld(file);
                    resetConfig.setLastReset(System.currentTimeMillis());
                }
            }
        }
    }

    private void deleteWorld(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    if (files[i].listFiles().length == 0)
                        files[i].delete();
                    else
                        deleteWorld(files[i]);
                } else {
                    files[i].delete();
                }
                path.delete();
            }
        }
    }

    private void buildSpawn(World world) {
        int startX = world.getSpawnLocation().getBlockX() - (MelonenCB.getResetConfig().getSpawnSizeX() / 2);
        int startY = world.getSpawnLocation().getBlockY() - 1;
        int startZ = world.getSpawnLocation().getBlockZ() - (MelonenCB.getResetConfig().getSpawnSizeZ() / 2);

        for (SpawnBlock block : MelonenCB.getResetConfig().getSpawnBlocks()) {
            Block b = world.getBlockAt(startX + block.getX(), startY + block.getY(), startZ + block.getZ());
            b.setType(block.getMaterial());
            if (block.getBlockFace() != null || block.getAxis() != null) {
                BlockState state = b.getState();
                if (block.getFaceType() == 1) {
                    Directional data = ((Directional) state.getBlockData());
                    data.setFacing(block.getBlockFace());
                    state.update(true);
                    b.setBlockData(data);
                } else if (block.getFaceType() == 2) {
                    Rotatable data = ((Rotatable) state.getBlockData());
                    data.setRotation(block.getBlockFace());
                    state.update(true);
                    b.setBlockData(data);
                } else if (block.getFaceType() == 3) {
                    Orientable data = ((Orientable) state.getBlockData());
                    data.setAxis(block.getAxis());
                    state.update(true);
                    b.setBlockData(data);
                }
            }
        }

        startX = world.getSpawnLocation().getBlockX();
        startZ = world.getSpawnLocation().getBlockZ();
        int spawnRadius = MelonenCB.getResetConfig().getSpawnRadius();
        for (int z = startZ - spawnRadius; z <= startZ + spawnRadius; z++) {
            for (int x = startX - spawnRadius; x <= startX + spawnRadius; x++) {
                boolean b = x == startX - spawnRadius || x == startX + spawnRadius;
                boolean b1 = z == startZ - spawnRadius || z == startZ + spawnRadius;
                if (b || b1) {
                    int y = world.getHighestBlockYAt(x, z);
                    for (int i = y; i > 0; i--) {
                        Block block = world.getBlockAt(x, i, z);
                        if (!block.getType().isAir()) {
                            block.setType(Material.BEDROCK);
                        }
                    }
                }
            }
        }
    }

    public int getHighestYBelow(World world, int x, int z, int below) {
        for (int y = below; y >= 0; y--) {
            if (!world.getBlockAt(x, y, z).getType().isAir()) {
                return y;
            }
        }
        return 0;
    }
}
