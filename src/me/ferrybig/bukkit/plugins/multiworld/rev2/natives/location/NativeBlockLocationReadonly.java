/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.natives.location;

import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.NativeBlock;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.NativeChunk;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.NativeWorld;

/**
 *
 * @author Fernando
 */
public interface NativeBlockLocationReadonly {

    public NativeChunk getChunk();

    public NativeWorld getWorld();

    public int getBlockX();

    public int getBlockY();

    public int getBlockZ();

    public NativeLocation asLocation();

    public NativeBlock asBlock();
}
