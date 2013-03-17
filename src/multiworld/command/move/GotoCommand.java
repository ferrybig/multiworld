/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.command.move;

import multiworld.ArgumentException;
import multiworld.CommandException;
import multiworld.command.Command;
import multiworld.data.DataHandler;
import multiworld.data.InternalWorld;
import multiworld.data.PlayerHandler;
import multiworld.data.WorldHandler;
import org.bukkit.ChatColor;
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
	public void runCommand(CommandSender sender, String[] arg) throws CommandException
	{
		Player player = this.p.getPlayer(sender);
		if (arg.length != 1)
		{
			throw new ArgumentException("/goto <world>"); //NOI18N
		}
		InternalWorld worldObj = w.getWorld(arg[0],true);
		p.movePlayer(player, worldObj.getWorld());
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
		}else
		{
			return EMPTY_STRING_ARRAY;
		}
	}
}
