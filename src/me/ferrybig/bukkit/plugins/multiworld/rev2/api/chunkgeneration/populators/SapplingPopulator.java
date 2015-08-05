/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.api.chunkgeneration.populators;

import java.util.Random;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.materials.NativeMaterial;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.NativeBlock;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.NativeChunk;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.generation.NativeBlockPopulator;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.location.NativeDirection;

/**
 *
 * @author Fernando
 */
public class SapplingPopulator implements NativeBlockPopulator {

    private final Random random;
    private final NativeMaterial air;
    private final NativeMaterial sappling;
    private final NativeMaterial grass;

    public SapplingPopulator(Random random, NativeMaterial air, NativeMaterial sappling, NativeMaterial grass) {
        this.random = random;
        this.air = air;
        this.sappling = sappling;
        this.grass = grass;
    }

    @Override
    public void populateWorld(NativeChunk chunk) {
        // Add random glowstone
        for (int i = 0; i < 64; i++) {
            // pick out a random point
            int x = random.nextInt(14) + 1;
            int y = random.nextInt(250) + 3;
            int z = random.nextInt(14) + 1;
            NativeBlock mainBlock = chunk.getBlock(x, y, z);
            //skip if the block is air
            if (mainBlock.getType() != air) {
                continue;
            }
            if (mainBlock.getRelative(NativeDirection.DOWN).getType() != grass) {
                continue;
            }
            if (mainBlock.getLightLevel() < 9) {
                continue;
            }
            mainBlock.setType(sappling);

        }
    }
}
