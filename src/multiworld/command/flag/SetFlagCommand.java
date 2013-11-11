/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.command.flag;

import multiworld.InvalidFlagException;
import multiworld.InvalidFlagValueException;
import multiworld.addons.AddonHandler;
import multiworld.api.flag.FlagName;
import multiworld.command.ArgumentType;
import multiworld.command.Command;
import multiworld.command.CommandStack;
import multiworld.command.MessageType;
import multiworld.data.DataHandler;
import multiworld.data.InternalWorld;
import multiworld.data.WorldHandler;
import multiworld.flags.FlagValue;
import multiworld.translation.Translation;
import multiworld.translation.message.MessageCache;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Fernando
 */
public class SetFlagCommand extends Command
{
	private final DataHandler d;
	private final AddonHandler pl;
	private final WorldHandler worlds;

	public SetFlagCommand(DataHandler data, AddonHandler pl, WorldHandler worlds)
	{
		super("setflag", "This commands sets a flag value on a world, \nflag values can be compared with gamerules");
		this.d = data;
		this.pl = pl;
		this.worlds = worlds;
	}

	@Override
	public void runCommand(CommandStack stack)
	{
		String[] split = stack.getArguments();
		if (split.length != 3)
		{
			stack.sendMessageUsage(stack.getCommandLabel(), ArgumentType.valueOf("setflag"), ArgumentType.TARGET_WORLD, ArgumentType.valueOf("<Flag>"), ArgumentType.valueOf("<value>"));
		}
		else
		{
			InternalWorld world = worlds.getWorld(split[0], false);
			FlagName flag;
			FlagValue valueTo;
			try
			{
				flag = FlagName.getFlagFromString(split[1]);
				valueTo = FlagValue.parseFlagValue(split[2]);
			}
			catch (InvalidFlagException ex)
			{
				stack.sendMessage(MessageType.ERROR, Translation.INVALID_FLAG);
				return;
			}
			catch (InvalidFlagValueException ex)
			{
				stack.sendMessage(MessageType.ERROR, Translation.INVALID_FLAG_VALUE);
				return;
			}

			if (this.d.getWorldManager().getFlag(world.getName(), flag) == valueTo)
			{
				stack.sendMessage(MessageType.ERROR, Translation.COMMAND_SETFLAG_FAIL_SAME_VALUE);
			}
			else
			{
				this.d.getWorldManager().setFlag(world.getName(), flag, valueTo);
				this.d.scheduleSave();
				stack.sendMessageBroadcast(
					MessageType.SUCCES,
					Translation.COMMAND_SETFLAG_SUCCES,
					MessageCache.FLAG.get(flag.toString()),
					MessageCache.FLAG_VALUE.get(valueTo.toString()));
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
