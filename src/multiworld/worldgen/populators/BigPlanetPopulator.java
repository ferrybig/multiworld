package multiworld.worldgen.populators;

import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.World;

/**
 * The populator that makes the big planets
 * @author Fernando
 */
public class BigPlanetPopulator extends AbstractPlanetPopulator
{
	/**
	 * The main block where an planet from exists
	 */
	public static final byte[] ALLOWED_BLOCKS =
	{
		STONE, DIRT, 4, 24
	};
	/**
	 * The materials where the top layer of an planet from exists
	 */
	public static final byte[] TOP_LAYER_BLOCK =
	{
		GRASS, 87, 88, 12
	};
	/**
	 * The special bloks only at the middle of an planet
	 */
	public static final byte[] SPECIAL_BLOCKS =
	{
		DIRT, 14, 15, 16, 21, 56, 92, 41, 42, 22, 73
	};
	/**
	 * The maximun size of an planet
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
	 * Populates the world whit planets
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
