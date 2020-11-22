/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ferrybig.multiworld.worldgen;

import java.util.List;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;

/**
 * The base class for al world generators that make planets
 * @author Fernando
 */
public abstract class AbstractPlanetGen extends EmptyWorldGenerator
{
	@Override
	public abstract List<BlockPopulator> getDefaultPopulators(World world);

	@Override
	public boolean canSpawn(World world, int x, int z)
	{
		Block highest = world.getHighestBlockAt(x, z);
		if (highest.getType() == Material.AIR || highest.getType() == Material.WATER || highest.getType() == Material.LAVA)
		{
			highest.setType(Material.GRASS);
			highest.getRelative(BlockFace.EAST).setType(Material.GRASS);
			highest.getRelative(BlockFace.WEST).setType(Material.GRASS);
			highest.getRelative(BlockFace.NORTH).setType(Material.GRASS);
			highest.getRelative(BlockFace.SOUTH).setType(Material.GRASS);
			highest.getRelative(BlockFace.DOWN).setType(Material.DIRT);
		}
		return true;
	}
}
