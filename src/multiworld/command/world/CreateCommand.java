/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.command.world;

import multiworld.InvalidWorldGenException;
import multiworld.InvalidWorldGenOptionsException;
import multiworld.Utils;
import multiworld.WorldGenException;
import multiworld.command.ArgumentType;
import multiworld.command.Command;
import multiworld.command.CommandStack;
import multiworld.command.MessageType;
import multiworld.data.DataHandler;
import multiworld.data.MyLogger;
import multiworld.translation.Translation;
import multiworld.translation.message.MessageCache;
import multiworld.worldgen.WorldGenerator;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Fernando
 */
public class CreateCommand extends Command
{
	private final DataHandler data;
	private final MyLogger log;

	public CreateCommand(DataHandler data)
	{
		super("world.create","Creates a new world");
		this.data = data;
		this.log = data.getLogger();
	}

	@Override
	public String[] calculateMissingArguments(CommandSender sender, String commandName, String[] split)
	{
		if (split.length == 0)
		{
			return EMPTY_STRING_ARRAY;
		}
		else if (split.length == 1)
		{
			return EMPTY_STRING_ARRAY;
		}
		else if (split.length == 2)
		{
			return this.calculateMissingArgumentsWorldGenerator(split[1]);
		}
		else
		{
			return EMPTY_STRING_ARRAY;
		}
	}

	@Override
	public void runCommand(CommandStack stack)
	{
		String[] args = stack.getArguments();
		if (args.length == 0)
		{
			stack.sendMessageUsage(stack.getCommandLabel(),
					       ArgumentType.valueOf("create"),
					       ArgumentType.NEW_WORLD_NAME,
					       ArgumentType.valueOf("<generator>:<options>"),
					       ArgumentType.valueOf("<seed>"));
		}
		else
		{
			if (!Utils.checkWorldName(args[0]))
			{
				stack.sendMessage(MessageType.ERROR,
						  Translation.INVALID_WORLD,
						  MessageCache.WORLD.get(args[0]));
				return;
			}
			if (data.getWorldManager().getInternalWorld(args[0], false) != null)
			{
				stack.sendMessage(MessageType.ERROR,
						  Translation.COMMAND_CREATE_WORLD_EXISTS,
						  MessageCache.WORLD.get(args[0]));
				return;
			}
			long seed = (new java.util.Random()).nextLong();
			WorldGenerator env = WorldGenerator.NORMAL;
			String genOptions = ""; //NOI18N
			String genName;
			try
			{
				if (args.length > 1)
				{

					genName = args[1];
					int index = genName.indexOf(':'); //NOI18N
					if (index != -1)
					{
						genOptions = genName.substring(index + 1);
						genName = genName.substring(0, index);
					}
					env = WorldGenerator.getGenByName(genName);
					if (args.length > 2)
					{
						try
						{
							seed = Long.parseLong(args[2]);
						}
						catch (NumberFormatException e)
						{
							seed = args[2].hashCode();
						}
					}

				}
			}
			catch (InvalidWorldGenException ex)
			{
				String error = "Not found:" + ex.getWrongGen();
				stack.sendMessageBroadcast(
					MessageType.ERROR,
					Translation.COMMAND_CREATE_GET_ERROR,
					MessageCache.custom("%error%", error));
				stack.sendMessage(MessageType.ERROR,
						  Translation.COMMAND_CREATE_GET_ERROR,
						  MessageCache.custom("%error%", error));
				return;
			}

			stack.sendMessageBroadcast(null,
						   Translation.COMMAND_CREATE_START,
						   MessageCache.WORLD.get(args[0]),
						   MessageCache.GENERATOR.get(env.getName()),
						   MessageCache.GENERATOR_OPTION.get(genOptions),
						   MessageCache.SEED.get(String.valueOf(seed)));
			try
			{
				if (this.data.getWorldManager().makeWorld(args[0], env, seed, genOptions))
				{
					this.data.scheduleSave();
					stack.sendMessage(MessageType.SUCCES, Translation.COMMAND_CREATE_SUCCES, MessageCache.WORLD.get(args[0]));
				}
			}
			catch (InvalidWorldGenOptionsException error)
			{
				stack.sendMessageBroadcast(
					MessageType.ERROR,
					Translation.COMMAND_CREATE_GET_PRE_ERROR,
					MessageCache.custom("%error%", error.getMessage()));
				stack.sendMessage(
					MessageType.ERROR,
					Translation.COMMAND_CREATE_GET_ERROR,
					MessageCache.custom("%error%", error.getMessage()));
			}
			catch (WorldGenException error)
			{
				stack.sendMessageBroadcast(
					MessageType.ERROR,
					Translation.COMMAND_CREATE_GET_PRE_ERROR,
					MessageCache.custom("%error%", error.getMessage()));
				stack.sendMessage(
					MessageType.ERROR,
					Translation.COMMAND_CREATE_GET_ERROR,
					MessageCache.custom("%error%", error.getMessage()));
			}
		}
	}
}
