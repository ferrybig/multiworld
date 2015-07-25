/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.api.chunkgeneration.populators;

import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.generation.NativeBlockPopulator;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.NativeBlock;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.NativeChunk;

/**
 * The base class for al <code>BlockPopulators</code> that works only on the
 * surface
 *
 * @author Fernando
 */
public abstract class SurfacePopulator implements NativeBlockPopulator {

    @Override
    public void populateWorld(NativeChunk chunk) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                this.chanceBlock(x, z, chunk.getBlock(x, chunk.getHighestBlockYAt(x, z), z));
            }
        }
    }

    public abstract void chanceBlock(int x, int z, NativeBlock block);
}
