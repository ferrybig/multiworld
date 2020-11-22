package nl.ferrybig.multiworld.command.world;

import nl.ferrybig.multiworld.command.ArgumentType;
import nl.ferrybig.multiworld.command.Command;
import nl.ferrybig.multiworld.command.CommandStack;
import nl.ferrybig.multiworld.command.MessageType;
import nl.ferrybig.multiworld.data.DataHandler;
import nl.ferrybig.multiworld.data.WorldHandler;
import nl.ferrybig.multiworld.translation.Translation;
import nl.ferrybig.multiworld.translation.message.MessageCache;
import org.bukkit.command.CommandSender;

/**
 *
 * @author ferrybig
 */
public class UnloadWorldCommand extends Command
{
	private final DataHandler data;
	private final WorldHandler worlds;

	public UnloadWorldCommand(DataHandler data, WorldHandler worlds)
	{
		super("world.unload","Unloads a world");
		this.data = data;
		this.worlds = worlds;
	}

	@Override
	public String[] calculateMissingArguments(CommandSender sender, String commandName, String[] split)
	{
		if (split.length < 2) {
			return this.calculateMissingArgumentsWorld("");
		} else {
			return EMPTY_STRING_ARRAY;
		}
	}

	@Override
	public void runCommand(CommandStack stack)
	{
		String[] arguments = stack.getArguments();
		if (arguments.length != 1)
		{
			stack.sendMessageUsage(stack.getCommandLabel(), ArgumentType.valueOf("unload"), ArgumentType.TARGET_WORLD);
			return;
		}
		final String worldName = arguments[0];
		if (!this.worlds.isWorldExistingAndSendMessage(worldName, stack))
		{
			return;
		}
		if (!this.worlds.isWorldLoaded(worldName))
		{
			stack.sendMessage(MessageType.ERROR,
					  Translation.WORLD_UNLOADED_ALREADY,
					  MessageCache.WORLD.get(worldName));
			return;
		}
		stack.sendMessageBroadcast(null,
					   Translation.WORLD_UNLOADING_START,
					   MessageCache.WORLD.get(worldName));
		if (this.worlds.unloadWorld(worldName))
		{
			stack.sendMessageBroadcast(MessageType.SUCCES,
						   Translation.WORLD_UNLOADING_END,
						   MessageCache.WORLD.get(worldName));
		}
		else
		{
			stack.sendMessageBroadcast(MessageType.ERROR,
						   Translation.WORLD_UNLOADING_FAILED,
						   MessageCache.WORLD.get(worldName));
		}
	}
}
