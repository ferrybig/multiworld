/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.command;

import java.util.Arrays;
import java.util.Set;
import multiworld.Utils;
import multiworld.translation.message.PackedMessageData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.permissions.Permissible;

/**
 *
 * @author Fernando
 */
public class DefaultMessageLogger implements MessageLogger
{

	private final DebugLevel level;
	private final String prefix;
	private final CommandSender reciever;
	public static final String DEFAULT_PREFIX = ChatColor.GOLD + "[" + ChatColor.GREEN + "MultiWorld" + ChatColor.GOLD + "] " + ChatColor.WHITE;
	private final String errorPrefix;
	private final String succesPrefix;

	public DefaultMessageLogger(DebugLevel level, CommandSender reciever, String prefix)
	{
		this(level, reciever, prefix, ChatColor.RED.toString(), ChatColor.GREEN.toString());
	}

	public DefaultMessageLogger(DebugLevel level, CommandSender reciever, String prefix, String errorPrefix, String succesPrefix)
	{
		this.level = level;
		this.reciever = reciever;
		this.prefix = prefix;
		this.errorPrefix = errorPrefix;
		this.succesPrefix = succesPrefix;
	}

	@Override
	public DebugLevel getDebugLevel()
	{
		return this.level;
	}

	@Override
	public void sendMessage(MessageType type, String message)
	{
		StringBuilder builder = new StringBuilder();
		if (type != null)
		{
			switch (type)
			{
				case SUCCES:
					builder.append(succesPrefix);
					break;
				case ERROR:
					builder.append(errorPrefix);
					break;
			}
		}
		builder.append(message.replace(Command.RESET, ChatColor.getLastColors(prefix)));
		Utils.sendMessage(reciever, builder.toString(), prefix, true);
	}

	@Override
	public void sendMessage(MessageType type, PackedMessageData... message)
	{
		this.sendMessage(type, this.transformMessage(message));
	}

	@Override
	public void sendMessageBroadcast(MessageType type, String message)
	{
		sendMessageBroadcast(type, message, true);
	}

	public void sendMessageBroadcast(MessageType type, String message, boolean sendToConsole)
	{
		message = message.replace(Command.RESET, ChatColor.getLastColors(prefix));
		String result = prefix + reciever.getName() + ": " + message;
		if (reciever instanceof BlockCommandSender && ((BlockCommandSender) reciever).getBlock().getWorld().getGameRuleValue("commandBlockOutput").equalsIgnoreCase("false"))
		{
			reciever.getServer().getConsoleSender().sendMessage(result);
			return;
		}
		Set<Permissible> users = reciever.getServer().getPluginManager().getPermissionSubscriptions(Server.BROADCAST_CHANNEL_ADMINISTRATIVE);
		String colored = prefix + ChatColor.GRAY + ChatColor.ITALIC + "[" + reciever.getName() + ": " + ChatColor.getLastColors(prefix) + message + ChatColor.GRAY + ChatColor.ITALIC + "]";
		if (!(reciever instanceof ConsoleCommandSender))
		{
			reciever.sendMessage(prefix + message);
		}
		for (Permissible user : users)
		{
			if (user instanceof CommandSender)
			{
				CommandSender target = (CommandSender) user;
				if (target instanceof ConsoleCommandSender)
				{
					if (!sendToConsole && target == Bukkit.getConsoleSender())
					{
						continue;

					}
					target.sendMessage(result);
				}
				else if (target != reciever)
				{
					target.sendMessage(colored);
				}
			}
		}
	}

	@Override
	public void sendMessageBroadcast(MessageType type, PackedMessageData... message)
	{
		this.sendMessageBroadcast(type, this.transformMessage(message), !Arrays.asList(message).contains(PackedMessageData.NO_CONSOLE_MESSAGE));
	}

	@Override
	public void sendMessageDebug(String message, DebugLevel level)
	{
		if (this.getDebugLevel().getLevel() <= level.getLevel())
		{
			// Debugger can log this message
			this.sendMessage(null, "[" + level.name() + "] " + message);
		}
	}

	@Override
	public void sendMessageUsage(String commandLabel, ArgumentType... types)
	{
		StringBuilder build = new StringBuilder();
		build.append("Command Usage: /").append(commandLabel);
		for (ArgumentType type : types)
		{
			build.append(' ').append(type.getMessage());
		}
		this.sendMessage(MessageType.ERROR, build.toString());
	}

	public String transformMessage(PackedMessageData[] options)
	{
		String process = "";
		if (options.length != 0)
		{
			for (PackedMessageData option : options)
			{
				process = option.transformMessage(process);
			}
		}
		return process;
	}

}
