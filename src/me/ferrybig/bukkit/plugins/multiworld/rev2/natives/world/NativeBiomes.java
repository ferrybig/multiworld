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
public interface NativeBiomes {
    public NativeBiome getBiome(String fullname);
    
    public NativeBiome requireBiome(String fullname);
    
    public NativeBiome requireOrDefaultBiome(String fullname);
    
    public NativeBiome getDefault();
}
