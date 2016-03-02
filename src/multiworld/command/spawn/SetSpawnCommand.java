/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.command.spawn;

import multiworld.command.Command;
import multiworld.command.CommandStack;
import multiworld.command.MessageType;
import multiworld.data.DataHandler;
import multiworld.data.PlayerHandler;
import multiworld.data.WorldHandler;
import multiworld.translation.Translation;
import multiworld.translation.message.MessageCache;
import org.bukkit.Location;

/**
 *
 * @author Fernando
 */
public class SetSpawnCommand extends Command
{
	private final PlayerHandler p;
	private final WorldHandler w;
	private final DataHandler d;

	public SetSpawnCommand(DataHandler data, WorldHandler worlds, PlayerHandler player)
	{
		super("setspawn","Sets a spawn of a world");
		this.w = worlds;
		this.p = player;
		this.d = data;
	}

	@Override
	public void runCommand(CommandStack stack)
	{
		Location l = stack.getLocation();
		if(l == null)
		{
			stack.sendMessage(MessageType.ERROR, Translation.COMMAND_SPAWN_FAIL_CONSOLE);
			return;
		}
		boolean succes = l.getWorld().setSpawnLocation(l.getBlockX(), l.getBlockY(), l.getBlockZ());
		if (succes)
		{
			stack.sendMessageBroadcast(MessageType.SUCCES,
					  Translation.COMMAND_SETSPAWN_SUCCESS,
					  MessageCache.custom("%x%", String.valueOf(l.getBlockX())),
					  MessageCache.custom("%y%", String.valueOf(l.getBlockY())),
					  MessageCache.custom("%z%", String.valueOf(l.getBlockZ())),
					  MessageCache.WORLD.get(l.getWorld().getName()));
		}
		else
		{
			stack.sendMessage(MessageType.ERROR,Translation.COMMAND_SETSPAWN_FAIL);
		}
	}
}
