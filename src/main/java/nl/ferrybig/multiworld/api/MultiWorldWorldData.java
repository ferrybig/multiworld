/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ferrybig.multiworld.api;

import nl.ferrybig.multiworld.api.flag.FlagName;
import org.bukkit.World;

/**
 * @author Fernando
 */
public interface MultiWorldWorldData {

  /**
   * gets the name of the world
   *
   * @return the name
   */
  public String getName();

  /**
   * return true if loaded, false other wise
   *
   * @return true if loaded, false otherwise
   */
  public boolean isLoaded();

  /**
   * Gets the seed of this world
   *
   * @return The seed
   */
  public long getSeed();

  /**
   * Gets the dimension of this world
   *
   * @return The dimension
   */
  public World.Environment getDimension();

  /**
   * @param flag
   * @return
   */
  public boolean isOptionSet(FlagName flag);

  /**
   * @param flag
   * @return
   */
  public boolean getOptionValue(FlagName flag);

  /**
   * @param flag
   * @param newValue
   * @throws ConfigurationSaveException
   */
  public void setOptionValue(FlagName flag, boolean newValue) throws ConfigurationSaveException;

  /**
   * @return
   * @throws ConfigurationSaveException
   */
  public boolean loadWorld() throws ConfigurationSaveException;

  /**
   * @return
   * @throws ConfigurationSaveException
   */
  public boolean unloadWorld() throws ConfigurationSaveException;

  /**
   * @return
   */
  public String getGeneratorType();

  /**
   * @return
   */
  public World getBukkitWorld();

}
