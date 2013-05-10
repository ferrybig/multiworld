/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.command.flag;

import multiworld.CommandException;
import multiworld.Utils;
import multiworld.api.flag.FlagName;
import multiworld.command.Command;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Fernando
 */
public class FlagListCommand extends Command
{

	public FlagListCommand()
	{super("flaglist");
	}

	@Override
	public void runCommand(CommandSender s, String[] arguments) throws CommandException
	{
		Utils.sendMessage(s,FlagName.makeFlagList());
	}
	
}
