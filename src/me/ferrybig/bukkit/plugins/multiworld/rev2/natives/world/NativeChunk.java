/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world;

/**
 *
 * @author Fernando
 */
public interface NativeChunk {
    public int getX();
    
    public int getZ();
    
    public NativeWorld getWorld();
    
    public boolean isPopulated();
    
    public int getHighestBlockYAt(int x, int z);

    public NativeBlock getBlock(int x, int highestBlockYAt, int z);
}
