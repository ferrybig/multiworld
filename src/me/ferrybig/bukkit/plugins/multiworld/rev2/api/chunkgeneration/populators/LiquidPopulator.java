/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.api.chunkgeneration.populators;

import java.util.Random;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.materials.NativeMaterial;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.materials.NativeMaterialManager;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.NativeChunk;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.generation.NativeBlockPopulator;

/**
 *
 * @author Fernando
 */
public class LiquidPopulator implements NativeBlockPopulator {
    private final NativeMaterial water;
    private final NativeMaterial lava;
    private final NativeMaterial air;

    public LiquidPopulator(NativeMaterialManager materials) {
        this.water = materials.requireMaterial("minecraft:water");
        this.lava = materials.requireMaterial("minecraft:lava");
        this.air = materials.requireMaterial("minecraft:air");
    }
    
    @Override
    public void populateWorld(NativeChunk chunk) {
        Random random = new Random();
        // Add random water
        this.addRandomLiquidBlock(chunk, random, 4, water);
        //add random lava
        this.addRandomLiquidBlock(chunk, random, 8, lava);
    }

    public void addRandomLiquidBlock(NativeChunk chunk, Random random, int tries, NativeMaterial blockId) {
        for (int i = 0; i < tries; i++) {
            // pick out a random point
            int x = random.nextInt(14) + 1;
            int y = random.nextInt(250) + 3;
            int z = random.nextInt(14) + 1;

            //skip if the selected block is not an solid block
            if (chunk.getBlock(x, y, z).getType() == air) {
                continue;
            }

            //skip if the block above is not solid
            if (chunk.getBlock(x, y + 1, z).getType() == air) {
                continue;
            }

            int emptySides = 0;
            if (chunk.getBlock(x + 1, y, z).getType() == air) {
                emptySides++;
            }
            if (chunk.getBlock(x, y, z + 1).getType() == air) {
                emptySides++;
            }
            if (chunk.getBlock(x - 1, y, z).getType() == air) {
                emptySides++;
            }
            if (chunk.getBlock(x, y, z - 1).getType() == air) {
                emptySides++;
            }
            if (chunk.getBlock(x, y - 1, z).getType() == air) {
                emptySides++;
            }
            if (emptySides < 3 && emptySides != 0) {
                // Place block
                chunk.getBlock(x, y, z).setType(blockId);
            }
        }
    }
}
