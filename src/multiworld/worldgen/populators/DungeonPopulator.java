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
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Fernando
 */
public class DungeonPopulator extends BlockPopulator
{
	@Override
	public void populate(World world, Random random, Chunk chunk)
	{
		for (int i33 = 0; i33 < 8; i33++)
		{
			final int dungeonX = chunk.getX() * 16 + random.nextInt(16) + 8;
			final int dungeonY = random.nextInt(250);
			final int dungeonZ = chunk.getZ() * 16 + random.nextInt(16) + 8;

			final int dungeonWidthX = random.nextInt(2) + 2;
			final int dungeonWidthY = 3;
			final int dungeonWidthZ = random.nextInt(2) + 2;

			final int dungeonMaxXPoint = dungeonX + dungeonWidthX + 1;
			final int dungeonMaxYPoint = dungeonY + dungeonWidthY + 1;
			final int dungeonMaxZPoint = dungeonZ + dungeonWidthZ + 1;

			final int dungeonMinXPoint = dungeonX - dungeonWidthX - 1;
			final int dungeonMinYPoint = dungeonY - 1;
			final int dungeonMinZPoint = dungeonZ - dungeonWidthZ - 1;

			int counterX;
			int counterY;
			int counterZ;

			int tmpX;
			int tmpY;
			int tmpZ;

			Block block;

			for (counterX = dungeonMinXPoint; counterX <= dungeonMaxXPoint; counterX++)
			{
				for (counterY = dungeonMinYPoint; counterY <= dungeonMaxYPoint; counterY++)
				{
					for (counterZ = dungeonMinZPoint; counterZ <= dungeonMaxZPoint; counterZ++)
					{

						/* orginal code -/
						 * Material localMaterial = world.getBlockAt(counterX, counterY, counterZ).getType();
						 * if ((counterY == dungeonMinYPoint) && (!isBlock(localMaterial)))
						 * {
						 *     return;
						 * }
						 * if ((counterY == dungeonMaxYPoint) && (!isBlock(localMaterial)))
						 * {
						 *     return;
						 * }
						/- new code */
						if ((counterY == dungeonMinYPoint) || (counterY == dungeonMaxYPoint))
						{
							Material localMaterial = world.getBlockAt(counterX, counterY, counterZ).getType();
							if (!isBlock(localMaterial))
							{
								return;
							}
						}

					}

				}

			}

			for (counterX = dungeonMinXPoint; counterX <= dungeonMaxXPoint; counterX++)
			{
				for (counterY = dungeonY + dungeonWidthY; counterY >= dungeonMinYPoint; counterY--)
				{
					for (counterZ = dungeonMinZPoint; counterZ <= dungeonMaxZPoint; counterZ++)
					{
						block = world.getBlockAt(counterX, counterY, counterZ);
						if ((counterX == dungeonMinXPoint) || (counterY == dungeonMinYPoint) || (counterZ == dungeonMinZPoint) || (counterX == dungeonMaxXPoint) || (counterY == dungeonMaxYPoint) || (counterZ == dungeonMaxZPoint))
						{
							// Block is at side
							if ((counterY >= 0) && (!isBlock(world.getBlockAt(counterX, counterY - 1, counterZ).getType())))
							{
								// Block is at roof
								block.setType(Material.AIR);
							}
							else if (isBlock(block.getType()))
							{
								// Block is at floor or wall
								if ((counterY == dungeonMinYPoint) && (random.nextInt(4) != 0))
								{
									block.setType(Material.MOSSY_COBBLESTONE);
								}
								else
								{
									block.setType(Material.COBBLESTONE);
								}
							}
						}
						else
						{
							// block is inside
							block.setType(Material.AIR);
						}
					}
				}
			}

			// Place 2 chests
			for (counterX = 0; counterX < 2; counterX++)
			{
				// Try 3 times for each chest
				for (counterZ = 0; counterZ < 3; counterZ++)
				{
					tmpX = dungeonX + random.nextInt(dungeonWidthX * 2 + 1) - dungeonWidthX;
					tmpY = dungeonY;
					tmpZ = dungeonZ + random.nextInt(dungeonWidthZ * 2 + 1) - dungeonWidthZ;

					if (!isEmpty(world, tmpX, tmpY, tmpZ))
					{
						continue;
					}

					//Check if block is side at room
					int i5 = 0;
					if (isBlock(world.getBlockAt(tmpX - 1, tmpY, tmpZ).getType()))
					{
						i5++;
					}
					if (isBlock(world.getBlockAt(tmpX + 1, tmpY, tmpZ).getType()))
					{
						i5++;
					}
					if (isBlock(world.getBlockAt(tmpX, tmpY, tmpZ - 1).getType()))
					{
						i5++;
					}
					if (isBlock(world.getBlockAt(tmpX, tmpY, tmpZ + 1).getType()))
					{
						i5++;
					}
					if (i5 != 1)
					{
						// Skip if not on the side
						continue;
					}



					//Place an chest
					block = world.getBlockAt(tmpX, tmpY, tmpZ);
					block.setType(Material.CHEST);
					Chest localTileEntityChest = (Chest) block.getState();
					// Commented out some minecraft code
					// if (localTileEntityChest == null)
					// {
					//	break;
					// }


					// Insert items
					Inventory inv = localTileEntityChest.getBlockInventory();
					for (int i6 = 0; i6 < 8; i6++)
					{
						// Get random item
						ItemStack randomItem = getRandomItem(random);
						if (randomItem == null)
						{
							// Skip if item == null
							continue;
						}
						inv.setItem(random.nextInt(inv.getSize()), randomItem);
					}
					// Prevent another chest placed next to this:
					break;
				}

			}
			// Get spawner location
			block = world.getBlockAt(dungeonX, dungeonY, dungeonZ);

			// Place spawner
			block.setType(Material.SPAWNER);

			// Chance spawned mob
			CreatureSpawner spawner = (CreatureSpawner) block.getState();
			spawner.setSpawnedType(this.getRandomMob(random));
		}
	}

