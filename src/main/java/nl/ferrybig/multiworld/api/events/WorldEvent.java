/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ferrybig.multiworld.api.events;

import nl.ferrybig.multiworld.api.MultiWorldWorldData;

/**
 * @author Fernando
 */
public abstract class WorldEvent extends MultiWorldEvent {

  private final MultiWorldWorldData world;

  public WorldEvent(MultiWorldWorldData world) {
    super();
    this.world = world;
  }

  /**
   * @return the world
   */
  public MultiWorldWorldData getWorld() {
    return world;
  }
}
