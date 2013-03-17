/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.command.flag;

import multiworld.ArgumentException;
import multiworld.CommandException;
import multiworld.ConfigException;
import multiworld.Utils;
import multiworld.addons.AddonHandler;
import multiworld.api.flag.FlagName;
import multiworld.command.Command;
import multiworld.data.DataHandler;
import multiworld.data.InternalWorld;
import multiworld.flags.FlagValue;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Fernando
 */
public class SetFlagCommand extends Command
{
	private final DataHandler d;
	private final AddonHandler pl;

	public SetFlagCommand(DataHandler data,AddonHandler pl)
	{
		super("setflag");
		this.d = data;
		this.pl = pl;
	}

	@Override
	public void runCommand(CommandSender s, String[] split) throws CommandException
	{
		if (split.length != 3)
		{
			throw new ArgumentException("/mw setflag <world> <flag> <value>"); //NOI18N
		}
		else
		{
			try
			{
				InternalWorld world = Utils.getWorld(split[0], this.d,false);
				FlagName flag = FlagName.getFlagFromString(split[1]);
				FlagValue valueTo = FlagValue.parseFlagValue(split[2]);
				if (this.d.getFlag(world.getName(), flag) == valueTo)
				{
					s.sendMessage(this.d.getLang().getString("flag.set.same.value"));
				}
				else
				{
					
					this.d.setFlag(world.getName(), flag, valueTo);
				}
				s.sendMessage(ChatColor.GREEN + this.d.getLang().getString("flag.set.succes"));
			}
			catch (ConfigException e)
			{
				s.sendMessage(ChatColor.RED + this.d.getLang().getString("flag.set.err"));
				this.d.getLogger().throwing("multiworld.MultiWorld", "onCommand", e, "User command setflag error"); //NOI18N
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
		else if (split.length == 3)
		{
			return this.calculateMissingArgumentsBoolean(split[2]);
		}
		else
		{
			return EMPTY_STRING_ARRAY;
		}
	}
}
