/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.worldgen.populators;

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
public class GlowstonePopulator implements NativeBlockPopulator {

    private final Random random;
    private final NativeMaterial air;
    private final NativeMaterial glowstone;
    private final NativeDirection[] blocks;

    public GlowstonePopulator(Random random, NativeMaterial air, NativeMaterial glowstone, NativeDirection[] blocks) {
        this.random = random;
        this.air = air;
        this.glowstone = glowstone;
        this.blocks = blocks;
    }

    @Override
    public void populateWorld(NativeChunk chunk) {
        // Add random glowstone
        for (int i = 0; i < 64; i++) {
            for (int tries = 0; tries < 4; tries++) {
                // pick out a random point
                int x = random.nextInt(14) + 1;
                int y = random.nextInt(250) + 3;
                int z = random.nextInt(14) + 1;
                NativeBlock mainBlock = chunk.getBlock(x, y, z);
                //skip if the block is air
                if (mainBlock.getType() == air) {
                    continue;
                }
                //skip if the block above or below is not an solid block
                if ((chunk.getBlock(x, y + 1, z).getType() != air)
                        && (chunk.getBlock(x, y - 1, z).getType() != air)) {
                    continue;
                }
                // Place glowstone 

                for (NativeDirection counter : blocks) {
                    NativeBlock tmp = mainBlock.getRelative(counter);
                    if (tmp.getType() != air) {
                        tmp.setTypeIdAndData(glowstone, (byte) 0, true);
                    }
                }
                break;
            }
        }
    }
}
