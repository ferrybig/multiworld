/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.data;

import multiworld.ConfigException;
import multiworld.UnknownWorldException;
import multiworld.Utils;
import multiworld.WorldGenException;
import multiworld.worldgen.WorldGenerator;
import org.bukkit.World;

/**
 *
 * @author Fernando
 */
public class WorldHandler
{
	private final DataHandler data;

	public WorldHandler(DataHandler h)
	{
		this.data = h;
	}

	public InternalWorld getWorld(String name,boolean mustBeLoaded) throws UnknownWorldException
	{
		Utils.checkWorldName(name);
		InternalWorld worldObj = this.data.getInternalWorld(name,mustBeLoaded);
		if (worldObj == null)
		{
			throw new UnknownWorldException(name);
		}
		return worldObj;
	}
	public boolean isWorldExisting(String world)
	{
		return this.data.isWorldExisting(world);
	}
	public boolean isWorldLoaded(String world)
	{
		return this.data.isWorldLoaded(world);
	}

	public boolean makeWorld(String name, WorldGenerator env, long seed, String options) throws ConfigException, WorldGenException
	{
		return this.data.makeWorld(name, env, seed, options);
	}

	public boolean deleteWorld(String world) throws ConfigException
	{
		return this.data.deleteWorld(world, false);
	}

	public boolean unloadWorld(String world) throws ConfigException
	{
		return this.data.unloadWorld(world, false);
	}

	public World loadWorld(String name) throws ConfigException
	{
		return this.data.loadWorld(name, false);
	}
}
