/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.data;

import java.util.List;
import multiworld.NotAPlayerException;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Fernando
 */
public class PlayerHandler
{
	private final DataHandler h;
	private final MyLogger log;

	public PlayerHandler(DataHandler h)
	{
		this.h = h;
		this.log = h.getLogger();
	}

	/**
	 * Move al the players from world 'from' to world 'to' whit as message 'warpOutMsg'
	 * @param from The target world
	 * @param to The destination world
	 * @param warpOutMsg The message to send to the players
	 */
	public void moveAllPlayers(World from, World to, String warpOutMsg)
	{
		if (from == to)
		{
			throw new IllegalArgumentException();
		}
		List<Player> playerList = from.getPlayers();
		Player[] players = new Player[playerList.size()];
		for (Player tmp : playerList.toArray(players))
		{
			this.movePlayer(tmp, to);
			if (!"".equals(warpOutMsg))
			{
				tmp.sendMessage(warpOutMsg);
			}
		}
	}

	/**
	 * Moves the speciefied player
	 *
	 * @param player the target player
	 * @param world the value of location
	 */
	
	public void movePlayer(Player player, World world)
	{
		movePlayer(player, world.getSpawnLocation());
	}

	/**
	 * Moves the speciefied player
	 * @param player the target player
	 * @param loc  
	 */
	public void movePlayer(Player player, Location loc)
	{
		World world = loc.getWorld();
		Chunk chunk;
		world.loadChunk(chunk = world.getChunkAt(loc));
		player.teleport(loc);
		this.log.fine("Warping " + player.getDisplayName() + " to location " + loc.toString()); //NOI18N
	}

	public Player getPlayer(String name)
	{
		return Bukkit.getPlayer(name);
	}

	public Player getPlayer(CommandSender s) throws NotAPlayerException
	{
		if (s instanceof Player)
		{
			return (Player) s;
		}
		throw new NotAPlayerException();
	}
}
