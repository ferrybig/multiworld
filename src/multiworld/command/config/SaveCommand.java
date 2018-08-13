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
public class SaveCommand extends Command
{
	private final ReloadHandler r;
	private final DataHandler d;

	public SaveCommand(DataHandler data, ReloadHandler reload)
	{
		super("save", "Saves data from multiworld to the disk");
		this.d = data;
		this.r = reload;
	}

	@Override
	public void runCommand(CommandStack stack)
	{
		if (this.saveCommand())
		{
			stack.sendMessageBroadcast(MessageType.SUCCES,Translation.COMMAND_SAVE_SUCCES);
		}
		else
		{
			stack.sendMessageBroadcast(MessageType.ERROR,Translation.COMMAND_SAVE_FAIL);
		}
	}

	private boolean saveCommand()
	{
		return this.r.save();
	}
}
