/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.data;

import multiworld.WorldGenException;
import multiworld.api.MultiWorldWorldData;
import multiworld.api.flag.FlagName;
import multiworld.flags.FlagValue;
import multiworld.worldgen.WorldGenerator;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

/**
 *
 * @author Fernando
 */
public interface WorldUtils
{
	public boolean deleteWorld(String world);

	public MultiWorldWorldData[] getAllWorlds();

	/**
	 * Gets an flag from the specified world
	 * <p>
	 * @param worldName
	 * @param flag The flag to return
	 * @return The value of the specified flag
	 */
	public FlagValue getFlag(String worldName, FlagName flag);

	public InternalWorld getInternalWorld(String name, boolean mustBeLoaded);

	public InternalWorld[] getLoadedWorlds();

	public World getWorld(String name);

	public WorldContainer getWorldMeta(String world, boolean mustLoad);

	public InternalWorld[] getWorlds(boolean b);

	public boolean isWorldLoaded(String name);

	public World loadWorld(String name);

	public boolean makeWorld(String name, WorldGenerator env, long seed, String options) throws WorldGenException;

	/**
	 * Sets the end portal from world "fromworld" to be redirected to "toworld"
	 * <p>
	 * @param fromWorld
	 * @param toWorld
	 * @return
	 */
	public boolean setEndPortal(String fromWorld, String toWorld);

	/**
	 * Sets an flag on a world
	 * <p>
	 * @param world the world to set on
	 * @param flag The flag to affect
	 * @param value The new value
	 */
	public void setFlag(String world, FlagName flag, FlagValue value);

	/**
	 * Sets the nether portal from world "fromworld" to be redirected to "toworld"
	 * <p>
	 * @param fromWorld
	 * @param toWorld
	 * @return
	 */
	public boolean setPortal(String fromWorld, String toWorld);

	public boolean unloadWorld(String world);

	public WorldContainer[] getWorlds();

	public void loadWorlds(ConfigurationSection worldList, MyLogger logger, Difficulty baseDifficulty, SpawnWorldControl spawn);

	public void saveWorlds(ConfigurationSection worldSection, MyLogger log, SpawnWorldControl spawn);

	public boolean isWorldExisting(String world);

}
