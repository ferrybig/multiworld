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
public class OrePopulator extends BlockPopulator
{
	@Override
	public void populate(World world, Random random, Chunk chunk)
	{
		applyOre(world, random, chunk, 1, Material.DIAMOND_ORE, 16);
		applyOre(world, random, chunk, 16, Material.REDSTONE_ORE, 16);
		applyOre(world, random, chunk, 32, Material.GOLD_ORE, 32);
		applyOre(world, random, chunk, 32, Material.LAPIS_ORE, 32);
		applyOre(world, random, chunk, 64, Material.IRON_ORE, 64);
		applyOre(world, random, chunk, 128, Material.COAL_ORE, 128);
	}
	private static final BlockFace[] DEFAULT_SIDES = new BlockFace[]
	{
		BlockFace.DOWN, BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.UP, BlockFace.SELF
	};

	public void applyOre(World world, Random random, Chunk chunk, int tries, Material mat, int height)
	{
		this.applyOre(world, random, chunk, tries, mat, height, DEFAULT_SIDES);
	}

	public void applyOre(World world, Random random, Chunk chunk, int tries, Material mat, int height, BlockFace[] sides)
	{
		for (int i = 0; i < tries; i++)
		{
			int x = random.nextInt(14) + 1;
			int z = random.nextInt(14) + 1;
			int y = random.nextInt(height - 2) + 1;
			Block mainBlock = chunk.getBlock(x, y, z);
			//skip if the block is air
			if (mainBlock.getType() == Material.AIR)
			{
				continue;
			}
			// Place ore
			for (BlockFace counter : new BlockFace[]
				{
					BlockFace.DOWN, BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.UP, BlockFace.SELF
				})
			{
				Block tmp = mainBlock.getRelative(counter);
				if (tmp.getType() == Material.STONE)
				{
					tmp.setTypeIdAndData(mat.getId(), (byte) 0, false);
				}
			}

		}
	}
	private int a = 0;//Block id
	private int b = 0; //size

	public boolean a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
	{
		float f = paramRandom.nextFloat() * 3.141593F;

		double d1 = paramInt1 + 8 + Math.sin(f) * this.b / 8.0F;
		double d2 = paramInt1 + 8 - Math.sin(f) * this.b / 8.0F;
		double d3 = paramInt3 + 8 + Math.cos(f) * this.b / 8.0F;
		double d4 = paramInt3 + 8 - Math.cos(f) * this.b / 8.0F;

		double d5 = paramInt2 + paramRandom.nextInt(3) - 2;
		double d6 = paramInt2 + paramRandom.nextInt(3) - 2;

		for (int i = 0; i <= this.b; i++)
		{
			double d7 = d1 + (d2 - d1) * i / this.b;
			double d8 = d5 + (d6 - d5) * i / this.b;
			double d9 = d3 + (d4 - d3) * i / this.b;

			double d10 = paramRandom.nextDouble() * this.b / 16.0D;
			double d11 = (Math.sin(i * 3.141593F / this.b) + 1.0F) * d10 + 1.0D;
			double d12 = (Math.sin(i * 3.141593F / this.b) + 1.0F) * d10 + 1.0D;

			int j = (int) Math.floor(d7 - d11 / 2.0D);
			int k = (int) Math.floor(d8 - d12 / 2.0D);
			int m = (int) Math.floor(d9 - d11 / 2.0D);

			int n = (int) Math.floor(d7 + d11 / 2.0D);
			int i1 = (int) Math.floor(d8 + d12 / 2.0D);
			int i2 = (int) Math.floor(d9 + d11 / 2.0D);

			for (int i3 = j; i3 <= n; i3++)
			{
				double d13 = (i3 + 0.5D - d7) / (d11 / 2.0D);
				if (d13 * d13 < 1.0D)
				{
					for (int i4 = k; i4 <= i1; i4++)
					{
						double d14 = (i4 + 0.5D - d8) / (d12 / 2.0D);
						if (d13 * d13 + d14 * d14 < 1.0D)
						{
							for (int i5 = m; i5 <= i2; i5++)
							{
								double d15 = (i5 + 0.5D - d9) / (d11 / 2.0D);
								if ((d13 * d13 + d14 * d14 + d15 * d15 < 1.0D)
									&& (paramWorld.getBlockTypeIdAt(i3, i4, i5) == Material.STONE.getId()))
								{
									paramWorld.getBlockAt(i3, i4, i5).setTypeId(this.a);
								}
							}
						}
					}
				}

			}

		}
		return true;
	}
}
