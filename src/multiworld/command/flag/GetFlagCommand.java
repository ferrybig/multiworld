/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.command.flag;

import multiworld.ArgumentException;
import multiworld.CommandException;
import multiworld.Utils;
import multiworld.api.flag.FlagName;
import multiworld.chat.Formatter;
import multiworld.command.Command;
import multiworld.data.DataHandler;
import multiworld.data.InternalWorld;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Fernando
 */
public class GetFlagCommand extends Command
{
	private final DataHandler d;

	public GetFlagCommand(DataHandler data)
	{
		super("getflag");
		this.d = data;
	}

	@Override
	public void runCommand(CommandSender sender, String[] split) throws CommandException
	{
		if (split.length != 2)
		{
			throw new ArgumentException("/mw getflag <world> <flag>"); //NOI18N
		}
		else
		{
			if (split[1].equals("*")) //NOI18N
			{
				for (String txt : this.showWorldFlags(this.d.getInternalWorld(split[0], true)))
				{
					sender.sendMessage(ChatColor.GREEN + txt);
				}
			}
			else
			{
				FlagName flag = FlagName.getFlagFromString(split[1]);
				Utils.getWorld(split[0], this.d, false);
				sender.sendMessage(ChatColor.GREEN + flag.toString() + ChatColor.WHITE + " = " + Formatter.printFlag(this.d.getFlag(split[0], flag))); //NOI18N
			}
		}
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
		else if (split.length == 2)
		{
			return this.calculateMissingArgumentsFlagName(split[1]);
		}
		else
		{
			return EMPTY_STRING_ARRAY;
		}
	}

	/**
	 * Show all flags set on world
	 *
	 * @param world the world to show from
	 * @return an array of string containing the lines of the flags
	 */
	public String[] showWorldFlags(InternalWorld world)
	{
		FlagName[] flagsNames = FlagName.class.getEnumConstants();
		StringBuilder out = new StringBuilder().append(this.d.getLang().getString("flag.get.all", new Object[]
			{
				world.getName()
			}));
		for (FlagName flag : flagsNames)
		{
			out.append("#").append(ChatColor.GREEN).append(flag.toString()).append(ChatColor.WHITE).append(" = ").append(Formatter.printFlag(this.d.getFlag(world.getName(), flag)));
		}
		return out.toString().split("\\#");
	}
}
