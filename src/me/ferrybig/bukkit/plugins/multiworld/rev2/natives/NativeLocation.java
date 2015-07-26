/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.natives;

import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.NativeBlock;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.NativeWorld;

/**
 *
 * @author Fernando
 */
public interface NativeLocation {

    public NativeWorld getWorld();

    public double getX();

    public double getY();

    public double getZ();
    
    public int getBlockX();

    public int getBlockY();

    public int getBlockZ();

    public double distance(NativeLocation loc);
    
    public NativeBlock asBlock();
    
}
