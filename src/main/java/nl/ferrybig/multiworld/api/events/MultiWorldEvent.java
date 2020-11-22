/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ferrybig.multiworld.api.events;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

/**
 * @author Fernando
 */
public abstract class MultiWorldEvent extends Event {

  public MultiWorldEvent() {
    super();
  }/*
private static final HandlerList handlers = new HandlerList();
	@Override
	public HandlerList getHandlers()
	{
		return handlers;
	}

	public static HandlerList getHandlerList()
	{
		return handlers;
	}*/

  /**
   * Passes this event to PluginManager.callEvent();
   */
  public void call() {
    Bukkit.getPluginManager().callEvent(this);
  }
}
