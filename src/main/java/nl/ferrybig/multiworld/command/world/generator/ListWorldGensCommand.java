/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ferrybig.multiworld.command.world.generator;

import nl.ferrybig.multiworld.chat.Formatter;
import nl.ferrybig.multiworld.command.Command;
import nl.ferrybig.multiworld.command.CommandStack;
import nl.ferrybig.multiworld.command.MessageType;
import nl.ferrybig.multiworld.worldgen.WorldGenerator;

/**
 *
 * @author Fernando
 */
public class ListWorldGensCommand extends Command
{
	public ListWorldGensCommand()
	{
		super("listgens","List al world generators installed inside this nl.ferrybig.multiworld build");
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
