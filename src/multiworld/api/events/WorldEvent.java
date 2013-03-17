/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.api.events;

import multiworld.api.MultiWorldWorldData;
import multiworld.api.flag.FlagName;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 *
 * @author Fernando
 */
public abstract class WorldEvent extends MultiWorldEvent
{
	private final MultiWorldWorldData world;
	public WorldEvent(MultiWorldWorldData world)
	{
		super();
		this.world = world;
	}

	/**
	 * @return the world
	 */
	public MultiWorldWorldData getWorld()
	{
		return world;
	}
}
