/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.api.events;

import multiworld.api.MultiWorldWorldData;
import multiworld.api.flag.FlagName;
import org.bukkit.event.HandlerList;

/**
 *
 * @author Fernando
 */
public class FlagChanceEvent extends MultiWorldEvent
{
	private final MultiWorldWorldData world;
	private final FlagName flag;
	private final boolean newValue;
	public FlagChanceEvent(MultiWorldWorldData world,FlagName flag,boolean newValue)
	{
		super();
		this.world = world;
		this.flag = flag;
		this.newValue = newValue;
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

	/**
	 * @return the flag
	 */
	public FlagName getFlag()
	{
		return flag;
	}

	/**
	 * @return the newValue
	 */
	public boolean getNewValue()
	{
		return newValue;
	}

	/**
	 * @return the world
	 */
	public MultiWorldWorldData getWorld()
	{
		return world;
	}
}
