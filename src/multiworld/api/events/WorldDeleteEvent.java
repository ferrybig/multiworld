/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.api.events;

import multiworld.api.MultiWorldWorldData;
import org.bukkit.event.HandlerList;

/**
 *
 * @author Fernando
 */
public class WorldDeleteEvent extends WorldEvent
{
	public WorldDeleteEvent(MultiWorldWorldData world)
	{
		super(world);
	}
	private static final HandlerList handlers = new HandlerList();

	@Override
	public HandlerList getHandlers()
	{
		return handlers;
	}

	public static HandlerList getHandlerList()
	{
		return handlers;
	}
}
