/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.command.spawn;

import multiworld.NotAPlayerException;
import multiworld.Utils;
import multiworld.command.Command;
import multiworld.data.DataHandler;
import multiworld.data.PlayerHandler;
import multiworld.data.WorldHandler;
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
		super("spawn");
		this.d = data;
		this.w = worlds;
		this.p = player;
	}

	@Override
	public void runCommand(CommandSender s, String[] arguments) throws NotAPlayerException
	{
		Player player = this.p.getPlayer(s);
		player.teleport(player.getWorld().getSpawnLocation());
		Utils.sendMessage(s, this.d.getLang().getString("SPAWN_WARP"));
	}
}