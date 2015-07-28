/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world;

import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.NativeLocation;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.materials.NativeMaterial;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.state.NativeBlockState;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.state.NativeDirection;

/**
 *
 * @author Fernando
 */
public interface NativeBlock {
    public NativeChunk getChunk();
    
    public NativeWorld getWorld();
    
    public int getBlockX();

    public int getBlockY();

    public int getBlockZ();
    
    public NativeLocation asLocation();

    public void setTypeIdAndData(short block, byte data, boolean fullupdate);

    public NativeMaterial getType();

    public void setType(NativeMaterial requireMaterial);
    
    public NativeBlockState getBlockState();

    public NativeBiome getBiome();

    public NativeBlock getRelative(NativeDirection counter);

    public void setTypeIdAndData(NativeMaterial block, byte data, boolean fullupdate);
}
