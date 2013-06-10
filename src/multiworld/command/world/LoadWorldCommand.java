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
public class LoadWorldCommand extends Command
{
	private final DataHandler data;
	private final WorldHandler worlds;

	public LoadWorldCommand(DataHandler data, WorldHandler worlds)
	{
		super("world.load");
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
				throw new ArgumentException("/mw load <world name>");
			}
			this.worlds.getWorld(arguments[0], false);
			if(this.worlds.isWorldLoaded(arguments[0]))
			{
				Utils.sendMessage(s,this.data.getLang().getString("world.load.alreadyloaded"));
				return;
			}
			Utils.sendMessage(s,this.data.getLang().getString("world.load.start"));
			this.worlds.loadWorld(arguments[0]);
			Utils.sendMessage(s,this.data.getLang().getString("world.load.done"));
		}
		catch (ConfigException ex)
		{
			throw new CommandFailedByConfigException(ex);
		}
	}
}
