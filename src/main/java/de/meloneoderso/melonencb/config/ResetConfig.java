// -----------------------
// Coded by Pandadoxo
// on 06.04.2021 at 11:04 
// -----------------------

package de.meloneoderso.melonencb.config;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ResetConfig {

    int spawnSizeX;
    int spawnSizeZ;
    private long lastReset;
    private boolean autoReset;
    private int spawnRadius;
    private int interval;
    private TimeUnit timeUnit;
    private List<SpawnBlock> spawnBlocks;

    public ResetConfig() {
        this.lastReset = 0;
        this.autoReset = true;
        this.spawnRadius = 25;
        this.interval = 14;
        this.timeUnit = TimeUnit.DAYS;
        this.spawnBlocks = new ArrayList<>();
        this.spawnSizeX = 0;
        this.spawnSizeZ = 0;
    }

    public long getNextReset() {
        return lastReset + timeUnit.toMillis(interval);
    }

    public String getNextResetTime() {
        return getFormat().format(getNextReset()) + " Uhr";
    }

    public String getLastResetTime() {
        return getFormat().format(lastReset) + " Uhr";
    }

    private SimpleDateFormat getFormat() {
        return new SimpleDateFormat("dd.MM.YY - HH:mm");
    }

    public long getLastReset() {
        return lastReset;
    }

    public void setLastReset(long lastReset) {
        this.lastReset = lastReset;
    }

    public boolean isAutoReset() {
        return autoReset;
    }

    public void setAutoReset(boolean autoReset) {
        this.autoReset = autoReset;
    }

    public int getSpawnRadius() {
        return spawnRadius;
    }

    public void setSpawnRadius(int spawnRadius) {
        this.spawnRadius = spawnRadius;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public List<SpawnBlock> getSpawnBlocks() {
        return spawnBlocks;
    }

    public void setSpawnBlocks(List<SpawnBlock> spawnBlocks) {
        this.spawnBlocks = spawnBlocks;
    }

    public int getSpawnSizeX() {
        return spawnSizeX;
    }

    public void setSpawnSizeX(int spawnSizeX) {
        this.spawnSizeX = spawnSizeX;
    }

    public int getSpawnSizeZ() {
        return spawnSizeZ;
    }

    public void setSpawnSizeZ(int spawnSizeZ) {
        this.spawnSizeZ = spawnSizeZ;
    }
}
