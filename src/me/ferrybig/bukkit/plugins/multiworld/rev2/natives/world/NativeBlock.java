/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world;

import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.location.NativeLocation;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.location.NativeBlockLocationReadonly;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.materials.NativeMaterial;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.state.NativeBlockState;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.location.NativeDirection;

/**
 *
 * @author Fernando
 */
public interface NativeBlock extends NativeBlockLocationReadonly {
    
    public void setTypeIdAndData(short block, byte data, boolean fullupdate);

    public NativeMaterial getType();

    public void setType(NativeMaterial requireMaterial);
    
    public NativeBlockState getBlockState();

    public NativeBiome getBiome();

    public NativeBlock getRelative(NativeDirection counter);

    public void setTypeIdAndData(NativeMaterial block, byte data, boolean fullupdate);

    public int getLightLevel();

    /**
     * Apply snow biome effects to this block.
     * normal block -> block on top will be snow
     * water -> ice
     * leaves -> snow on top + repeat for blocks below
     * transparent -> no effect
     * @return 
     */
    public boolean applySnowBiomeEffects();

}
