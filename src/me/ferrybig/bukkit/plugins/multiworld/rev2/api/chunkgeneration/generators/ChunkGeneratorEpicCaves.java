/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.api.chunkgeneration.generators;

import java.util.List;
import java.util.Random;
import me.ferrybig.bukkit.plugins.multiworld.rev2.api.chunkgeneration.util.ChunkMaker;
import me.ferrybig.bukkit.plugins.multiworld.rev2.api.chunkgeneration.util.DefaultChunkMaker;
import me.ferrybig.bukkit.plugins.multiworld.rev2.api.chunkgeneration.util.SimplexOctaveGenerator;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.NativeWorld;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.generation.NativeChunkGenerator;

/**
 *
 * @author Fernando
 */
public class ChunkGeneratorEpicCaves implements NativeChunkGenerator {

    private static final int OCTAVES = 8;
    private static final double AMP = 0.7;
    private final SimplexOctaveGenerator gen;
    private final double scale = 32.0; //how far apart the tops of the hills are
    private final double max;

    public ChunkGeneratorEpicCaves(long seed) {
        gen = new SimplexOctaveGenerator(new Random(seed), OCTAVES);
        gen.setScale(1 / scale); //The distance between peaks of the terrain. Scroll down more to see what happens when you play with this

        double max = 0;
        double amp = 1;
        for (int i = 0; i < OCTAVES; i++) {
            max += amp;
            amp *= AMP;
        }
        this.max = max;
    }

    @Override
    public short[][] createChunk(int ChunkX, int ChunkZ, NativeWorld world) {
        ChunkMaker chunk = new DefaultChunkMaker(world.getMaxHeight());
        double threshold;
        for (int y = 255; y > 0; y--) {
            threshold = ((double) y - 128) / 128 * max;
            for (int x = 0; x < 16; x++) {
                int real_x = x + ChunkX * 16;
                for (int z = 0; z < 16; z++) {
                    int real_z = z + ChunkZ * 16;
                    if (gen.noise(real_x, y, real_z, 0.5, AMP) > threshold) {
                        chunk.setBlock(x, y, z, (short) 1); // 1 should be stone on most platforms
                    }
                }
            }
        }
        return chunk.getRawChunk();
    }
}
