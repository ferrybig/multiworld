/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.command.config;

import multiworld.command.Command;
import multiworld.command.CommandStack;
import multiworld.command.MessageType;
import multiworld.data.DataHandler;
import multiworld.data.ReloadHandler;
import multiworld.translation.Translation;

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
		super("load","Reloads the multiworld configuration file");
		this.d = d;
		this.r = r;
	}

	@Override
	public void runCommand(CommandStack stack)
	{
		if (this.reloadCommand())
		{
			stack.sendMessage(MessageType.SUCCES, Translation.COMMAND_RELOAD_SUCCES);
		}
		else
		{
			stack.sendMessage(MessageType.ERROR, Translation.COMMAND_RELOAD_FAIL);
		}
	}

	private boolean reloadCommand()
	{
		return r.reload();
	}
}
