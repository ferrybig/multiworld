/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.state;

import java.util.Collection;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.inventory.NativeInventory;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.NativeBlock;

/**
 *
 * @author Fernando
 */
public interface NativeBlockState {
    public NativeBlock getBlock();
    
    public boolean commitChanges(boolean force);
    
    public boolean hasInventory();
    
    public NativeInventory getInventory();
    
    public boolean canBeTurned();
    
    public Collection<NativeDirection> getValidDirections();
    
    public void setDirection(NativeDirection dir);
    
    public void setDirection(NativeDirection dir, boolean force);
    
    public NativeDirection getDirection();
    
    public byte[] blockInformationToBytes();
    
    public boolean blockInformationFromBytes(byte[] data);
}
