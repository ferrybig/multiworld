/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ferrybig.multiworld.addons;

import nl.ferrybig.multiworld.api.flag.FlagName;
import nl.ferrybig.multiworld.data.DataHandler;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 *
 * @author Fernando
 */
public class EnderChestBlokker implements MultiworldAddon, Listener
{
	final DataHandler data;

	public EnderChestBlokker(DataHandler data)
	{
		this.data = data;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockPlace(BlockPlaceEvent evt)
	{
		if (enabled)
		{
			if (this.data.getWorldManager().getFlag(evt.getPlayer().getWorld().getName(), FlagName.CREATIVEWORLD).getAsBoolean())
			{
				if (evt.getBlockPlaced().getType() == Material.ENDER_CHEST)
				{
					evt.setCancelled(true);
				}
			}
		}
	}

	@Override
	public void onDisable()
	{
		enabled = false;
	}

	@Override
	public void onEnable()
	{
		enabled = true;
	}
	private boolean enabled = false;

	public boolean isEnabled()
	{
		return enabled;
	}
}
