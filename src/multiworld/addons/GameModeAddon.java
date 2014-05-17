/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.addons;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import multiworld.MultiWorldPlugin;
import multiworld.Utils;
import multiworld.api.events.FlagChanceEvent;
import multiworld.api.events.GameModeChanceByWorldEvent;
import multiworld.api.flag.FlagName;
import multiworld.data.DataHandler;
import multiworld.data.MyLogger;
import multiworld.flags.FlagValue;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author Fernando
 */
public final class GameModeAddon implements Listener, MultiworldAddon
{
	/**
	 * The players who have creative mode and their inv
	 */
	private HashMap<UUID, PlayerData> creativePlayers;
	private HashMap<UUID, RemovePlayerTask> tasks;
	/**
	 * is this class enabled?
	 */
	private boolean isEnabled = false;
	private final MyLogger log;
	private final DataHandler data;

	public GameModeAddon(DataHandler d)
	{

		this.log = d.getLogger();
		this.data = d;
	}

	/**
	 * Called when a player joins the server
	 * @param event The event data
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		this.log.finest("Got PlayerJoinEvent");//NOI18N
		if (this.tasks.containsKey(event.getPlayer().getUniqueId()))
		{
			Bukkit.getScheduler().cancelTask(this.tasks.get(event.getPlayer().getUniqueId()).getTaskId());
			this.tasks.remove(event.getPlayer().getUniqueId());
		}
		else
		{
			this.checkAndAddPlayer(event.getPlayer(), event.getPlayer().getWorld());
		}
	}

	/**
	 * called when a player normally leaves the server
	 * @param event The event data
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		this.log.finest("Got PlayerQuitEvent");//NOI18N
		Player player = event.getPlayer();
		this.tasks.put(player.getUniqueId(), new RemovePlayerTask(player));
	}

	/**
	 * called when a player respawns
	 * @param event The event data
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerChanceWorld(PlayerChangedWorldEvent event)
	{
		this.log.finest("Got PlayerChanceWorldEvent");//NOI18N
		this.reloadPlayer(event.getPlayer(), event.getPlayer().getWorld());
	}

	@EventHandler
	public void onFlagChance(FlagChanceEvent event)
	{
		if (!isEnabled)
		{
			return;
		}
		if (event.getFlag() == FlagName.CREATIVEWORLD)
		{
			for (Player p : event.getWorld().getBukkitWorld().getPlayers())
			{
				this.reloadPlayer(p, p.getWorld());
			}
		}
	}

	/**
	 * Removes a player from the data base
	 * @param player the player to be removed
	 */
	private void removePlayer(Player player)
	{
		if (!isEnabled)
		{
			return;
		}
		PlayerData tmp = this.creativePlayers.get(player.getUniqueId());
		if (tmp != null)
		{
			removePlayerAction(player, tmp);
			this.creativePlayers.remove(player.getUniqueId());
		}
	}

	/**
	 * The internal version of remove
	 * @param player The player to be removed
	 * @param database The database whit stuff to give to player
	 */
	private void removePlayerAction(Player player, PlayerData database)
	{
		if (database != null)
		{
			database.putOnPlayer(player);
		}
		player.setGameMode(GameMode.SURVIVAL);
		new GameModeChanceByWorldEvent(player, GameMode.SURVIVAL).call();
		this.log.fine("Chancing " + player.getDisplayName() + " game mode back to GameMode.SURVIVAL");//NOI18N
	}

	/**
	 * is the player affected
	 * @param player the player to check
	 * @return if it was affected
	 */
	private boolean isAffected(Player player)
	{
		return Utils.hasPermission(player, "creativemode");//NOI18N
	}

	/**
	 * checks and posible add the player to the database
	 * @param player
	 * @param toWorld
	 */
	private void checkAndAddPlayer(Player player, World toWorld)
	{
		if (!isEnabled || !this.isAffected(player))
		{
			return;
		}
		if (this.data.getWorldManager().getFlag(toWorld.getName(), FlagName.CREATIVEWORLD) == FlagValue.TRUE)
		{
			this.addPlayer(player);
		}
	}

