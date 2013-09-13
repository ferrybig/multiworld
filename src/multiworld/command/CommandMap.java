/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.command;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import multiworld.CommandException;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Fernando
 */
public class CommandMap extends Command
{
	public final Map<String, Command> m;
	public final Map<String, String> aliasses;
	private static final String[] nullString = new String[0];
	private final String unknownMessage;

	public CommandMap(String permissions, Map<String, Command> h, Map<String, String> aliasses, String unknownMessage)
	{
		super(permissions);
		this.m = Collections.<String, Command>unmodifiableMap(h);
		this.aliasses = aliasses;
		this.unknownMessage = unknownMessage;
	}

	public void excute(CommandSender sender, String name, String[] split) throws CommandException
	{
		if (name.equalsIgnoreCase("multiworld") || name.equalsIgnoreCase("mw"))
		{
			if (split.length == 0)
			{
				this.m.get("help").excute(sender, nullString);
				return;
			}
			this.parseCommand(sender, split[0], this.removeFirstFromArray(split));
		}
		else
		{
			this.parseCommand(sender, name, split);
		}
	}

	private String[] removeFirstFromArray(String[] input)
	{
		if (input.length < 2)
		{
			return nullString;
		}
		String[] output = new String[input.length - 1];
		System.arraycopy(input, 1, output, 0, output.length);
		return output;
	}

	private void parseCommand(CommandSender sender, String name, String[] arg) throws CommandException
	{
		if (name.isEmpty())
		{
			this.m.get("help").excute(sender, nullString);
			return;
		}
		Command cmd = this.m.get(name);
		if (cmd != null)
		{
			cmd.excute(sender, arg);
		}
		else
		{
			if (aliasses != null)
			{
				String newName = aliasses.get(name);
				if (newName != null)
				{
					this.parseCommand(sender, newName, arg);
					return;
				}
			}
			sender.sendMessage(this.unknownMessage);
		}
	}

	@Override
	public void runCommand(CommandSender s, String[] split) throws CommandException
	{
		if (split.length == 0)
		{
			this.m.get("help").excute(s, nullString);
			return;
		}
		this.parseCommand(s, split[0], this.removeFirstFromArray(split));
	}

	public String[] getOptionsForUnfinishedCommands(CommandSender sender, String commandName, String[] split)
	{
		if (commandName.equalsIgnoreCase("multiworld") || commandName.equalsIgnoreCase("mw"))
		{

			if (split.length == 0)
			{
				Set<String> commands = this.m.keySet();
				return commands.toArray(new String[commands.size()]);
			}
			else if (split.length == 1)
			{
				Set<String> commands = new HashSet<String>();
				commands.addAll(this.m.keySet());
				Set<String> found = new HashSet<String>(commands.size());
				String lowerName = split[0].toLowerCase();
				for (String command : commands)
				{
					if (command.toLowerCase().startsWith(lowerName))
					{
						found.add(command);
					}
				}
				return found.toArray(new String[found.size()]);

			}
			else
			{
				if (this.aliasses.containsKey(split[0].toLowerCase()))
				{
					split[0] = this.aliasses.get(split[0].toLowerCase());
				}
				if (this.m.containsKey(split[0].toLowerCase()))
				{
					Command command = this.m.get(split[0].toLowerCase());
					return command.calculateMissingArguments(sender, commandName, this.removeFirstFromArray(split));
				}
				return nullString;
			}
		}
		else
		{
			if (this.aliasses.containsKey(commandName))
			{
				commandName = this.aliasses.get(commandName);
			}
			if (this.m.containsKey(commandName))
			{
				Command command = this.m.get(commandName);
				return command.calculateMissingArguments(sender, commandName, this.removeFirstFromArray(split));
			}
			return nullString;
		}
	}
}
