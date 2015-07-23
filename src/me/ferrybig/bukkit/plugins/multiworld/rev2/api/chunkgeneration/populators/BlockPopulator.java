/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.api.chunkgeneration.populators;

import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.NativeChunk;

/**
 *
 * @author Fernando
 */
public interface BlockPopulator {
    public void populateWorld(NativeChunk chunk);
}
