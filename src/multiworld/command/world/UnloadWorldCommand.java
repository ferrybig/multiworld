package multiworld.command.world;

import multiworld.command.ArgumentType;
import multiworld.command.Command;
import static multiworld.command.Command.EMPTY_STRING_ARRAY;
import multiworld.command.CommandStack;
import multiworld.command.MessageType;
import multiworld.data.DataHandler;
import multiworld.data.WorldHandler;
import multiworld.translation.Translation;
import multiworld.translation.message.MessageCache;
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
