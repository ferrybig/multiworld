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
public class GlowstonePopulator extends BlockPopulator
{
	@Override
	public void populate(World world, Random random, Chunk chunk)
	{
		// Add random glowstone
		for (int i = 0; i < 64; i++)
		{
			for (int tries = 0; tries < 4; tries++)
			{
				// pick out a random point
				int x = random.nextInt(14) + 1;
				int y = random.nextInt(250) + 3;
				int z = random.nextInt(14) + 1;
				Block mainBlock = chunk.getBlock(x, y, z);
				//skip if the block is air
				if (mainBlock.getType() == Material.AIR)
				{
					continue;
				}
				//skip if the block above or below is not an solid block
				if ((chunk.getBlock(x, y + 1, z).getType() != Material.AIR) && (chunk.getBlock(x, y - 1, z).getType() != Material.AIR))
				{
					continue;
				}
				// Place glowstone 

				for (BlockFace counter : new BlockFace[]
					{
						BlockFace.DOWN, BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.UP, BlockFace.SELF
					})
				{
					Block tmp = mainBlock.getRelative(counter);
					if (tmp.getType() != Material.AIR)
					{
						tmp.setType(Material.GLOWSTONE);
					}
				}
				break;
			}
		}
	}
}
