/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.command.other;

import multiworld.chat.Formatter;
import multiworld.command.Command;
import multiworld.command.CommandStack;
import multiworld.command.MessageType;
import multiworld.data.VersionHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 *
 * @author Fernando
 */
public class DebugCommand extends Command
{
	private final VersionHandler debug;

	public DebugCommand(VersionHandler d)
	{
		super("debug", "Prints outs some debug information");
		this.debug = d;
	}

	@Override
	public void runCommand(CommandStack stack)
	{
		stack.sendMessage(MessageType.HIDDEN_SUCCES, "Now printing debug information");
		stack.sendMessage(MessageType.HIDDEN_SUCCES, "MultiWorld version: " + this.debug.getVersion());
		stack.sendMessage(MessageType.HIDDEN_SUCCES, "Bukkit version: " + Bukkit.getVersion());
		stack.sendMessage(MessageType.HIDDEN_SUCCES, "");
		stack.sendMessage(MessageType.HIDDEN_SUCCES, "--<[Modules]>--");
		stack.sendMessage(MessageType.HIDDEN_SUCCES, Formatter.createList(ChatColor.WHITE, "State", "pluginName"));
		for (String plugin : debug.getPlugins())
		{
			stack.sendMessage(MessageType.HIDDEN_SUCCES,
					  Formatter.createList((debug.isLoaded(plugin) ? (debug.isEnabled(plugin) ? "Working" : "Loaded") : "Unloaded"),
							       plugin));
		}
		stack.sendMessage(MessageType.HIDDEN_SUCCES, "");
		stack.sendMessage(MessageType.HIDDEN_SUCCES, "--<[CommandStacks]>--");
		CommandStack tmp = stack;
		do
		{
			StringBuilder sb = new StringBuilder();
			sb.append(tmp.getClass().getCanonicalName());
			sb.append("\n - /").append(String.valueOf(tmp.getCommandLabel()));
			sb.append(' ');
			String[] args = tmp.getArguments();
			if (args != null)
			{
				sb.append('[');
				for (String arg : args)
				{
					sb.append(arg).append(' ');
				}
				if (args.length != 0)
				{
					sb.setLength(sb.length() - 1);
				}
				sb.append(']');
			}
			stack.sendMessage(MessageType.HIDDEN_SUCCES, sb.toString());
		}
		while ((tmp = tmp.getParent()) != null);
	}
}
