/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.worldgen;

import java.util.List;
import java.util.Random;
import multiworld.WorldGenException;
import multiworld.data.InternalWorld;
import multiworld.worldgen.populators.DungeonPopulator;
import multiworld.worldgen.populators.GlowstonePopulator;
import multiworld.worldgen.populators.LiquidPopulator;
import multiworld.worldgen.populators.OrePopulator;
import multiworld.worldgen.populators.PumpkingPopulator;
import multiworld.worldgen.populators.SapplingPopulator;
import multiworld.worldgen.util.ChunkMaker;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

/**
 *
 * @author Fernando
 */
public class ChunkGeneratorEpicCaves extends MultiWorldChunkGen
{
	public ChunkGeneratorEpicCaves()
	{
	}
	SimplexOctaveGenerator gen;

	@Override
	public List<BlockPopulator> getDefaultPopulators(World world)
	{
		List<BlockPopulator> pop = super.getDefaultPopulators(world);
		pop.add(new DungeonPopulator());
		pop.add(new GlowstonePopulator());
		pop.add(new SapplingPopulator());
		pop.add(new LiquidPopulator());
		pop.add(new OrePopulator());
		pop.add(new PumpkingPopulator());
		return pop;
	}

	@Override
	public boolean canSpawn(World world, int x, int z)
	{
		return true;
	}
	final double scale = 32.0; //how far apart the tops of the hills are

	@Override
	public short[][] generateExtBlockSections(World world, Random random, int ChunkX, int ChunkZ, BiomeGrid biomes)
	{
		if (this.gen == null)
		{
			gen = new SimplexOctaveGenerator(new Random(world.getSeed()), 8);
			gen.setScale(1 / scale); //The distance between peaks of the terrain. Scroll down more to see what happens when you play with this
		}
		ChunkMaker chunk = new ChunkMaker(world.getMaxHeight());
		double threshold = 0.0; //scroll down to see what happens when you play with this.
		for (int x = 0; x < 16; x++)
		{
			int real_x = x + ChunkX * 16;
			for (int z = 0; z < 16; z++)
			{
				int real_z = z + ChunkZ * 16;
				int grassCounter = 0;
				for (int y = 255; y > 0; y--)
				{
					if (gen.noise(real_x, y, real_z, 0.5, 0.5) > threshold)
					{
						if (grassCounter == 0)
						{
							chunk.setBlock(x, y, z, GRASS);
						}
						else if (grassCounter < 3)
						{
							chunk.setBlock(x, y, z, DIRT);
						}
						else
						{
							chunk.setBlock(x, y, z, STONE);
						}
						grassCounter++;
					}
					else
					{
						grassCounter = 0;
					}
				}
			}
		}


		return chunk.getRawChunk();
	}

	@Override
	public void makeWorld(InternalWorld options) throws WorldGenException
	{
		 options.setWorldGen(this);
	}
}
