/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.addons;

import java.util.HashMap;
import java.util.Map;
import multiworld.data.ConfigNode;
import multiworld.data.DataHandler;

/**
 *
 * @author Fernando
 */
public class AddonMap implements  SettingsListener, PluginList
{
	Map<String,AddonHolder<?>> pluginMap = new HashMap<String,AddonHolder<?>>();
	private final DataHandler data;

	public AddonMap(DataHandler data)
	{
		
		this.data = data;
		this.addPlugin(WorldChatSeperatorPlugin.class,"WorldChatSeperatorPlugin",DataHandler.OPTIONS_WORLD_CHAT);
		this.addPlugin(NetherPortalHandler.class,"NetherPortalHandler",DataHandler.OPTIONS_LINK_NETHER);
		this.addPlugin(EndPortalHandler.class,"EndPortalHandler",DataHandler.OPTIONS_LINK_END);
		this.addPlugin(GameModeAddon.class,"GameModeChancer",DataHandler.OPTIONS_GAMEMODE);
		this.addPlugin(EnderChestBlokker.class,"EnderChestBlokker",DataHandler.OPTIONS_BLOCK_ENDER_CHESTS);
		this.addPlugin(WorldSpawnControll.class,"WorldSpawnHandler",DataHandler.OPTIONS_WORLD_SPAWN);
	}
	private <T extends MultiworldAddon> void addPlugin(Class<T> type, String name, ConfigNode<Boolean> config)
	{
		pluginMap.put(name.toUpperCase(), new AddonHolder<T>(type,name,data, config));
	}
	public AddonHolder<?> getPlugin(String plugin)
	{
		return pluginMap.get(plugin.toUpperCase());
	}

	@Override
	public void onSettingsChance()
	{
		for(AddonHolder<?> plugin : pluginMap.values())
		{
			plugin.onSettingsChance();
		}
	}

	@Override
	public boolean isLoaded(String plugin)
	{
		return getPlugin(plugin).isLoaded();
	}

	@Override
	public boolean isEnabled(String plugin)
	{
		return getPlugin(plugin).isEnabled();
	}

	@Override
	public String[] getPlugins()
	{
		String[] plugins = new String[this.pluginMap.size()];
		int i = 0;
		for(AddonHolder<?> plugin : this.pluginMap.values())
		{
			plugins[i] = plugin.getName();
			i++;
		}
		return plugins;
	}

	@Override
	public String toString()
	{
		return "AddonMap{" + "pluginMap=" + pluginMap + '}';
	}

	@Override
	public void disableAll()
	{
		for(AddonHolder<?> plugin : this.pluginMap.values())
		{
			if(plugin.isEnabled())
			{
				plugin.onDisable();
			}
		}
	}

	@Override
	public boolean enabledInsideConfig(String plugin)
	{
		return getPlugin(plugin).isEnabledInsideConfig();
	}
	
}
