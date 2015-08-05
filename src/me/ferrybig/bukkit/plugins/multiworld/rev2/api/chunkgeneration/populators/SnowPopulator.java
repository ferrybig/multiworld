/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.api.chunkgeneration.populators;

import java.util.Set;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.materials.NativeMaterials;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.NativeBiome;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.NativeBiomes;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.NativeBlock;

/**
 * Add snow and ice to worlds
 *
 * @author Fernando
 */
public class SnowPopulator extends SurfacePopulator {

    /**
     * Check if it must add snow, and add it if its needed
     *
     * @param x
     * @param z
     * @param block
     */
    @Override
    public void chanceBlock(int x, int z, NativeBlock block) {
        if (block.getBiome().containsSnow()) {
            if (block.getBiome().getSnowHeigth() <= 0 || block.getBlockY() > block.getBiome().getSnowHeigth()) {
                block.applySnowBiomeEffects();
            }
        }
    }
}
