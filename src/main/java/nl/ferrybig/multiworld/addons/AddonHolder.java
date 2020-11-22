/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ferrybig.multiworld.addons;

import nl.ferrybig.multiworld.MultiWorldPlugin;
import nl.ferrybig.multiworld.data.config.DefaultConfigNode;
import nl.ferrybig.multiworld.data.DataHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

/**
 *
 * @param <T> 
 * @author Fernando
 */
public class AddonHolder<T extends MultiworldAddon> implements MultiworldAddon, SettingsListener
{
	private T addon = null;
	private final Class<T> type;
	private final String name;
	private final DataHandler data;
	private final DefaultConfigNode<Boolean> updateNode;

	public AddonHolder(Class<T> type, String name, DataHandler data, DefaultConfigNode<Boolean> updateNode)
	{
		if (type == null || name == null || data == null)
		{
			throw new NullPointerException("null not accepted as param");
		}
		this.type = type;
		this.name = name;
		this.data = data;
		this.updateNode = updateNode;

	}

	@Override
	public void onDisable()
	{
		assert getAddon() != null;
		this.data.getLogger().fine("Disabling plugin \"" + type.getSimpleName() + "\"");
		this.getAddon().onDisable();
	}

	@Override
	public void onEnable()
	{

		if (this.getAddon() == null)
		{
			try
			{
				this.addon = type.getConstructor(DataHandler.class).newInstance(this.data);
				assert getAddon() != null;
				this.data.getLogger().fine("Loaded plugin \"" + type.getSimpleName() + "\"");
				if (this.getAddon() instanceof Listener)
				{
					Bukkit.getServer().getPluginManager().registerEvents((Listener) this.getAddon(), MultiWorldPlugin.getInstance());
				}
			}
			catch (Exception ex)
			{
				throw new RuntimeException("Mistake from delevoper: " + ex.toString(), ex);
			}
		}
		this.data.getLogger().fine("Enabling plugin \"" + type.getSimpleName() + "\"");
		this.getAddon().onEnable();
	}

	@Override
	public void onSettingsChance()
	{
		if (isEnabledInsideConfig())
		{
			if (!this.isEnabled())
			{
				this.onEnable();
			}
		}
		else
		{
			if (this.isEnabled())
			{
				this.onDisable();
			}
		}
	}

	public boolean isLoaded()
	{
		return this.getAddon() != null;
	}

	@Override
	public boolean isEnabled()
	{
		if (this.getAddon() == null)
		{
			return false;
		}
		else
		{
			return this.getAddon().isEnabled();
		}
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	@Override
	public String toString()
	{
		return "AddonHolder{" + "addon=" + getAddon() + ", type=" + type + ", name=" + name + ", updateNode=" + updateNode + '}';
	}

	/**
	 * @return the addon
	 */
	public T getAddon()
	{
		return addon;
	}

	boolean isEnabledInsideConfig()
	{
		return this.data.getNode(this.updateNode);
	}
}
