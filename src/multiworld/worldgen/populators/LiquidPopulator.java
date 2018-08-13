/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.worldgen.populators;

import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

/**
 *
 * @author Fernando
 */
public class LiquidPopulator extends BlockPopulator
{
	@Override
	public void populate(World world, Random random, Chunk chunk)
	{
		// Add random water
		this.addRandomLiquidBlock(chunk, random, 4, Material.WATER);
		//add random lava
		this.addRandomLiquidBlock(chunk, random, 8, Material.LAVA);
	}

	public void addRandomLiquidBlock(Chunk chunk, Random random, int tries, Material blockId)
	{
		for (int i = 0; i < tries; i++)
		{
			// pick out a random point
			int x = random.nextInt(14)+1;
			int y = random.nextInt(250) + 3;
			int z = random.nextInt(14)+1;


			//skip if the selected block is not an solid block
			if (chunk.getBlock(x, y, z).getType() == Material.AIR)
			{
				continue;
			}

			//skip if the block above is not solid
			if (chunk.getBlock(x, y + 1, z).getType() == Material.AIR)
			{
				continue;
			}


			int emptySides = 0;
			if (chunk.getBlock(x + 1, y, z).getType() == Material.AIR)
			{
				emptySides++;
			}
			if (chunk.getBlock(x, y, z + 1).getType() == Material.AIR)
			{
				emptySides++;
			}
			if (chunk.getBlock(x - 1, y, z).getType() == Material.AIR)
			{
				emptySides++;
			}
			if (chunk.getBlock(x, y, z - 1).getType() == Material.AIR)
			{
				emptySides++;
			}
			if (chunk.getBlock(x, y - 1, z).getType() == Material.AIR)
			{
				emptySides++;
			}
			if (emptySides < 3 && emptySides != 0)
			{
				// Place block
				chunk.getBlock(x, y, z).setType(blockId);
			}
		}
	}
}
