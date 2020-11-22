package nl.ferrybig.multiworld.worldgen.populators;

import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.Material;
import static org.bukkit.Material.*;

/**
 * The populator that makes the big planets
 * @author Fernando
 */
public class BigPlanetPopulator extends AbstractPlanetPopulator
{
	/**
	 * The main block where an planet from exists
	 */
	public static final Material[] ALLOWED_BLOCKS =
	{
		STONE, DIRT, COBBLESTONE, SANDSTONE
	};
	/**
	 * The materials where the top layer of an planet from exists
	 */
	public static final Material[] TOP_LAYER_BLOCK =
	{
		GRASS, NETHERRACK, SOUL_SAND, SAND
	};
	/**
	 * The special blocks only at the middle of an planet
	 */
	public static final Material[] SPECIAL_BLOCKS =
	{
		AIR, GOLD_ORE, IRON_ORE, REDSTONE_ORE, LAPIS_ORE, CAKE, GOLD_BLOCK, IRON_BLOCK, DIAMOND_BLOCK, LAPIS_BLOCK, EMERALD_BLOCK
	};
	/**
	 * The maximum size of an planet
	 */
	public static final int MAX_SIZE = 30;
	/**
	 * The minium size of an planet
	 */
	public static final int MIN_SIZE = 5;
	/**
	 * The maximum population depth
	 */
	public static final int MAX_POPULATE_DEPTH = 1;
	/**
	 * When this is higher than MAX_POPULATE_DEPTH, don't populate this chunk
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
		if (this.populateDepth > MAX_POPULATE_DEPTH)
		{
			return;
		}
		if (random.nextInt(8) == 0)
		{
			try
			{
				this.populateDepth++;
				int planetX = random.nextInt(16) + (source.getX() << 4);
				int planetY = random.nextInt(world.getSeaLevel()) + (64 - MIN_SIZE);
				int planetZ = random.nextInt(16) + (source.getZ() << 4);
				int size = random.nextInt(MAX_SIZE - MIN_SIZE) + MIN_SIZE;
				if (!(planetY + size > world.getMaxHeight() || planetY - size < 0))
				{
					this.makePlanet(world,
							planetX,
							planetY,
							planetZ,
							size,
							TOP_LAYER_BLOCK[random.nextInt(TOP_LAYER_BLOCK.length)],
							ALLOWED_BLOCKS[random.nextInt(ALLOWED_BLOCKS.length)],
							SPECIAL_BLOCKS[random.nextInt(SPECIAL_BLOCKS.length)]);

				}
			}
			finally
			{
				this.populateDepth--;
			}
		}

	}
}
