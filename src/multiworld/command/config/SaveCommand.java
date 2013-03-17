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
public class SaveCommand extends Command
{
	private final ReloadHandler r;
	private final DataHandler d;

	public SaveCommand(DataHandler data, ReloadHandler reload)
	{
		super("save");
		this.d = data;
		this.r = reload;
	}

	@Override
	public void runCommand(CommandSender s, String[] arguments) throws CommandException
	{
		if (this.saveCommand())
					{
						s.sendMessage(ChatColor.GREEN + this.d.getLang().getString("SAVE SUCCES"));
					}
					else
					{
						s.sendMessage(ChatColor.RED + this.d.getLang().getString("SAVE ERR"));
					}
	}
	private boolean saveCommand()
	{
		return this.r.save();
	}
}
