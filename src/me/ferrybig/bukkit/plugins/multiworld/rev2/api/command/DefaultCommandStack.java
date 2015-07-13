/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.api.command;

import me.ferrybig.bukkit.plugins.multiworld.rev2.api.command.message.PackedMessageData;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.NativeCommandSender;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.NativeLocation;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.NativePermissionsHolder;

/**
 *
 * @author Fernando
 */
public class DefaultCommandStack implements CommandStack
{

	public static Builder builder(MessageLogger messages)
	{
		return new Builder(messages);
	}

	public static Builder builder(NativeCommandSender sender)
	{
		return new Builder(sender);
	}
	private final String[] args;
	private final String commandLabel;
	private final NativeLocation loc;
	private final MessageLogger messages;
	private final CommandStack parent;
	private final NativePermissionsHolder permissions;
	private final NativeCommandSender sender;

	private DefaultCommandStack(MessageLogger messages, NativeCommandSender sender, NativeLocation loc, String[] args, CommandStack parent, String commandLabel, NativePermissionsHolder permissions)
	{
		this.messages = messages;
		this.sender = sender;
		this.loc = loc;
		this.args = args;
		this.parent = parent;
		this.commandLabel = commandLabel;
		this.permissions = permissions;
	}

	@Override
	public Builder editStack()
	{
		return new Builder(this, true);
	}

	@Override
	public String[] getArguments()
	{
		return this.args;
	}

	@Override
	public String getCommandLabel()
	{
		return this.commandLabel;
	}

	@Override
	public DebugLevel getDebugLevel()
	{
		return this.messages.getDebugLevel();
	}

	@Override
	public NativeLocation getLocation()
	{
		return this.loc;
	}

	@Override
	public CommandStack getParent()
	{
		return this.parent;
	}

	@Override
	public NativeCommandSender getSender()
	{
		return this.sender;
	}

	@Override
	public boolean hasPermission(String permission)
	{
		return this.permissions.hasPermision(permission);
	}

	@Override
	public Builder newStack()
	{
		return new Builder(this, false);
	}

	@Override
	public void sendMessage(MessageType type, String message)
	{
		messages.sendMessage(type, message);
	}

	@Override
	public void sendMessage(MessageType type, PackedMessageData ... message)
	{
		messages.sendMessage(type, message);
	}

	@Override
	public void sendMessageBroadcast(MessageType type, String message)
	{
		messages.sendMessageBroadcast(type, message);
	}

	@Override
	public void sendMessageBroadcast(MessageType type, PackedMessageData ... message)
	{
		messages.sendMessageBroadcast(type, message);
	}

	@Override
	public void sendMessageDebug(String message, DebugLevel level)
	{
		messages.sendMessageDebug(message, level);
	}

	@Override
	public void sendMessageUsage(String commandLabel, ArgumentType... types)
	{
		messages.sendMessageUsage(commandLabel, types);
	}

	public static class Builder implements CommandStack.Builder
	{
		private String commandLabel;
		private NativeLocation loc;
		private MessageLogger messages;
		private NativeCommandSender sender;
		private String[] args;
		private CommandStack parent;
		private int poppedArguments = 0;
		private NativePermissionsHolder permissions;

		private Builder(CommandStack main, boolean editStack)
		{
			this((MessageLogger) main);
			if (editStack)
			{
				parent = main.getParent();
			}
			else
			{
				parent = main;
			}
			loc = main.getLocation();
			sender = main.getSender();
			args = main.getArguments() != null ? main.getArguments().clone():null;
			commandLabel = main.getCommandLabel();
			permissions = sender;
		}

		public Builder(MessageLogger messages)
		{
			this.messages = messages;
		}

		public Builder(NativeCommandSender sender)
		{
			this(new DefaultMessageLogger(DebugLevel.NONE, sender, DefaultMessageLogger.DEFAULT_PREFIX, sender.getNative()));
			this.sender = sender;
			this.permissions = sender;
		}

		@Override
		public DefaultCommandStack build()
		{
			String[] arguments;
			if (this.poppedArguments > 0)
			{
				int newLength = Math.max(0, this.args.length - poppedArguments);
				arguments = new String[newLength];
				if (newLength != 0)
				{
					assert newLength > 0;
					System.arraycopy(args, poppedArguments, arguments, 0, newLength);
				}
			}
			else
			{
				arguments = this.args != null ? this.args.clone() : null;
			}
			return new DefaultCommandStack(messages, sender, loc, arguments, parent, commandLabel, this.permissions);
		}

		@Override
		public Builder setArguments(String[] args)
		{
			this.args = args;
			this.poppedArguments = 0;
			return this;
		}

		@Override
		public Builder setCommandLabel(String commandLabel)
		{
			this.commandLabel = commandLabel;
			return this;
		}

		@Override
		public Builder setLocation(NativeLocation loc)
		{
			this.loc = loc;
			return this;
		}

		@Override
		public Builder setMessageLogger(MessageLogger logger)
		{
			this.messages = logger;
			return this;
		}

		@Override
		public Builder setSender(NativeCommandSender sender)
		{
			this.sender = sender;
			if (this.permissions == null)
			{
				this.setPermissible(sender);
			}
			return this;
		}

		@Override
		public Builder popArguments(int number)
		{
			if (number < 0)
			{
				throw new IllegalArgumentException("number cannot be lower than 0");
			}
			this.poppedArguments += number;
			return this;
		}

		public Builder setPermissible(NativePermissionsHolder permissions)
		{
			this.permissions = permissions;
			return this;
		}
	}
}
