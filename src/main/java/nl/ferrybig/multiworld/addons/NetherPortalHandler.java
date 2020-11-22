/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ferrybig.multiworld.addons;

import nl.ferrybig.multiworld.data.DataHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.player.PlayerPortalEvent;

/**
 * @author Fernando
 */
public class NetherPortalHandler extends PortalHandler {

  public NetherPortalHandler(DataHandler data) {
    super(data, Bukkit.getServer(), data.getLogger(), false);
  }

  /**
   * Overriden methode to prevent a bug
   *
   * @param event
   */
  @EventHandler(priority = EventPriority.LOWEST)
  @Override
  public void onPlayerPortal(EntityPortalEvent event) {
    super.onPlayerPortal(event);
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerPortal(PlayerPortalEvent event) {
    final EntityPortalEvent evt = new EntityPortalEvent(event.getPlayer(), event.getFrom(),
        event.getTo(), event.getPortalTravelAgent());
    evt.setCancelled(event.isCancelled());
    this.onPlayerPortal(evt);
    event.setCancelled(evt.isCancelled());
    event.setTo(evt.getTo());
    event.setFrom(evt.getFrom());
    event.setPortalTravelAgent(evt.getPortalTravelAgent());
  }
}
