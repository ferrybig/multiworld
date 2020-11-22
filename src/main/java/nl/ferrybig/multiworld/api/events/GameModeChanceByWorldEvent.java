/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ferrybig.multiworld.api.events;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

/**
 * @author Fernando
 */
public class GameModeChanceByWorldEvent extends MultiWorldEvent {

  private static final HandlerList handlers = new HandlerList();
  private final Player player;
  private final GameMode newMode;

  public GameModeChanceByWorldEvent(Player player, GameMode newMode) {
    super();
    this.player = player;
    this.newMode = newMode;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  /**
   * @return the player
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * @return the newMode
   */
  public GameMode getNewMode() {
    return newMode;
  }
}
