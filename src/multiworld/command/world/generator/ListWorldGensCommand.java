/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.command.world.generator;

import multiworld.chat.Formatter;
import multiworld.command.Command;
import multiworld.command.CommandStack;
import multiworld.command.MessageType;
import multiworld.worldgen.WorldGenerator;

/**
 *
 * @author Fernando
 */
public class ListWorldGensCommand extends Command
{
	public ListWorldGensCommand()
	{
		super("listgens","List al world generators installed inside this multiworld build");
	}

	@Override
	public void runCommand(CommandStack stack)
	{
		WorldGenerator[] gens = WorldGenerator.values();
		for (WorldGenerator g : gens)
		{
			if (!g.mayInList())
			{
				continue;
			}
			stack.sendMessage(MessageType.HIDDEN_SUCCES, g.getName());
			stack.sendMessage(MessageType.HIDDEN_SUCCES, "+- "+g.getDestr());
			stack.sendMessage(MessageType.HIDDEN_SUCCES, "+- "+Formatter.printSpeed(g.getSpeed()));
		}
	}
}
