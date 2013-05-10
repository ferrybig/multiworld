/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.data;

import multiworld.ConfigException;
import multiworld.MultiWorldPlugin;
import multiworld.api.ConfigurationSaveException;
import multiworld.api.MultiWorldWorldData;
import multiworld.api.events.WorldLoadEvent;
import multiworld.api.events.WorldUnloadEvent;
import multiworld.api.flag.FlagName;
import multiworld.flags.FlagValue;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.World.Environment;

/**
 *
 * @author Fernando
 */
public class WorldContainer implements MultiWorldWorldData
{
	private InternalWorld world;
	private boolean loaded;

	public WorldContainer(InternalWorld world, boolean loaded)
	{
		this.world = world;
		this.loaded = loaded;

	}

	/**
	 * @return the world
	 */
	public InternalWorld getWorld()
	{
		return world;
	}

	/**
	 * @return the loaded
	 */
	@Override
	public boolean isLoaded()
	{
		return loaded;
	}

	/**
	 * Internal use only
	 * @param loaded the loaded to set
	 */
	public void setLoaded(boolean loaded)
	{
		if (this.loaded != loaded)
		{
			if (loaded)
			{
				new WorldLoadEvent(this).call();
			}
			else
			{
				new WorldUnloadEvent(this).call();
			}
			this.loaded = loaded;
		}
	}

	/**
	 * @param world the world to set
	 */
	public void setWorld(InternalWorld world)
	{
		this.world = world;
	}

	@Override
	public World getBukkitWorld()
	{
		World w = Bukkit.getWorld(this.getName());
		this.setLoaded(w != null);
		return w;
	}

	@Override
	public String getName()
	{
		return this.getWorld().getName();
	}

	@Override
	public long getSeed()
	{
		return this.getWorld().getSeed();
	}

	@Override
	public boolean getOptionValue(FlagName flag)
	{
		return MultiWorldPlugin.getInstance().getDataManager().getFlag(this.getName(), flag).getAsBoolean(flag);

	}

	@Override
	public void setOptionValue(FlagName flag, boolean newValue) throws ConfigurationSaveException
	{
		try
		{
			MultiWorldPlugin.getInstance().getDataManager().setFlag(this.getName(), flag, FlagValue.fromBoolean(newValue));
		}
		catch (ConfigException ex)
		{
			throw new ConfigurationSaveException("Error saving flag " + flag, ex);
		}
	}

	@Override
	public boolean isOptionSet(FlagName flag)
	{
		return MultiWorldPlugin.getInstance().getDataManager().getFlag(this.getName(), flag) != FlagValue.UNKNOWN;
	}

	@Override
	public Environment getDimension()
	{
		return this.getWorld().getEnv();
	}

	@Override
	public boolean loadWorld() throws ConfigurationSaveException
	{
		try
		{
			return MultiWorldPlugin.getInstance().getDataManager().loadWorld(this.getName(), true) != null;
		}
		catch (ConfigException ex)
		{
			throw new ConfigurationSaveException("Unable to save world after load", ex);
		}
	}

	@Override
	public boolean unloadWorld() throws ConfigurationSaveException
	{
		try
		{
			return MultiWorldPlugin.getInstance().getDataManager().unloadWorld(this.getName(), true);
		}
		catch (ConfigException ex)
		{
			throw new ConfigurationSaveException("Unable to save world after unload", ex);
		}
	}

	@Override
	public String getGeneratorType()
	{
		return this.getWorld().getMainGen();
	}
}
