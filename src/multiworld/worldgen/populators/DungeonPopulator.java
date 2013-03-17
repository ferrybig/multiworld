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
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Fernando
 */
public class DungeonPopulator extends BlockPopulator
{

	private boolean isGenarating;


	

	@Override
	public void populate(World world, Random random, Chunk source)
	{
	}

	private ItemStack getRandomChestItem(Random random)
	{
		int i = random.nextInt(11);
		if (i == 0)
		{
			return new ItemStack(Material.SADDLE);
		}
		if (i == 1)
		{
			return new ItemStack(Material.IRON_INGOT, random.nextInt(4) + 1);
		}
		if (i == 2)
		{
			return new ItemStack(Material.BREAD);
		}
		if (i == 3)
		{
			return new ItemStack(Material.WHEAT, random.nextInt(4) + 1);
		}
		if (i == 4)
		{
			return new ItemStack(Material.SULPHUR, random.nextInt(4) + 1);
		}
		if (i == 5)
		{
			return new ItemStack(Material.STRING, random.nextInt(4) + 1);
		}
		if (i == 6)
		{
			return new ItemStack(Material.BUCKET);
		}
		if ((i == 7) && (random.nextInt(100) == 0))
		{
			return new ItemStack(Material.GOLDEN_APPLE);
		}
		if ((i == 8) && (random.nextInt(2) == 0))
		{
			return new ItemStack(Material.REDSTONE, random.nextInt(4) + 1);
		}
		if ((i == 9) && (random.nextInt(10) == 0))
		{
			return new ItemStack(Material.getMaterial((Material.GOLD_RECORD.getId() + random.nextInt(2))));
		}
		if (i == 10)
		{
			return new ItemStack(Material.INK_SACK, 1);
		}

		return null;
	}

}
