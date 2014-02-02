/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.command.world;

import java.util.Arrays;
import java.util.Comparator;
import multiworld.command.Command;
import multiworld.command.CommandStack;
import multiworld.command.MessageType;
import multiworld.data.DataHandler;
import multiworld.data.InternalWorld;
import multiworld.translation.Translation;
import multiworld.translation.message.MessageCache;
import org.bukkit.ChatColor;

/**
 *
 * @author Fernando
 */
public class ListCommand extends Command
{
	private final DataHandler data;

	public ListCommand(DataHandler data)
	{
		super("list","Lists al worlds on the server");
		this.data = data;
	}

	@Override
	public void runCommand(CommandStack stack)
	{
		stack.sendMessage(MessageType.SUCCES, Translation.COMMAND_LIST_HEADER);
		InternalWorld[] worlds = this.data.getWorldManager().getWorlds(false);
		Arrays.sort(worlds, new Comparator<InternalWorld>() {

			@Override
			public int compare(InternalWorld t, InternalWorld t1)
			{
				return t.getName().compareToIgnoreCase(t1.getName());
			}
		});
		for (InternalWorld world : worlds)
		{
			stack.sendMessage(MessageType.HIDDEN_SUCCES,
					  Translation.COMMAND_LIST_DATA,
					  MessageCache.WORLD.get(world.getName()),
					  MessageCache.custom("%loaded%", (this.data.getWorldManager().isWorldLoaded(world.getName()) ? ChatColor.GREEN+"Loaded" : ChatColor.RED + "Unloaded")+RESET),
					  MessageCache.custom("%type%", world.getOptions().isEmpty() ? world.getFullGeneratorName() : world.getFullGeneratorName() + ":" + world.getOptions())
			);
		}
	}
}
