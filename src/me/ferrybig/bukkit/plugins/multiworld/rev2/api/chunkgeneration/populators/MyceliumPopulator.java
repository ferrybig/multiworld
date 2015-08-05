/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.api.chunkgeneration.populators;

import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.materials.NativeMaterialManager;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.NativeBlock;

/**
 * The class for 1.9 that handles the grass at Mycelium biomess
 *
 * @author Fernando
 */
public class MyceliumPopulator extends SurfacePopulator {

    private final NativeMaterialManager materials;

    public MyceliumPopulator(NativeMaterialManager materials) {
        this.materials = materials;
    }

    @Override
    public void chanceBlock(int x, int z, NativeBlock block) {
        if ((block.getBiome().name().contains("mushroom"))) {
            if ((block.getType().name().equals("minecraft:grass"))) {
                block.setType(materials.requireMaterial("minecraft:mycelium"));
            }
        }
    }
}
