/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.command.world;

import multiworld.ArgumentException;
import multiworld.CommandException;
import multiworld.CommandFailedException;
import multiworld.ConfigException;
import multiworld.InvalidWorldGenOptionsException;
import multiworld.InvalidWorldNameException;
import multiworld.Utils;
import multiworld.WorldGenException;
import multiworld.command.Command;
import multiworld.data.DataHandler;
import multiworld.data.MyLogger;
import multiworld.worldgen.WorldGenerator;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Fernando
 */
public class CreateCommand extends Command
{
	private final DataHandler data;
	private final MyLogger log;

	public CreateCommand(DataHandler data)
	{
		super("world.create");
		this.data = data;
		this.log = data.getLogger();
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
		else if(split.length == 2)
		{
			return this.calculateMissingArgumentsWorldGenerator(split[1]);
		}
		else
		{
			return EMPTY_STRING_ARRAY;
		}
	}

	@Override
	public void runCommand(CommandSender sender, String[] split) throws CommandException
	{
		if (split.length == 0)
		{
			throw new ArgumentException("/mw create <world> [type] [seed]"); //NOI18N
		}
		else
		{
			try
			{
				Utils.checkWorldName(split[0]);
				if (data.getInternalWorld(split[0], false) != null)
				{
					sender.sendMessage(ChatColor.RED + this.data.getLang().getString("WORLD CREATE ERR WORLD EXISTS"));
					return;
				}
				long seed = (new java.util.Random()).nextLong();
				WorldGenerator env = WorldGenerator.NORMAL;
				String genOptions = ""; //NOI18N
				String genName;
				if (split.length > 1)
				{

					genName = split[1];
					int index = genName.indexOf(':'); //NOI18N
					if (index != -1)
					{
						genOptions = genName.substring(index + 1);
						genName = genName.substring(0, index);
					}
					env = WorldGenerator.getGenByName(genName);
					if (split.length > 2)
					{
						try
						{
							seed = Long.parseLong(split[2]);
						}
						catch (NumberFormatException e)
						{
							seed = split[2].hashCode();
						}
					}

				}
				sender.sendMessage(ChatColor.GREEN + this.data.getLang().getString("WORLD CREATE MAKING"));
				if (!this.data.makeWorld(split[0], env, seed, genOptions))
				{
					sender.sendMessage(ChatColor.RED + this.data.getLang().getString("WORLD CREATE ERR NULL"));
				}
				else
				{
					this.data.loadWorld(split[0], true);
					sender.sendMessage(ChatColor.GREEN + this.data.getLang().getString("WORLD CREATE SUCCES", new Object[]
						{
							split[0], env.getName(), seed
						}));
				}
			}
			catch (ConfigException ex)
			{
				throw new CommandException(ex);
			}
			catch (InvalidWorldNameException e)
			{
				throw new CommandFailedException("The input world isn't a valid world name!");
			}
			catch(InvalidWorldGenOptionsException e)
			{
				throw new CommandFailedException(e.getMessage());
			}
			catch (WorldGenException ex)
			{
				throw new CommandFailedException("Do /mw listgens for a list of valid world gens");
			}
		}
	}
}
