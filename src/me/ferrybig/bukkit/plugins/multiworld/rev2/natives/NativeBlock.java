/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.natives;

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

    public void setTypeIdAndData(byte blockTop, byte b, boolean b0);
}