/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.api;

import multiworld.ConfigException;
import multiworld.MultiWorldPlugin;
import multiworld.api.flag.FlagName;

/**
 *
 * @author Fernando
 */
public class MultiWorldAPI
{
	protected final MultiWorldPlugin plugin;

	/**
	 * The 1 argument constuctor, used intern
	 * @param plugin The main multiworld plugin
	 */
	public MultiWorldAPI(MultiWorldPlugin plugin)
	{
		this.plugin = plugin;
	}

	/**
	 * checks when multiworld is still loaded
	 * @return true if enabled, false other wisse
	 */
	protected boolean isValid()
	{
		if (this.plugin.isEnabled())
		{
			return true;
		}
		return false;
	}

	/**
	 * same as isValid(), but throws an IllegalStateException when not enabled
	 * @throws IllegalStateException When multiworld is not enabled
	 */
	protected void checkValid()
	{
		if (!isValid())
		{
			throw new IllegalStateException("MultiWorld disabled");
		}
	}

	/**
	 * Checks if an world is creative, same as <code>getWorld(world).getOptionValue(FlagName.CREATIVEWORLD)</code>
	 * @param world The world to check
	 * @return true when its an creative world, false otherwise
	 * @throws NullPointerException When the world specified by world has not been found
	 * @throws IllegalStateException When multiworld is not enabled
	 */
	public boolean isCreativeWorld(String world)
	{

		checkValid();
		return this.getWorld(world).getOptionValue(FlagName.CREATIVEWORLD);
	}

	/**
	 * Checks if an world is existing whit the specified name
	 * @param world The name to look for
	 * @return true if the specified world is existing, false otherwise
	 * @throws IllegalStateException When multiworld is not enabled
	 */
	public boolean isWorldExisting(String world)
	{
		checkValid();
		return this.getWorld(world) != null;
	}

	/**
	 * Looks if an world is loaded, same as <code>getWorld(world).isLoaded()</code>
	 * @param world Name to look for
	 * @return true when the world is loaded, false other wise
	 * @throws NullPointerException When the world specified by world has not been found
	 * @throws IllegalStateException When multiworld is not enabled
	 */
	public boolean isWorldLoaded(String world)
	{
		checkValid();
		return this.getWorld(world).isLoaded();
	}

	/**
	 * Gets an world by its name, loaded or not
	 * @param world World to find, case incense
	 * @return The world when found, false otherwise
	 * @throws IllegalStateException When multiworld is not enabled
	 */
	public MultiWorldWorldData getWorld(String world)
	{
		checkValid();
		return this.plugin.getDataManager().getWorldManager().getWorldMeta(world, false);
	}

	/**
	 * Get al the worlds that are loaded by multiworld
	 * @return an array whit al the worlds
	 * @throws IllegalStateException When multiworld is not enabled
	 */
	public MultiWorldWorldData[] getWorlds()
	{
		checkValid();
		return this.plugin.getDataManager().getWorldManager().getAllWorlds();
	}

	/**
	 * Check if an component is loaded (loaded is not being used)
	 * @param addon The type to check for
	 * @return true when its loaded, false otherwise
	 * @throws IllegalStateException When multiworld is not enabled
	 */
	public boolean isLoaded(PluginType addon)
	{
		checkValid();
		assert this.plugin.getPluginHandler() != null;
		switch (addon)
		{
			case GAMEMODE_CHANCER:
				return this.plugin.getPluginHandler().isLoaded("GameModeChancer");
			case END_PORTAL_HANDLER:
				return this.plugin.getPluginHandler().isLoaded("EndPortalHandler");
			case NETHER_PORTAL_HANDLER:
				return this.plugin.getPluginHandler().isLoaded("NetherPortalHandler");
			case WORLD_CHAT_SEPERATOR:
				return this.plugin.getPluginHandler().isLoaded("WorldChatSeperatorPlugin");
		}
		return false;
	}

	/**
	 * Check is an compoment is actually being used
	 * @param addon Component to check for
	 * @return true when its used, false otherwise
	 * @throws IllegalStateException When multiworld is not enabled
	 */
	public boolean isEnabled(PluginType addon)
	{
		checkValid();
		assert this.plugin.getPluginHandler() != null;
		switch (addon)
		{
			case GAMEMODE_CHANCER:
				return this.plugin.getPluginHandler().isEnabled("GameModeChancer");
			case END_PORTAL_HANDLER:
				return this.plugin.getPluginHandler().isEnabled("EndPortalHandler");
			case NETHER_PORTAL_HANDLER:
				return this.plugin.getPluginHandler().isEnabled("NetherPortalHandler");
			case WORLD_CHAT_SEPERATOR:
				return this.plugin.getPluginHandler().isEnabled("WorldChatSeperatorPlugin");
		}
		return false;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final MultiWorldAPI other = (MultiWorldAPI) obj;
		return this.plugin == other.plugin || (this.plugin != null && this.plugin.equals(other.plugin));
	}

	@Override
	public int hashCode()
	{
		return this.plugin != null ? this.plugin.hashCode():0;
	}

	@Override
	public String toString()
	{
		return "MultiWorldAPI{" + "plugin=" + plugin + '}';
	}

	/**
	 * Saves the configuration
	 * @throws ConfigurationSaveException When there was an error while saving
	 * @throws IllegalStateException When multiworld is not enabled
	 */
	public void saveConfig() throws ConfigurationSaveException
	{
		checkValid();
		try
		{
			this.plugin.getDataManager().save();
		}
		catch (ConfigException ex)
		{
			throw new ConfigurationSaveException("Error when callig save()", ex);
		}
	}

	public String getCaseCorrectName(String world)
	{
		MultiWorldWorldData w = this.getWorld(world);
		if (w == null)
		{
			return world;
		}
		return w.getName();
	}
}
