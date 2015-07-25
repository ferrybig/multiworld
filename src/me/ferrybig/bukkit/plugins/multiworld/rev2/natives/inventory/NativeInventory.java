/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.natives.inventory;

import java.util.Collection;

/**
 *
 * @author Fernando
 */
public interface NativeInventory {
    public int getSize();
    
    public Collection<? extends NativeItemStack> addItem(NativeItemStack ... item);
    
    public void setItem(int slot, NativeItemStack item);
    
    public NativeItemStack getItem(int slot);
}
