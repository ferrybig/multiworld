/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.data;

import multiworld.MultiWorldPlugin;
import org.bukkit.configuration.ConfigurationSection;

/**
 *
 * @author Fernando
 */
public final class ConfigNode<T>
{
	private final String configPath;
	private final T defaultValue;
	private final Class<T> type;
	private final ConfigNode<ConfigurationSection> parent;

	public ConfigNode(String configPath, T defaultValue, Class<T> type)
	{
		this(null, configPath, defaultValue, type);
	}

	public ConfigNode(ConfigNode<ConfigurationSection> parent, String configPath, T defaultValue, Class<T> type)
	{
		if (configPath.isEmpty())
		{
			throw new IllegalArgumentException("null configPath");
		}
		this.parent = parent;
		this.configPath = configPath;
		this.defaultValue = defaultValue;
		this.type = type;
	}

	/**
	 * @return the configName
	 */
	public String getConfigPath()
	{
		return configPath;
	}

	/**
	 * @return the defaultValue
	 */
	public T getDefaultValue()
	{
		return defaultValue;
	}

	@SuppressWarnings(
	{
		"unchecked", "unchecked"
	})
	public T get(ConfigurationSection from)
	{
		MyLogger logger = null;
		DataHandler d = MultiWorldPlugin.getInstance().getDataManager();
		if (d != null)
		{
			logger = d.getLogger();
		}
		if (this.parent != null)
		{
			from = this.parent.get(from);
		}
		if (this.type == ConfigurationSection.class)
		{
			ConfigurationSection conf = from.getConfigurationSection(configPath);
			if (conf == null)
			{
				conf = from.createSection(configPath);
			}
			if (logger != null)
			{
				if (!conf.getCurrentPath().equals(this.getFullPath()))
				{
					logger.warning(conf.getCurrentPath() + " != " + this.getFullPath());
				}
			}
			return this.type.cast(conf);
		}
		else
		{

			T defaultValue1 = this.getDefaultValue();
			if (!from.contains(configPath))
			{
				if (logger != null)
				{
					logger.fine("Adding missing config node: " + this.getFullPath());
				}

				this.set1(from, defaultValue);
				return defaultValue;
			}
			else
			{
				Object get = from.get(configPath, defaultValue1);

				if (type.isAssignableFrom(get.getClass()) || defaultValue == null)
				{
					return type.cast(get);
				}
				else
				{
					if (logger != null)
					{
						logger.warning("Error with node \"" + this.getFullPath() + "\" plz fix it, it has been replaced by the default value");
					}
					this.set1(from, defaultValue);
					return defaultValue1;
				}
			}
		}
	}

	public void set(ConfigurationSection to, T value)
	{
		System.out.println(to.getCurrentPath());
		if (this.parent != null)
		{
			to = this.parent.get(to);
		}
		this.set1(to, value);
	}

	public void set1(ConfigurationSection to, T value)
	{
		to.set(this.configPath, value);
		// ^^ bitch line, history of the greatest bug evar: to.set(this.configPath, to);
	}

	@Override
	public String toString()
	{
		return "ConfigNode{" + "configPath=" + configPath + ", defaultValue=" + defaultValue + ", type=" + type + '}';
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
		@SuppressWarnings("unchecked")
		final ConfigNode<?> other = (ConfigNode<?>) obj;
		if ((this.configPath == null) ? (other.configPath != null) : !this.configPath.equals(other.configPath))
		{
			return false;
		}
		if (this.defaultValue != other.defaultValue && (this.defaultValue == null || !this.defaultValue.equals(other.defaultValue)))
		{
			return false;
		}
		if (this.type != other.type && (this.type == null || !this.type.equals(other.type)))
		{
			return false;
		}
		return true;
	}

	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = 59 * hash + (this.configPath != null ? this.configPath.hashCode() : 0);
		hash = 59 * hash + (this.defaultValue != null ? this.defaultValue.hashCode() : 0);
		hash = 59 * hash + (this.type != null ? this.type.hashCode() : 0);
		return hash;
	}

	private String getFullPath()
	{
		return (parent == null ? "" : (parent.getFullPath() + ".")) + this.configPath;
	}
}
