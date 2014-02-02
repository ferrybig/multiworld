/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.command.other;

import multiworld.command.Command;
import multiworld.command.CommandStack;
import multiworld.command.MessageType;

/**
 *
 * @author ferrybig
 */
public class EasterEggCommand extends Command
{

	public EasterEggCommand()
	{
		super(null,"Just a small hidden easter egg, ;P");
	}

	@Override
	public void runCommand(CommandStack stack)
	{
		if (stack.getCommandLabel().equals("multiworld"))
		{
			stack.sendMessage(MessageType.HIDDEN_SUCCES,
					  " ___###\n"
				+ "   /oo\\ |||\n"
				+ "   \\  / \\|/\n"
				+ "   /\"\"\\  I\n"
				+ "()|    |(I)\n"
				+ "   \\  /  I\n"
				+ "  /\"\"\"\"\\ I\n"
				+ " |      |I\n"
				+ " |      |I\n"
				+ "  \\____/ I");
		}
		else
		{
			switch (stack.getDebugLevel())
			{
				case NONE:
					stack.sendMessage(MessageType.HIDDEN_SUCCES, "There are no easter eggs inside this program!");
					break;
				case V:
					stack.sendMessage(MessageType.HIDDEN_SUCCES, "There realy are no easter eggs inside this program!");
					break;
				case VV:
					stack.sendMessage(MessageType.HIDDEN_SUCCES, "Did you know I really didn't added a easter egg into this command?");
					break;
				case VVV:
					stack.sendMessage(MessageType.HIDDEN_SUCCES, "STOP IT, or I will crash");
					break;
				case VVVV:
					stack.sendMessage(MessageType.HIDDEN_SUCCES, "If I give you a easter egg, will you stop then?");
					break;
				case VVVVV:
					stack.sendMessage(MessageType.HIDDEN_SUCCES, "Here you have your easter egg....");
					stack.sendMessage(MessageType.HIDDEN_SUCCES, "        /---\\                   ");
					stack.sendMessage(MessageType.HIDDEN_SUCCES, "-----/      \\------------------");
					stack.sendMessage(MessageType.HIDDEN_SUCCES, "-----\\      /------------------");
					stack.sendMessage(MessageType.HIDDEN_SUCCES, "        \\---/                   ");
					break;
				case VVVVVV:
					stack.sendMessage(MessageType.HIDDEN_SUCCES, "What is it? Its a snake that consumed a minecraft block");
					break;
			}
		}

	}

}
