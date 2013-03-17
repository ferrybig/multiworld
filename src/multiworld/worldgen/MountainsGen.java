/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.worldgen;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.bukkit.*;
import org.bukkit.generator.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.noise.SimplexOctaveGenerator;

/**
 *
 * @author Fernando
 */
public class MountainsGen
{
	public byte[] generate(World world, Random random, int x_chunk, int z_chunk)
	{
		byte[] ret = new byte[32768];
		SimplexOctaveGenerator noisegen = new SimplexOctaveGenerator(world.getSeed(), 4);
		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				double noiz = 25 + noisegen.noise((x + x_chunk * 16) / 100.0f, (z + z_chunk * 16) / 100.0f, 0.5, 0.5) * 15;
				for (int y = 0; y < noiz; y++)
				{
					if (y == 0)
					{
						ret[(x * 16 + z) * 128 + y] = 7;
					}
					else if (y >= noiz - 1)
					{
						ret[(x * 16 + z) * 128 + y] = 2;
					}
					else
					{
						ret[(x * 16 + z) * 128 + y] = 1;
					}
				}
			}
		}
		return ret;
	}

	public boolean canSpawn(World world, int x, int z)
	{
		return true;
	}
}
