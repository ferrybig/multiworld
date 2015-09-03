/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.generation;

import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.NativeChunk;

/**
 *
 * @author Fernando
 */
public interface NativeBlockPopulator {
    public void populateWorld(NativeChunk chunk);
}
