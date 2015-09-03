/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Fernando
 */
public interface NativeBiomeManager {
    public NativeBiome getBiome(String fullname);
    
    public NativeBiome requireBiome(String fullname);
    
    public NativeBiome requireOrDefaultBiome(String fullname);
    
    public NativeBiome getDefault();
    
    public Collection<? extends NativeBiome> getBiomes();
    
    public Set<NativeBiome> getEmptyBiomeSet();
    
    public <T> Map<NativeBiome,T> getEmptyBiomeMap();
}
