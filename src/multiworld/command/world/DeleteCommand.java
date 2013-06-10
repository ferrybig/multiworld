/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.command.world;

import multiworld.ArgumentException;
import multiworld.CommandException;
import multiworld.ConfigException;
import multiworld.command.Command;
import multiworld.data.DataHandler;
import multiworld.data.WorldHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Fernando
 */
public class DeleteCommand extends Command
{
	private final DataHandler d;

	public DeleteCommand(DataHandler data, WorldHandler worlds)
	{
		super("world.delete");
		this.d = data;
	}

	@Override
	public String[] calculateMissingArguments(CommandSender sender, String commandName, String[] split)
	{
		if (split.length == 0)
		{
			return this.calculateMissingArgumentsWorld("");
		}
		else if (split.length == 1)
		{
			return this.calculateMissingArgumentsWorld(split[0]);
		}
		else
		{
			return EMPTY_STRING_ARRAY;
		}
	}

	@Override
	public void runCommand(CommandSender s, String[] arg) throws CommandException
	{
		if (arg.length != 1)
		{
			throw new ArgumentException("/mw delete <world>"); //NOI18N
		}
		else
		{
			try
			{
				if (this.d.isWorldLoaded(arg[0]))
				{
					if (this.d.unloadWorld(arg[0], false))
					{
						s.sendMessage(ChatColor.GREEN + "Unloaded world " + arg[0] + "!");
					}
					else
					{
						s.sendMessage(ChatColor.RED + "Failed to unload world " + arg[0] + "!");
						return;
					}
				}
				if (this.d.deleteWorld(arg[0], true))
				{
					s.sendMessage(ChatColor.GREEN + "Deleted world " + arg[0] + "!");
				}
				else
				{
					s.sendMessage(ChatColor.RED + "Failed to delete world " + arg[0] + "!");
				}
			}
			catch (ConfigException ex)
			{
				throw new CommandException(ex);
			}
		}
	}
}
