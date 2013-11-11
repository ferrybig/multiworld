package multiworld.data;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import multiworld.ConfigException;
import multiworld.Utils;
import multiworld.WorldGenException;
import multiworld.command.CommandStack;
import multiworld.command.DebugLevel;
import multiworld.command.MessageType;
import multiworld.translation.Translation;
import multiworld.translation.message.MessageCache;
import multiworld.worldgen.WorldGenerator;
import org.bukkit.Bukkit;
import org.bukkit.World;

/**
 *
 * @author ferrybig
 */
public class WorldHandler
{
	private final DataHandler data;
	private final WorldManager worlds;

	public WorldHandler(DataHandler h)
	{
		this.data = h;
		this.worlds = this.data.getWorldManager();
	}

	public InternalWorld getWorld(String name, boolean mustBeLoaded)
	{
		if (!Utils.checkWorldName(name))
		{
			return null;
		}
		InternalWorld worldObj = this.worlds.getInternalWorld(name, mustBeLoaded);
		if (worldObj == null)
		{
			return null;
		}
		return worldObj;
	}

	public boolean isWorldExisting(String world)
	{
		return this.data.isWorldExisting(world);
	}

	public boolean isWorldExistingAndSendMessage(String world, CommandStack stack)
	{
		if (!this.isWorldExisting(world))
		{
			stack.sendMessage(MessageType.ERROR,
					  Translation.WORLD_NOT_FOUND,
					  MessageCache.WORLD.get(world));
			return false;
		}
		return true;
	}

	public boolean isWorldLoaded(String world)
	{
		return this.worlds.isWorldLoaded(world);
	}

	public boolean makeWorld(String name, WorldGenerator env, long seed, String options) throws WorldGenException
	{
		return this.worlds.makeWorld(name, env, seed, options);
	}

	public boolean deleteWorld(String world) throws ConfigException
	{
		return this.worlds.deleteWorld(world, false);
	}

	public boolean unloadWorld(String world)
	{
		return this.worlds.unloadWorld(world, false);
	}

	public World loadWorld(final String name, final CommandStack debugger)
	{
		final String magicPrefix = "preparing spawn area for " + name.toLowerCase() + ",";
		final String magicSuffix = "%";
		Handler filter = new Handler()
		{

			@Override
			public void close() throws SecurityException
			{
			}

			@Override
			public void flush()
			{
			}

			@Override
			public void publish(LogRecord lr)
			{
				String message = lr.getMessage().toLowerCase();
				debugger.sendMessageDebug(message, DebugLevel.VVVVVV);
				if (message.startsWith(magicPrefix) && message.endsWith(magicSuffix))
				{
					message = message.substring(magicPrefix.length(), message.length() - magicSuffix.length());
					debugger.sendMessageBroadcast(
						null,
						Translation.PREPARING_SPAWN_ARENA,
						MessageCache.NUMBER.get(message),
						MessageCache.WORLD.get(name));
				}
			}
		};
		try
		{
			Bukkit.getLogger().addHandler(filter);
			this.data.scheduleSave();
			return this.worlds.loadWorld(name);
		}
		finally
		{
			Bukkit.getLogger().removeHandler(filter);
		}
	}

}
