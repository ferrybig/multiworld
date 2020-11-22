package nl.ferrybig.multiworld.addons;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import nl.ferrybig.multiworld.MultiWorldPlugin;
import nl.ferrybig.multiworld.Utils;
import nl.ferrybig.multiworld.api.events.FlagChanceEvent;
import nl.ferrybig.multiworld.api.events.GameModeChanceByWorldEvent;
import nl.ferrybig.multiworld.api.flag.FlagName;
import nl.ferrybig.multiworld.data.DataHandler;
import nl.ferrybig.multiworld.data.MyLogger;
import nl.ferrybig.multiworld.flags.FlagValue;
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

public final class GameModeAddon implements Listener, MultiworldAddon {

  private final MyLogger log;
  private final DataHandler data;
  private HashMap<UUID, PlayerData> creativePlayers;
  private HashMap<UUID, RemovePlayerTask> tasks;
  private boolean isEnabled = false;

  public GameModeAddon(DataHandler d) {

    this.log = d.getLogger();
    this.data = d;
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerJoin(PlayerJoinEvent event) {
    this.log.finest("Got PlayerJoinEvent");//NOI18N
    if (this.tasks.containsKey(event.getPlayer().getUniqueId())) {
      Bukkit.getScheduler().cancelTask(this.tasks.get(event.getPlayer().getUniqueId()).getTaskId());
      this.tasks.remove(event.getPlayer().getUniqueId());
    } else {
      this.checkAndAddPlayer(event.getPlayer(), event.getPlayer().getWorld());
    }
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerQuit(PlayerQuitEvent event) {
    this.log.finest("Got PlayerQuitEvent");//NOI18N
    Player player = event.getPlayer();
    this.tasks.put(player.getUniqueId(), new RemovePlayerTask(player));
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerChanceWorld(PlayerChangedWorldEvent event) {
    this.log.finest("Got PlayerChanceWorldEvent");//NOI18N
    this.reloadPlayer(event.getPlayer(), event.getPlayer().getWorld());
  }

  @EventHandler
  public void onFlagChance(FlagChanceEvent event) {
    if (!isEnabled) {
      return;
    }
    if (event.getFlag() == FlagName.CREATIVEWORLD) {
      for (Player p : event.getWorld().getBukkitWorld().getPlayers()) {
        this.reloadPlayer(p, p.getWorld());
      }
    }
  }

  private void removePlayer(Player player) {
    if (!isEnabled) {
      return;
    }
    PlayerData tmp = this.creativePlayers.get(player.getUniqueId());
    if (tmp != null) {
      removePlayerAction(player, tmp);
      this.creativePlayers.remove(player.getUniqueId());
    }
  }

  private void removePlayerAction(Player player, PlayerData database) {
    if (database != null) {
      database.putOnPlayer(player);
    }
    player.setGameMode(GameMode.SURVIVAL);
    new GameModeChanceByWorldEvent(player, GameMode.SURVIVAL).call();
    this.log.fine(
        "Chancing " + player.getDisplayName() + " game mode back to GameMode.SURVIVAL");//NOI18N
  }

  private boolean isAffected(Player player) {
    return Utils.hasPermission(player, "creativemode");//NOI18N
  }

  private void checkAndAddPlayer(Player player, World toWorld) {
    if (!isEnabled || !this.isAffected(player)) {
      return;
    }
    if (this.data.getWorldManager().getFlag(toWorld.getName(), FlagName.CREATIVEWORLD)
        == FlagValue.TRUE) {
      this.addPlayer(player);
    }
  }

  private void addPlayer(Player player) {
    if (!this.creativePlayers.containsKey(player.getUniqueId())) {
      this.creativePlayers.put(player.getUniqueId(),
          this.data.getNode(DataHandler.OPTIONS_GAMEMODE_INV) ? PlayerData.getFromPlayer(player)
              : null);
    }
    player.setGameMode(GameMode.CREATIVE);
    new GameModeChanceByWorldEvent(player, GameMode.CREATIVE).call();
    this.log
        .fine("Chancing " + player.getDisplayName() + " game mode to GameMode.CREATIVE");//NOI18N
  }

  public void reloadPlayer(Player player, World toWorld) {
    if (!isEnabled || !this.isAffected(player)) {
      return;
    }
    if (this.data.getWorldManager().getFlag(toWorld.getName(), FlagName.CREATIVEWORLD)
        == FlagValue.TRUE) {
      if (!this.creativePlayers.containsKey(player.getUniqueId()) && player.isOnline()) {
        this.addPlayer(player);
      }
    } else {
      if (this.creativePlayers.containsKey(player.getUniqueId())) {
        this.removePlayer(player);
      }
    }
  }

  @Override
  public void onDisable() {
    Iterator<Map.Entry<UUID, PlayerData>> loop = this.creativePlayers.entrySet().iterator();
    Map.Entry<UUID, PlayerData> data;
    while (loop.hasNext()) {
      data = loop.next();
      Player player = Bukkit.getPlayer(data.getKey());
      if (player != null) {
        this.removePlayerAction(player, data.getValue());
      }
    }
    for (RemovePlayerTask task : this.tasks.values()) {
      task.run();
      Bukkit.getScheduler().cancelTask(task.getTaskId());
    }
    this.creativePlayers.clear();
    this.creativePlayers = null;
    this.isEnabled = false;
  }

  @Override
  public void onEnable() {
    this.tasks = new HashMap<UUID, RemovePlayerTask>(Math.min(20, Bukkit.getMaxPlayers()));
    this.creativePlayers = new HashMap<UUID, PlayerData>(Math.min(20, Bukkit.getMaxPlayers()));
    Bukkit.getScheduler().scheduleSyncDelayedTask(MultiWorldPlugin.getInstance(), new Runnable() {
      @Override
      public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
          checkAndAddPlayer(player, player.getWorld());
        }
      }
    });
    this.isEnabled = true;
  }

  @Override
  public boolean isEnabled() {
    return this.isEnabled;
  }

  private class RemovePlayerTask implements Runnable {

    private final Player player;
    private final int taskId = Bukkit.getScheduler()
        .scheduleSyncDelayedTask(MultiWorldPlugin.getInstance(), this);

    RemovePlayerTask(Player player) {
      this.player = player;
    }

    @Override
    public void run() {
      try {
        GameModeAddon.this.removePlayer(player);
        player.saveData();
      } finally {
        GameModeAddon.this.tasks.remove(player.getUniqueId());
        GameModeAddon.this.creativePlayers.remove(player.getUniqueId());
      }
    }

    public int getTaskId() {
      return taskId;
    }
  }
}
