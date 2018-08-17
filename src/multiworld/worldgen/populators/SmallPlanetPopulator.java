package multiworld.worldgen.populators;

import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.Material;
import static org.bukkit.Material.*;

/**
 * Use the planet gen to make mutch, small planets
 * @version 1.2.0
 * @author Fernando
 */
public class SmallPlanetPopulator extends AbstractPlanetPopulator
{
	/**
	 * The main block where an planet from exists
	 */
	public static final Material[] ALLOWED_BLOCKS =
	{
		AIR, STONE, DIRT, COBBLESTONE, OAK_PLANKS, NETHERRACK, GLASS, OAK_LOG, BRICKS, CLAY
	};
	/**
	 * The materials where the top layer of an planet from exists
	 */
	public static final Material[] TOP_LAYER_BLOCK =
	{
		GRASS, GLOWSTONE, OAK_LOG, GLASS, SANDSTONE
	};
	/**
	 * The special blocks only at the middle of an planet
	 */
	public static final Material[] SPECIAL_BLOCKS =
	{
		AIR, GOLD_ORE, IRON_ORE, REDSTONE_ORE, LAPIS_ORE, CAKE, GOLD_BLOCK, IRON_BLOCK, DIAMOND_BLOCK, LAPIS_BLOCK, EMERALD_BLOCK
	};
	/**
	 * The number of planets it tries to make
	 */
	public static final int NUMBER_OFF_PLANETS = 4;
	/**
	 * The maximum size of an planet
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
	 * Populates the world with planets
	 * @param world the world to work on
	 * @param random the random to use
	 * @param source the source chunk
	 */
	@Override
	public void populate(World world, Random random, Chunk source)
	{
		if (populateDepth > MAX_POPULATE_DEPTH)
		{
			return;
		}
		populateDepth++;
		int planetX, planetY, planetZ, size;

		for (int i = 0; i < NUMBER_OFF_PLANETS; i++)
		{

			planetY = random.nextInt(world.getMaxHeight());
			size = random.nextInt(MAX_SIZE - MIN_SIZE) + MIN_SIZE;
			if (planetY + size > world.getMaxHeight() || planetY - size < 0)
			{
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
