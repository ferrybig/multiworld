/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.command.config;

import multiworld.CommandException;
import multiworld.command.Command;
import multiworld.data.DataHandler;
import multiworld.data.ReloadHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Fernando
 */
public class LoadCommand extends Command
{
	private final ReloadHandler r;
	private final DataHandler d;

	public LoadCommand(DataHandler d, ReloadHandler r)
	{
		super("load");
		this.d = d;
		this.r = r;
	}

	@Override
	public void runCommand(CommandSender sender, String[] arguments) throws CommandException
	{
		if (this.reloadCommand())
		{
			sender.sendMessage(ChatColor.GREEN + this.d.getLang().getString("RELOAD SUCCESS"));
		}
		else
		{
			sender.sendMessage(ChatColor.RED + this.d.getLang().getString("RELOAD ERR"));
		}
	}

	private boolean reloadCommand()
	{
		return r.reload();
	}
}
