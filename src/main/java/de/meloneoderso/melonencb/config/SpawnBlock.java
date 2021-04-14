// -----------------------
// Coded by Pandadoxo
// on 09.04.2021 at 12:23 
// -----------------------

package de.meloneoderso.melonencb.config;

import org.bukkit.Axis;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

public class SpawnBlock {

    private Material material;
    private BlockFace blockFace;
    private Axis axis;
    private int faceType; //1 == dir, 2 == rot, 3 == ori
    private int x;
    private int y;
    private int z;

    public SpawnBlock(Material material, BlockFace blockFace, int faceType, int x, int y, int z) {
        this.material = material;
        this.blockFace = blockFace;
        this.faceType = faceType;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public SpawnBlock(Material material, Axis axis, int faceType, int x, int y, int z) {
        this.material = material;
        this.axis = axis;
        this.faceType = faceType;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public BlockFace getBlockFace() {
        return blockFace;
    }

    public void setBlockFace(BlockFace blockFace) {
        this.blockFace = blockFace;
    }

    public int getFaceType() {
        return faceType;
    }

    public void setFaceType(int faceType) {
        this.faceType = faceType;
    }

    public Axis getAxis() {
        return axis;
    }

    public void setAxis(Axis axis) {
        this.axis = axis;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
