/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.command.world;

import multiworld.CommandException;
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
public class ListCommand extends Command
{
	private final DataHandler data;

	public ListCommand(DataHandler data)
	{
		super("list");
		this.data = data;
	}

	@Override
	public void runCommand(CommandSender sender, String[] arguments) throws CommandException
	{
		sender.sendMessage(this.data.getLang().getString("world.list.header").split("\n"));
		InternalWorld[] worlds = this.data.getWorlds(false);
		for (InternalWorld world : worlds)
		{
			sender.sendMessage(Formatter.createList(ChatColor.GRAY,Formatter.printBoolean(this.data.isWorldLoaded(world.getName())),world.getName(),world.getWorldType()));
		}
	}
}
