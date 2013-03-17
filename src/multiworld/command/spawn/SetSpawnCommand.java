/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.command.spawn;

import multiworld.CommandException;
import multiworld.Utils;
import multiworld.command.Command;
import multiworld.data.DataHandler;
import multiworld.data.PlayerHandler;
import multiworld.data.WorldHandler;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
		super("setspawn");
		this.w = worlds;
		this.p = player;
		this.d = data;
	}

	@Override
	public void runCommand(CommandSender s, String[] arguments) throws CommandException
	{
		// set the spawn for the concurent world
		Player player = this.p.getPlayer(s);
		Location l = player.getLocation();
		boolean succes = player.getWorld().setSpawnLocation(l.getBlockX(), l.getBlockY(), l.getBlockZ());
		if (succes)
		{
			Utils.sendMessage(s, this.d.getLang().getString("SPAWN_SET_SUCCES"));
		}
		else
		{
			Utils.sendMessage(s, this.d.getLang().getString("SPAWN_SET_FAIL"));
		}
	}
}
