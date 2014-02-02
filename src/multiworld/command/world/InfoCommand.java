/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.command.world;

import multiworld.command.ArgumentType;
import multiworld.command.Command;
import multiworld.command.CommandStack;
import multiworld.command.MessageType;
import multiworld.data.InternalWorld;
import multiworld.data.WorldHandler;
import multiworld.translation.Translation;
import multiworld.translation.message.MessageCache;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Fernando
 */
public class InfoCommand extends Command
{
	private final WorldHandler w;

	public InfoCommand(WorldHandler w)
	{
		super("info","Shows information about a world");
		this.w = w;
	}

	@Override
	public String[] calculateMissingArguments(CommandSender sender, String commandName, String[] split)
	{
		if (split.length == 0)
		{
			return this.calculateMissingArgumentsWorld("");
		}
		else if (split.length == 1)
		{
			return this.calculateMissingArgumentsWorld(split[0]);
		}
		else
		{
			return EMPTY_STRING_ARRAY;
		}
	}

	@Override
	public void runCommand(CommandStack stack)
	{
		String worldName;
		worldName = stack.getLocation().getWorld().getName();
		String[] args = stack.getArguments();
		if (args.length != 0)
		{
			worldName = args[0];
		}
		if (worldName == null)
		{
			stack.sendMessageUsage(stack.getCommandLabel(), ArgumentType.valueOf("list"), ArgumentType.TARGET_WORLD);
			return;
		}
		if (w.isWorldExistingAndSendMessage(worldName, stack))
		{
			InternalWorld worldObj = this.w.getWorld(worldName, false);
			stack.sendMessage(MessageType.HIDDEN_SUCCES,
					  Translation.COMMAND_INFO_DATA,
					  MessageCache.WORLD.get(worldObj.getName()),
					  MessageCache.SEED.get(String.valueOf(worldObj.getSeed())),
					  MessageCache.GENERATOR.get(worldObj.getFullGeneratorName()),
					  MessageCache.custom("%options%", worldObj.getOptions()),
					  MessageCache.ENVIOMENT.get(String.valueOf(worldObj.getEnv())),
					  MessageCache.DIFFICULTY.get(String.valueOf(worldObj.getDifficulty())));
		}
	}
}
