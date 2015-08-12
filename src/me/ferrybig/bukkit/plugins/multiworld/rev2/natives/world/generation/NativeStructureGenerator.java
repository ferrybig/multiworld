/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.generation;

import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.NativeChunkGrid;

/**
 *
 * @author Fernando
 */
public interface NativeStructureGenerator {
    public int getMaxStructureSize();
    
    public boolean shouldGenerateAt(int chunkX, int chunkZ);
    
    public void generateStructure(int chunkX, int chunkZ, NativeChunkGrid grid);
}
