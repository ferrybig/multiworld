/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ferrybig.multiworld.worldgen;

import java.util.List;
import java.util.Random;
import nl.ferrybig.multiworld.WorldGenException;
import nl.ferrybig.multiworld.data.InternalWorld;
import nl.ferrybig.multiworld.worldgen.populators.LiquidPopulator;
import nl.ferrybig.multiworld.worldgen.populators.OrePopulator;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

/**
 *
 * @author Fernando
 */
public class FlyingIslandsGenerator extends ChunkGenerator implements ChunkGen
{
	/**
	 *
	 * @param x X co-ordinate of the block to be set in the array
	 * @param y Y co-ordinate of the block to be set in the array
	 * @param z Z co-ordinate of the block to be set in the array
	 * @param chunk An array containing the Block id's of all the blocks in the chunk. The first offset is the block section number. There are 16 block sections, stacked vertically, each of which
	 * 16 by 16 by 16 blocks.
	 * @param material The material to set the block to.
	 */
	private void setBlock(int x, int y, int z, ChunkData chunk, Material material)
	{
		chunk.setBlock(x, y, z, material);
	}

	private Material getBlock(int x, int y, int z, ChunkData chunk)
	{
		return chunk.getType(x, y, z);
	}

	@Override
	public List<BlockPopulator> getDefaultPopulators(World world)
	{
		List<BlockPopulator> pop = super.getDefaultPopulators(world);
		pop.add(new BlockPopulator()
		{
			@Override
			public void populate(World world, Random random, Chunk chunk)
			{
				for (int x = 0; x < 16; x++)
				{
					for (int z = 0; z < 16; z++)
					{
						Block bl = world.getHighestBlockAt(chunk.getX() * 16 + x, chunk.getZ() * 16 + z);
						if (bl.isEmpty())
						{
							bl = bl.getRelative(BlockFace.DOWN);
						}
						if (bl.getType() == Material.GRASS || bl.getType() == Material.DIRT)
						{
							if (random.nextInt(16) == 1)
							{
								bl.getRelative(BlockFace.UP).setType(Material.JUNGLE_SAPLING); // todo mark the sappling as ready to grow
							}
						}
					}
				}
			}
		});
		pop.add(new OrePopulator());
		pop.add(new LiquidPopulator());
		pop.add(new LiquidPopulator());
		return pop;
	}

	@Override
	public ChunkData generateChunkData(World world, Random random, int ChunkX, int ChunkZ, ChunkGenerator.BiomeGrid biomes)
	{
		ChunkData chunk = super.createChunkData(world);

		SimplexOctaveGenerator overhangs = new SimplexOctaveGenerator(world, 8);
		SimplexOctaveGenerator bottoms = new SimplexOctaveGenerator(world, 8);
		SimplexOctaveGenerator oceanGen = new SimplexOctaveGenerator(world, 8);
		SimplexOctaveGenerator sandGen = new SimplexOctaveGenerator(world, 8);

		overhangs.setScale(1 / 64.0); //little note: the .0 is VERY important
		bottoms.setScale(1 / 256.0);
		oceanGen.setScale(0.03125D);
		sandGen.setScale(1 / 16.0);

		int overhangsMagnitude = 32; //used when we generate the noise for the tops of the overhangs
		int bottomsMagnitude = 16;

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				int realX = x + ChunkX * 16;
				int realZ = z + ChunkZ * 16;
				biomes.setBiome(x, z, Biome.OCEAN);


				//int bottomHeight = 32;
				int bottomHeight = (int) (bottoms.noise(realX, realZ, 0.6, 0.5) * bottomsMagnitude + 64) + 16;
				int maxHeight = (int) overhangs.noise(realX, realZ, 0.5, 0.6) * overhangsMagnitude + bottomHeight + 32;
				double seaNoise = (oceanGen.noise(realX, 48, realZ, 0.5D, 0.5D, true) + 1) * 13;
				double sandNoise = (sandGen.noise(realX, seaNoise, realZ, 0.5D, 0.5D, true) + 1) * 1.5;

				int floodHeight = 48 + (int) Math.round(seaNoise);

				for (int y = 0; y < maxHeight; y++)
				{
					if (y <= floodHeight)
					{
						setBlock(x, y, z, chunk, Material.STONE);
					}
					else if (y > bottomHeight)
					{ //part where we do the overhangs
						double density = overhangs.noise(realX, y, realZ, 0.5, 0.5);

						if (density > 0.3 && density < 0.7)
						{
							setBlock(x, y, z, chunk, Material.STONE);
							biomes.setBiome(x, z, Biome.JUNGLE);
						}
						else if (y < 80)
						{
							setBlock(x, y, z, chunk, Material.WATER);
						}
					}
					else if (y < 80)
					{
						setBlock(x, y, z, chunk, Material.WATER);
					}

				}

				for (int y = bottomHeight + 1; y < maxHeight; y++)
				{
					Material thisblock = getBlock(x, y, z, chunk);
					Material blockabove = getBlock(x, y + 1, z, chunk);

					if (thisblock == Material.STONE)
					{
						if (blockabove == Material.AIR)
						{
							if (random.nextInt(8) == 0)
							{
								if (random.nextBoolean())
								{
									setBlock(x, y + 1, z, chunk, Material.SUNFLOWER);
								}
								else
								{
									setBlock(x, y + 1, z, chunk, Material.ROSE_BUSH);
								}

							}
							setBlock(x, y, z, chunk, Material.GRASS);
							if (getBlock(x, y - 1, z, chunk) == Material.STONE)
							{
								setBlock(x, y - 1, z, chunk, Material.DIRT);
								if (getBlock(x, y - 2, z, chunk) == Material.STONE)
								{
									setBlock(x, y - 2, z, chunk, Material.DIRT);
									if (getBlock(x, y - 3, z, chunk) == Material.STONE)
									{
										setBlock(x, y - 3, z, chunk, Material.DIRT);
									}
								}
							}


						}
						if (blockabove == Material.WATER)
						{
							setBlock(x, y, z, chunk, Material.SAND);
							setBlock(x, y - 1, z, chunk, Material.SAND);

							for (int i = 0; i < sandNoise; i++)
							{
								setBlock(x, y - 2 - i, z, chunk, Material.SANDSTONE);
							}

						}
					}
				}
				Material thisblock = getBlock(x, floodHeight, z, chunk);
				Material blockabove = getBlock(x, floodHeight + 1, z, chunk);
				if (thisblock == Material.STONE && blockabove == Material.WATER)
				{
					setBlock(x, floodHeight, z, chunk, Material.SAND);
					setBlock(x, floodHeight - 1, z, chunk, Material.SAND);

					for (int i = 0; i < sandNoise; i++)
					{
						setBlock(x, floodHeight - 2 - i, z, chunk, Material.SANDSTONE);
					}

				}


			}
		}
		return chunk;
	}

	@Override
	public void makeWorld(InternalWorld options) throws WorldGenException
	{
		options.setWorldGen(this);
	}
}
