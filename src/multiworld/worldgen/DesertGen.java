/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.worldgen;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import multiworld.InvalidWorldGenOptionsException;
import multiworld.WorldGenException;
import multiworld.data.InternalWorld;
import multiworld.worldgen.util.ChunkMaker;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.util.noise.SimplexOctaveGenerator;

/**
 *
 * @author Fernando
 */
public class DesertGen extends MultiWorldChunkGen
{
	private final Map<String, Short> heightMapMax = new HashMap<String, Short>();
	private final Map<String, Short> heightMapMin = new HashMap<String, Short>();

	@Override
	public void makeWorld(InternalWorld world) throws WorldGenException
	{
		short[] option = parseOptions(world.getOptions());
		heightMapMax.put(world.getName(), option[1]);
		heightMapMin.put(world.getName(), option[0]);

		world.setWorldGen(this);
	}

	private short[] parseOptions(String options) throws InvalidWorldGenOptionsException
	{
		if (options.isEmpty())
		{
			return new short[]
			{
				64, 80
			};
		}
		try
		{
			short number = Short.parseShort(options);
			if (number > 200 || number < 64)
			{
				throw new InvalidWorldGenOptionsException("Argument must be lower than 200 and higher than 64");
			}
			return new short[]
			{
				number, number
			};
		}
		catch (NumberFormatException e)
		{
			String[] numbers = options.split("\\-");
			if (numbers.length != 2)
			{
				throw new InvalidWorldGenOptionsException("wrong systax");
			}
			try
			{
				short lowest = Short.parseShort(numbers[0]);
				short higest = Short.parseShort(numbers[1]);
				if (lowest > higest)
				{
					throw new InvalidWorldGenOptionsException("wrong order of numbers");
				}
				if (higest > 200 || lowest < 64)
				{
					throw new InvalidWorldGenOptionsException("Arguments must be lower than 200 and higher than 64");
				}
				return new short[]
				{
					lowest, higest
				};
			}
			catch (NumberFormatException e1)
			{
				throw new InvalidWorldGenOptionsException(e1.getLocalizedMessage());
			}
		}
	}

	protected final short getHeightMaxByWorldName(String name)
	{
		return this.heightMapMax.get(name);
	}

	protected final short getHeightMinByWorldName(String name)
	{
		return this.heightMapMin.get(name);
	}

	@Override
	public boolean canSpawn(World world, int x, int z)
	{
		return true;
	}
	final double scale = 64.0; //how far apart the tops of the hills are

	@Override
	public short[][] generateExtBlockSections(World world, Random random, int x, int z, BiomeGrid biomes)
	{
		ChunkMaker chunk = new ChunkMaker(world.getMaxHeight());
		short maxHeight = getHeightMaxByWorldName(world.getName());
		short minHeight = getHeightMinByWorldName(world.getName());
		SimplexOctaveGenerator gen = null;

		if (maxHeight != minHeight)
		{
			gen = new SimplexOctaveGenerator(new Random(world.getSeed()), 8);
			gen.setScale(1 / scale); //The distance between peaks of the terrain. Scroll down more to see what happens when you play with this
		}
		for (int x1 = 0; x1 < 16; x1++)
		{
			for (int z1 = 0; z1 < 16; z1++)
			{
				biomes.setBiome(x1, z1, Biome.DESERT_HILLS);
				chunk.setBlock(x1, 0, z1, (short) BEDROCK);
				chunk.cuboid(chunk.getPointer(x1, 1, z1), chunk.getPointer(x1, minHeight, z1), (byte) Material.SAND.getId());
				if (gen != null)
				{
					int height = maxHeight - minHeight;
					double noise = gen.noise(x * 16 + x1, minHeight, z * 16 + z1, 0.5, 0.5, true);
					noise += 1;
					noise /= 2;
					noise *= height;

					height = minHeight + (int) Math.round(noise);
					chunk.cuboid(chunk.getPointer(x1, minHeight, z1), chunk.getPointer(x1, height, z1), (byte) Material.SAND.getId());
				}

			}
		}
		return chunk.getRawChunk();
	}
}
