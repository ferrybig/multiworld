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
public interface NativeBiome {
    public boolean containsSnow();
    
    public int getSnowHeigth();
    
    public String name();
    
    public boolean containsVillages();
    
    public boolean containsDessertTemples();
    
    public boolean containsCaves();
    
    public boolean containsMonsterSpawning();
    
    public boolean containsDungeons();
    
    public boolean isVanillaBiome();
}
