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
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
		super("move");
		this.p = player;
		this.w = worlds;
		this.d = data;
	}

	@Override
	public void runCommand(CommandSender s, String[] split) throws CommandException
	{
		if (split.length != 2)
		{
			throw new ArgumentException("/mw move <player> <world>"); //NOI18N
		}
		else
		{
			Player targetPlayer = Bukkit.getPlayer(split[0]);
			InternalWorld destinationWorld = Utils.getWorld(split[1], this.d, true);
			if (targetPlayer == null)
			{
				s.sendMessage(ChatColor.RED + this.d.getLang().getString("PLAYER NOT FOUND"));
				return;
			}

			this.p.movePlayer(targetPlayer, destinationWorld.getWorld());
			targetPlayer.sendMessage("You are been moved to world \"" + destinationWorld.getName() + "\" by: " + Utils.getPlayerName(s));
			s.sendMessage("Moved player");

		}
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
			return this.calculateMissingArgumentsPlayer(split[0]);
		}else if (split.length == 2)
		{
			return this.calculateMissingArgumentsWorld(split[1]);
		}else
		{
			return EMPTY_STRING_ARRAY;
		}
	}
}
