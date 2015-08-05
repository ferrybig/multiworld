/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.natives.location;

/**
 *
 * @author Fernando
 */
public interface NativeBlockLocation extends NativeBlockLocationReadonly {
    public int setBlockX(int x);

    public int setBlockY(int y);

    public int setBlockZ(int z);
    
    
}
