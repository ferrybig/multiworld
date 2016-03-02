/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.command.move;

import multiworld.Utils;
import multiworld.command.ArgumentType;
import multiworld.command.Command;
import multiworld.command.CommandStack;
import multiworld.command.MessageType;
import multiworld.data.DataHandler;
import multiworld.data.InternalWorld;
import multiworld.data.PlayerHandler;
import multiworld.data.WorldHandler;
import multiworld.translation.Translation;
import multiworld.translation.message.MessageCache;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Fernando
 */
public class MoveCommand extends Command
{
	private final PlayerHandler p;
	private final WorldHandler w;
	private final DataHandler d;

	public MoveCommand(DataHandler data, PlayerHandler player, WorldHandler worlds)
	{
		super("move","Moves a player to a other world");
		this.p = player;
		this.w = worlds;
		this.d = data;
	}

	@Override
	public void runCommand(CommandStack stack)
	{
		String[] args = stack.getArguments();
		if (args.length != 2 && args.length != 5)
		{
			stack.sendMessageUsage(stack.getCommandLabel(), ArgumentType.valueOf("move"), ArgumentType.TARGET_PLAYER, ArgumentType.TARGET_WORLD);
		}
		else
		{
			Player targetPlayer = Bukkit.getPlayer(args[0]);
			InternalWorld worldObj = w.getWorld(args[1], true);
			if (targetPlayer == null)
			{
				stack.sendMessage(MessageType.ERROR, Translation.PLAYER_NOT_FOUND, MessageCache.PLAYER.get(args[0]));
				return;
			}
			if (worldObj == null)
			{
				stack.sendMessage(MessageType.ERROR, Translation.WORLD_NOT_FOUND, MessageCache.WORLD.get(args[1]));
				return;
			}
			Location warpLoc = worldObj.getWorld().getSpawnLocation();
			warpLoc.setWorld(worldObj.getWorld());
			if (args.length == 5)
			{
				if (!Utils.canUseCommand(stack, this.getPermissions() + ".cordinates"))
				{
					return;
				}
				double x = getCoordinate(warpLoc.getX(), args[args.length - 3]);
				double y = getCoordinate(warpLoc.getY(), args[args.length - 2], 0, 0);
				double z = getCoordinate(warpLoc.getZ(), args[args.length - 1]);
				if (x == MIN_COORD_MINUS_ONE || y == MIN_COORD_MINUS_ONE || z == MIN_COORD_MINUS_ONE)
				{
					stack.sendMessage(MessageType.ERROR, Translation.INVALID_LOCATION);
					return;
				}
				warpLoc.setX(x);
				warpLoc.setY(y);
				warpLoc.setZ(z);
			}
			p.movePlayer(targetPlayer, warpLoc);
			stack.sendMessageBroadcast(MessageType.SUCCES,
						   Translation.COMMAND_MOVE_MESSAGE_SUCCES,
						   MessageCache.PLAYER.get(targetPlayer.getName()),
						   MessageCache.WORLD.get(warpLoc.getWorld().getName()));
		}
	}

	@Override
	public String[] calculateMissingArguments(CommandSender sender, String commandName, String[] split)
	{
		if (split.length == 0)
		{
			return this.calculateMissingArgumentsPlayer("", (sender instanceof Player) ? (Player)sender : null);
		}
		else if (split.length == 1)
		{
			return this.calculateMissingArgumentsPlayer(split[0], (sender instanceof Player) ? (Player)sender : null);
		}
		else if (split.length == 2)
		{
			return this.calculateMissingArgumentsWorld(split[1]);
		}
		else
		{
			return EMPTY_STRING_ARRAY;
		}
	}
}
