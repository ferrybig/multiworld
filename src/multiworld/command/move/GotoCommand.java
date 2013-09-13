/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.command.move;

import multiworld.ArgumentException;
import multiworld.CommandException;
import multiworld.Utils;
import multiworld.command.Command;
import multiworld.data.DataHandler;
import multiworld.data.InternalWorld;
import multiworld.data.PlayerHandler;
import multiworld.data.WorldHandler;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Fernando
 */
public class GotoCommand extends Command
{
	private final DataHandler h;
	private final PlayerHandler p;
	private final WorldHandler w;

	public GotoCommand(DataHandler h, PlayerHandler p, WorldHandler w)
	{
		super("goto");
		this.h = h;
		this.p = p;
		this.w = w;
	}

	@Override
	public void runCommand(CommandSender sender, String[] args) throws CommandException
	{
		Player player = this.p.getPlayer(sender);

		InternalWorld worldObj = w.getWorld(args[0], true);
		if (args.length != 1 && args.length != 4)
		{
			throw new ArgumentException("/goto <world>"); //NOI18N
		}
		Location warpLoc = worldObj.getWorld().getSpawnLocation();
		warpLoc.setWorld(worldObj.getWorld());
		if (args.length == 4)
		{
			Utils.canUseCommand(sender,this.getPermissions() + ".cordinates");
			double x = getCoordinate(sender, warpLoc.getX(), args[args.length - 3]);
			double y = getCoordinate(sender, warpLoc.getY(), args[args.length - 2], 0, 0);
			double z = getCoordinate(sender, warpLoc.getZ(), args[args.length - 1]);
			if (x == MIN_COORD_MINUS_ONE || y == MIN_COORD_MINUS_ONE || z == MIN_COORD_MINUS_ONE)
			{
				sender.sendMessage("Please provide a valid location!");
				return;
			}
			warpLoc.setX(x);
			warpLoc.setY(y);
			warpLoc.setZ(z);
		}
		p.movePlayer(player, warpLoc);
		sender.sendMessage(ChatColor.GREEN + this.h.getLang().getString("PLAYER WARP"));

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
}
