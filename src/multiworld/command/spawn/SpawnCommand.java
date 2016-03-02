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
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Fernando
 */
public class SpawnCommand extends Command
{
	private final DataHandler d;
	private final PlayerHandler p;
	private final WorldHandler w;

	public SpawnCommand(DataHandler data, WorldHandler worlds, PlayerHandler player)
	{
		super("spawn","Teleports yourself to spawn");
		this.d = data;
		this.w = worlds;
		this.p = player;
	}

	@Override
	public void runCommand(CommandStack stack)
	{
		CommandSender sender = stack.getSender();
		if(sender instanceof Player)
		{
			Player player = (Player) sender;
			player.teleport(player.getWorld().getSpawnLocation());
			stack.sendMessage(MessageType.SUCCES, Translation.COMMAND_SPAWN_SUCCES);
		}
		else
		{
			stack.sendMessage(MessageType.ERROR, Translation.COMMAND_SPAWN_FAIL_CONSOLE);
		}
	}
}