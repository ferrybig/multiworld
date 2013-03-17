/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.command.other;

import multiworld.CommandException;
import multiworld.chat.Formatter;
import multiworld.command.Command;
import multiworld.data.VersionHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Fernando
 */
public class DebugCommand extends Command
{
	private final VersionHandler debug;

	public DebugCommand(VersionHandler d)
	{
		super("debug");
		this.debug = d;
	}

	@Override
	public void runCommand(CommandSender s, String[] arguments) throws CommandException
	{
		s.sendMessage(ChatColor.BLUE + "Now printing debug information");
		s.sendMessage(Formatter.createList(ChatColor.WHITE, "name", "version"));
		s.sendMessage(Formatter.createList(ChatColor.WHITE, "Bukkit", Bukkit.getVersion()));
		s.sendMessage(Formatter.createList(ChatColor.WHITE, "Multiworld", this.debug.getVersion()));
		s.sendMessage(ChatColor.BLUE + "----------------------------------------------");
		s.sendMessage(Formatter.createList(ChatColor.WHITE, "configState", "enabled", "loaded", "pluginName"));
		for (String plugin : debug.getPlugins())
		{
			s.sendMessage(Formatter.createList(Formatter.printBoolean(debug.enabledInsideConfig(plugin)),
							   Formatter.printBoolean(debug.isEnabled(plugin)),
							   Formatter.printBoolean(debug.isLoaded(plugin)),
							   ChatColor.WHITE + plugin));
		}
	}
}
