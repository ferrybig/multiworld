/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.command.world.generator;

import multiworld.CommandException;
import multiworld.Utils;
import multiworld.chat.Formatter;
import multiworld.command.Command;
import multiworld.worldgen.WorldGenerator;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Fernando
 */
public class ListWorldGensCommand extends Command
{
	public ListWorldGensCommand()
	{
		super("listgens");
	}

	@Override
	public void runCommand(CommandSender s, String[] arguments)
	{
		WorldGenerator[] gens = WorldGenerator.values();
		for (WorldGenerator g : gens)
		{
			if (!g.mayInList())
			{
				continue;
			}
			Utils.sendMessage(s, ChatColor.GOLD + g.getName());
			Utils.sendMessage(s, " - " + g.getDestr(), 10);
			Utils.sendMessage(s, " - " + Formatter.printSpeed(g.getSpeed()));
		}

	}
}
