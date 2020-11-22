/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ferrybig.multiworld.worldgen.populators;

import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

/**
 * The base class for al <code>BlockPopulators</code> that works only on the surface
 * @author Fernando
 */
public abstract class SurfacePopulator extends BlockPopulator
{

	@Override
	public void populate(World world, Random random, Chunk chunk)
	{
		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				Block block = chunk.getBlock(x, world.getHighestBlockYAt((x << 4) + chunk.getX(), (z << 4) + chunk.getZ()), z);
				this.chanceBlock(x, z, block);
			}
		}
	}

	public abstract void chanceBlock(int x, int z, Block block);
}
