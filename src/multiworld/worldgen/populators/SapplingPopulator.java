/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.worldgen.populators;

import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;

/**
 *
 * @author Fernando
 */
public class SapplingPopulator extends BlockPopulator
{
	@Override
	public void populate(World world, Random random, Chunk chunk)
	{
		// Add random glowstone
		for (int i = 0; i < 64; i++)
		{
			// pick out a random point
			int x = random.nextInt(14) + 1;
			int y = random.nextInt(250) + 3;
			int z = random.nextInt(14) + 1;
			Block mainBlock = chunk.getBlock(x, y, z);
			//skip if the block is air
			if (mainBlock.getType() != Material.AIR)
			{
				continue;
			}
			if (mainBlock.getRelative(BlockFace.DOWN).getType() != Material.GRASS)
			{
				continue;
			}if (mainBlock.getLightLevel() <9)
			{
				continue;
			}
			mainBlock.setType(Material.SAPLING);
			
		}
	}
}
