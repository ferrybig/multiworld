package me.ferrybig.bukkit.plugins.multiworld.rev2.api.chunkgeneration.populators;

import java.util.Random;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.NativeChunk;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.NativeWorld;

/**
 * Use the planet gen to make mutch, small planets
 *
 * @version 1.2.0
 * @author Fernando
 */
public class SmallPlanetPopulator extends AbstractPlanetPopulator {

    /**
     * The main block where an planet from exists
     */
    public static final byte[] ALLOWED_BLOCKS
            = {
                AIR, STONE, DIRT, 4, 5, 87, 20, 17, 43, 45, 82
            };
    /**
     * The materials where the top layer of an planet from exists
     */
    public static final byte[] TOP_LAYER_BLOCK
            = {
                GRASS, GLOW_STONE, 17, 20, 24, 44
            };
    /**
     * The special bloks only at the middle of an planet
     */
    public static final byte[] SPECIAL_BLOCKS
            = {
                AIR, 14, 15, 16, 21, 56, 92, 41, 42, DIAMOND_BLOCK, 22, 73
            };
    /**
     * The number of planets it tries to make
     */
    public static final int NUMBER_OF_PLANETS = 4;
    /**
     * The maximun size of an planet
     */
    public static final int MAX_SIZE = 7;
    /**
     * The minium size of an planet
     */
    public static final int MIN_SIZE = 2;
    /**
     * The maximum population depth
     */
    public static final int MAX_POPULATE_DEPTH = 3;
    /**
     * Auto abort populating when recursing to deep
     */
    private int populateDepth;

    /**
     * Populates the world whit planets
     *
     * @param source the source chunk
     */
    @Override
    public void populateWorld(NativeChunk source) {
        if (populateDepth > MAX_POPULATE_DEPTH) {
            return;
        }
        populateDepth++;
        int planetX, planetY, planetZ, size;
        Random random = new Random();
        NativeWorld world = source.getWorld();
        for (int i = 0; i < NUMBER_OF_PLANETS; i++) {

            planetY = random.nextInt(world.getMaxHeight());
            size = random.nextInt(MAX_SIZE - MIN_SIZE) + MIN_SIZE;
            if (planetY + size > world.getMaxHeight() || planetY - size < 0) {
                continue;
                //Reject this planet
            }
            planetX = random.nextInt(16) + (source.getX() << 4);
            planetZ = random.nextInt(16) + (source.getZ() << 4);

            this.makePlanet(world, planetX, planetY, planetZ, size, TOP_LAYER_BLOCK[random.nextInt(TOP_LAYER_BLOCK.length)], ALLOWED_BLOCKS[random.nextInt(ALLOWED_BLOCKS.length)],
                    SPECIAL_BLOCKS[random.nextInt(SPECIAL_BLOCKS.length)]);

        }
        populateDepth--;
    }
}
