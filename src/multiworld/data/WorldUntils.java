/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.data;

import multiworld.ConfigException;
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
public interface WorldUntils
{

	boolean deleteWorld(String world, boolean mustSave) throws ConfigException;

	MultiWorldWorldData[] getAllWorlds();

	/**
	 * Gets an flag from the specified world
	 * @param worldName
	 * @param flag The flag to return
	 * @return The value of the specified flag
	 */
	FlagValue getFlag(String worldName, FlagName flag);


	InternalWorld getInternalWorld(String name, boolean mustBeLoaded);

	InternalWorld[] getLoadedWorlds();

	World getWorld(String name);

	WorldContainer getWorldMeta(String world, boolean mustLoad);

	InternalWorld[] getWorlds(boolean b);

	boolean isWorldLoaded(String name);

	World loadWorld(String name, boolean mustSave) throws ConfigException;

	boolean makeWorld(String name, WorldGenerator env, long seed, String options) throws ConfigException, WorldGenException;

	/**
	 * Sets the end portal from world "fromworld" to be redirected to "toworld"
	 * @param fromWorld
	 * @param toWorld
	 * @return
	 */
	boolean setEndPortal(String fromWorld, String toWorld);

	/**
	 * Sets an flag on a world
	 * @param world the world to set on
	 * @param flag The flag to affect
	 * @param value The new value
	 * @throws ConfigException When there was an <code>Exception</code> while saving
	 */
	void setFlag(String world, FlagName flag, FlagValue value) throws ConfigException;

	/**
	 * Sets the nether portal from world "fromworld" to be redirected to "toworld"
	 * @param fromWorld
	 * @param toWorld
	 * @return
	 */
	boolean setPortal(String fromWorld, String toWorld);

	boolean unloadWorld(String world, boolean mustSave) throws ConfigException;

	WorldContainer[] getWorlds();

	void loadWorlds(ConfigurationSection worldList, MyLogger logger, Difficulty baseDifficulty, SpawnWorldControl spawn);

	void saveWorlds(ConfigurationSection worldSection, MyLogger log, SpawnWorldControl spawn);
	
}
