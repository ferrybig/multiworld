/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.natives.inventory;

import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.materials.NativeMaterial;

/**
 *
 * @author Fernando
 */
public interface NativeItemStack {
    public NativeMaterial getType();
    
    public byte getAmount();
    
    public short getDamage();
    
    public NativeItemMeta getMeta();
}
