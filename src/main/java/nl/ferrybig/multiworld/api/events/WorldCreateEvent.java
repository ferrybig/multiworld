/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ferrybig.multiworld.api.events;

import nl.ferrybig.multiworld.api.MultiWorldWorldData;
import org.bukkit.event.HandlerList;

/**
 * @author Fernando
 */
public class WorldCreateEvent extends WorldEvent {

  private static final HandlerList handlers = new HandlerList();

  public WorldCreateEvent(MultiWorldWorldData world) {
    super(world);
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }
}
