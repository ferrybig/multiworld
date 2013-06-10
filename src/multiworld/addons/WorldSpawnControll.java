/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.addons;

import multiworld.data.DataHandler;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 *
 * @author Fernando
 */
public class WorldSpawnControll extends AddonBase implements MultiworldAddon, Listener
{
	public WorldSpawnControll(DataHandler data)
	{
		super(data);
	}

	@EventHandler(priority = EventPriority.LOWEST,ignoreCancelled = true)
	public void onRespawn(PlayerRespawnEvent event)
	{
		this.data.getLogger().fine("Got player respawn event");
		if (!isEnabled())
		{
			return;
		}
		assert this.data.getSpawns() != null; // This class may not be initized when the spawn if turned to false
		World to = this.data.getSpawns().resolveWorld(event.getPlayer().getWorld().getName());
		this.data.getLogger().fine("Chanced spawn of player "+event.getPlayer().getName()+" to world "+to.getName());
		event.setRespawnLocation(to.getSpawnLocation());
	}
}
