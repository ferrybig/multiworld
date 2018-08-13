/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.worldgen;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import multiworld.InvalidWorldGenOptionsException;
import multiworld.data.InternalWorld;
import multiworld.worldgen.util.ChunkMaker;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;

/**
 * the gen that gives support for worlds thats exsts only from 1 chunk
 * @author Fernando
 */
public abstract class SimpleChunkGen extends MultiWorldChunkGen implements ChunkGen
{
	protected final Map<UUID, ChunkMaker> chunk = new HashMap<>();

	/**
	 * Shapes the basic shape of the chunk
	 * @param world The world to make for
	 * @param random The random to use
	 * @param x The x of the chunk
	 * @param z The Z of the chunk
	 * @param biomes 
	 * @return The bytes of the chunk data
	 */
	@Override
	public ChunkData generateChunkData(World world,
						  Random random,
						  int x,
						  int z,
						  ChunkGenerator.BiomeGrid biomes)
	{

		ChunkMaker tmp = this.chunk.get(world.getUID());
		if (tmp == null)
		{
			this.chunk.put(world.getUID(), this.makeChunk(world));
			tmp = this.chunk.get(world.getUID());
		}
		Biome b = this.getBiome();
		if (b != null)
		{
			for (int xCounter = 0; xCounter < 16; xCounter++)
			{
				for (int zCounter = 0; zCounter < 16; zCounter++)
				{
					biomes.setBiome(x, z, b);
				}
			}
		}
		return tmp.toChunkData(this.createChunkData(world));
	}

	/**
	 * Makes the basic shape of chunk
	 * 
	 * @param world
	 * @return  
	 */
	protected abstract ChunkMaker makeChunk(World world);

	@Override
	public void makeWorld(InternalWorld world) throws InvalidWorldGenOptionsException
	{
		world.setWorldGen(this);
	}

	@Override
	public boolean canSpawn(World world, int x, int z)
	{
		return true;
	}

	public void gc()
	{
		this.chunk.clear();
	}

	public Biome getBiome()
	{
		return null;
	}
}
