/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.command.world;

import multiworld.ArgumentException;
import multiworld.CommandException;
import multiworld.CommandFailedByConfigException;
import multiworld.ConfigException;
import multiworld.Utils;
import multiworld.command.Command;
import multiworld.data.DataHandler;
import multiworld.data.WorldHandler;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Fernando
 */
public class UnloadWorldCommand extends Command
{
	private final DataHandler data;
	private final WorldHandler worlds;

	public UnloadWorldCommand(DataHandler data, WorldHandler worlds)
	{
		super("world.unload");
		this.data = data;
		this.worlds = worlds;
	}

	
	@Override
	public void runCommand(CommandSender s, String[] arguments) throws CommandException
	{
		try
		{
			if(arguments.length != 1)
			{
				throw new ArgumentException("/mw unload <world name>");
			}
			if(!this.worlds.isWorldExisting(arguments[0]))
			{
				Utils.sendMessage(s,this.data.getLang().getString("world.unload.fail.unloaded"));
				return;
			}
			if(!this.worlds.isWorldLoaded(arguments[0]))
			{
				Utils.sendMessage(s,this.data.getLang().getString("world.unload.fail.unloaded"));
				return;
			}
			Utils.sendMessage(s,this.data.getLang().getString("world.unload.start"));
			this.worlds.unloadWorld(arguments[0]);
			Utils.sendMessage(s,this.data.getLang().getString("world.unload.done"));
		}
		catch (ConfigException ex)
		{
			throw new CommandFailedByConfigException(ex);
		}
	}
	
}
