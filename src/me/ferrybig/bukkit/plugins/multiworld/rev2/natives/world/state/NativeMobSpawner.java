/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.state;

import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.entities.NativeCreatureType;

/**
 *
 * @author Fernando
 */
public interface NativeMobSpawner extends NativeBlockState {
    public void setType(NativeCreatureType type);
    
    @Deprecated
    public NativeCreatureType getType();
}
