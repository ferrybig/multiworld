/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.api.chunkgeneration;

import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.NativeWorld;

/**
 *
 * @author Fernando
 */
public interface MultiWorldChunkGenerator {
    
    public short[][] createChunk(int x, int z, NativeWorld world);
    
}