	/**
	 * adds a a player to the database
	 * @param player The player to add
	 */
	private void addPlayer(Player player)
	{
		if (!this.creativePlayers.containsKey(player.getUniqueId()))
		{
			this.creativePlayers.put(player.getUniqueId(), this.data.getNode(DataHandler.OPTIONS_GAMEMODE_INV) ? PlayerData.getFromPlayer(player) : null);
		}
		player.setGameMode(GameMode.CREATIVE);
		new GameModeChanceByWorldEvent(player, GameMode.CREATIVE).call();
		this.log.fine("Chancing " + player.getDisplayName() + " game mode to GameMode.CREATIVE");//NOI18N
	}

	/**
	 * Reloads player data
	 * @param player the player to reload
	 * @param toWorld The world where he are now
	 */
	public void reloadPlayer(Player player, World toWorld)
	{
		if (!isEnabled || !this.isAffected(player))
		{
			return;
		}
		if (this.data.getWorldManager().getFlag(toWorld.getName(), FlagName.CREATIVEWORLD) == FlagValue.TRUE)
		{
			if (!this.creativePlayers.containsKey(player.getUniqueId()) && player.isOnline())
			{
				this.addPlayer(player);
			}
		}
		else
		{
			if (this.creativePlayers.containsKey(player.getUniqueId()))
			{
				this.removePlayer(player);
			}
		}
	}

	/**
	 * called when this plugin is disabled
	 */
	@Override
	public void onDisable()
	{
		Iterator<Map.Entry<UUID, PlayerData>> loop = this.creativePlayers.entrySet().iterator();
		Map.Entry<UUID, PlayerData> data;
		while (loop.hasNext())
		{
			data = loop.next();
			Player player = Bukkit.getPlayer(data.getKey());
			if (player != null)
			{
				this.removePlayerAction(player, data.getValue());
			}
		}
		for(RemovePlayerTask task : this.tasks.values())
		{
			task.run();
			Bukkit.getScheduler().cancelTask(task.getTaskId());
		}
		this.creativePlayers.clear();
		this.creativePlayers = null;
		this.isEnabled = false;
	}

	/**
	 * called when this plugin is enabled
	 */
	@Override
	public void onEnable()
	{
		this.tasks = new HashMap<UUID, RemovePlayerTask >(Math.min(20, Bukkit.getMaxPlayers()));
		this.creativePlayers = new HashMap<UUID, PlayerData>(Math.min(20, Bukkit.getMaxPlayers()));
		Bukkit.getScheduler().scheduleSyncDelayedTask(MultiWorldPlugin.getInstance(), new Runnable()
		{
			@Override
			public void run()
			{
				for (Player player : Bukkit.getOnlinePlayers())
				{
					checkAndAddPlayer(player, player.getWorld());
				}
			}
		});
		this.isEnabled = true;
	}

	/**
	 * is this plugin enaled
	 * @return true if this plugin is enabled, false otherwise
	 */
	@Override
	public boolean isEnabled()
	{
		return this.isEnabled;
	}

	private class RemovePlayerTask implements Runnable
	{
		private final Player player;
		private final int taskId = Bukkit.getScheduler().scheduleSyncDelayedTask(MultiWorldPlugin.getInstance(), this);

		RemovePlayerTask(Player player)
		{
			this.player = player;
		}

		@Override
		public void run()
		{
			try
			{
				GameModeAddon.this.removePlayer(player);
				player.saveData();
			}
			finally
			{
				GameModeAddon.this.tasks.remove(player.getUniqueId());
				GameModeAddon.this.creativePlayers.remove(player.getUniqueId());
			}
		}

		/**
		 * @return the taskId
		 */
		public int getTaskId()
		{
			return taskId;
		}
	}
}
