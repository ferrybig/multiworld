/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.command.flag;

import multiworld.api.flag.FlagName;
import multiworld.command.Command;
import multiworld.command.CommandStack;
import multiworld.command.MessageType;

/**
 *
 * @author Fernando
 */
public class FlagListCommand extends Command
{

	public FlagListCommand()
	{super("flaglist","gets al flags on the server");
	}

	@Override
	public void runCommand(CommandStack stack)
	{
		stack.sendMessage(MessageType.SUCCES,FlagName.makeFlagList());
	}

}