	private ItemStack getRandomItem(Random paramRandom)
	{
		int i = paramRandom.nextInt(11);
		if (i == 0)
		{
			return new ItemStack(Material.SADDLE);
		}
		if (i == 1)
		{
			return new ItemStack(Material.IRON_INGOT, paramRandom.nextInt(4) + 1);
		}
		if (i == 2)
		{
			return new ItemStack(Material.BREAD);
		}
		if (i == 3)
		{
			return new ItemStack(Material.WHEAT, paramRandom.nextInt(4) + 1);
		}
		if (i == 4)
		{
			return new ItemStack(Material.GUNPOWDER, paramRandom.nextInt(4) + 1);
		}
		if (i == 5)
		{
			return new ItemStack(Material.STRING, paramRandom.nextInt(4) + 1);
		}
		if (i == 6)
		{
			return new ItemStack(Material.BUCKET);
		}
		if ((i == 7) && (paramRandom.nextInt(100) == 0))
		{
			return new ItemStack(Material.GOLDEN_APPLE);
		}
		if ((i == 8) && (paramRandom.nextInt(2) == 0))
		{
			return new ItemStack(Material.REDSTONE, paramRandom.nextInt(4) + 1);
		}
		if ((i == 9) && (paramRandom.nextInt(10) == 0))
		{
			return new ItemStack(Material.MUSIC_DISC_BLOCKS);
		}
		if (i == 10)
		{
			return new ItemStack(Material.LAPIS_LAZULI);
		}

		return null;
	}

	private EntityType getRandomMob(Random paramRandom)
	{
		int i = paramRandom.nextInt(4);
		switch (i) {
			case 0:
				return EntityType.SKELETON;
			case 1:
			case 2:
				return EntityType.ZOMBIE;
			default:
				return EntityType.SPIDER;
		}
	}

	private boolean isBlock(Material mat)
	{
		switch (mat)
		{
			case STONE:
			case GRASS:
			case DIRT:
			case COBBLESTONE:
				return true;
			default:
				return false;
		}
	}

	public boolean isEmpty(World world, int x, int y, int z)
	{
		return world.getBlockAt(x, y, z).getType() == Material.AIR;
	}
}
