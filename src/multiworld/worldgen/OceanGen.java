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
public class OceanGen extends MultiWorldChunkGen
{
	private final Map<String, Byte> heightMapMax = new HashMap<String, Byte>();
	private final Map<String, Byte> heightMapMin = new HashMap<String, Byte>();

	@Override
	public void makeWorld(InternalWorld world) throws WorldGenException
	{
		byte[] option = parseOptions(world.getOptions());
		heightMapMax.put(world.getName(), option[1]);
		heightMapMin.put(world.getName(), option[0]);

		world.setWorldGen(this);
	}

	private byte[] parseOptions(String options) throws InvalidWorldGenOptionsException
	{
		if (options.isEmpty())
		{
			return new byte[]
				{
					50, 60
				};
		}
		try
		{
			byte number = Byte.parseByte(options);
			if (number > 64 || number < 10)
			{
				throw new InvalidWorldGenOptionsException("Argument must be lower than 64 and higher than 10");
			}
			return new byte[]
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
				byte lowest = Byte.parseByte(numbers[0]);
				byte higest = Byte.parseByte(numbers[1]);
				if (lowest > higest)
				{
					throw new InvalidWorldGenOptionsException("wrong order of numbers");
				}
				if (higest > 64 || lowest < 10)
				{
					throw new InvalidWorldGenOptionsException("Arguments must be lower than 64 and higher than 10");
				}
				return new byte[]
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

	protected final byte getHeightMaxByWorldName(String name)
	{
		return this.heightMapMax.get(name);
	}

	protected final byte getHeightMinByWorldName(String name)
	{
		return this.heightMapMin.get(name);
	}

	@Override
	public boolean canSpawn(World world, int x, int z)
	{
		return true;
	}
	final double scale = 32.0; //how far apart the tops of the hills are

	@Override
	public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biomes)
	{
		ChunkMaker chunk = new ChunkMaker(world.getMaxHeight());
		byte maxHeight = getHeightMaxByWorldName(world.getName());
		byte minHeight = getHeightMinByWorldName(world.getName());
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
				biomes.setBiome(x1, z1, Biome.OCEAN);
				chunk.setBlock(x1, 0, z1, Material.BEDROCK);
				chunk.cuboid(chunk.getPointer(x1, 1, z1), chunk.getPointer(x1, minHeight, z1), Material.SAND);
				if (gen != null)
				{
					int height = maxHeight - minHeight;
					double noise = gen.noise(x * 16 + x1, minHeight, z * 16 + z1, 0.5, 0.5, true);
					noise += 1;
					noise /= 2;
					noise *= height;

					height = minHeight + (int) Math.round(noise);
					chunk.cuboid(chunk.getPointer(x1, minHeight, z1), chunk.getPointer(x1, height, z1), Material.SAND);
					chunk.cuboid(chunk.getPointer(x1, height, z1), chunk.getPointer(x1, world.getSeaLevel(), z1),  Material.WATER);
				}
				else
				{
					chunk.cuboid(chunk.getPointer(x1, minHeight, z1), chunk.getPointer(x1, world.getSeaLevel(), z1), Material.WATER);
				}

			}
		}
		return chunk.toChunkData(super.createChunkData(world));
	}
}
