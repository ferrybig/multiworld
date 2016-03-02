/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.data.config;

import org.bukkit.configuration.ConfigurationSection;

/**
 *
 * @author ferrybig
 */
public class ConfigNodeSection extends ConfigNode<ConfigurationSection>
{

	public ConfigNodeSection(String configPath)
	{
		this(null, configPath);
	}

	public ConfigNodeSection(ConfigNode<ConfigurationSection> parent, String configPath)
	{
		super(parent, configPath, null);
	}

	@Override
	protected Object pack(ConfigurationSection data)
	{
		return data;
	}

	@Override
	protected ConfigurationSection unpack(Object rawConfigValue) throws DataPackException
	{
		return (ConfigurationSection) rawConfigValue;
	}

	@Override
	public ConfigurationSection get(ConfigurationSection from)
	{
		ConfigurationSection conf = from.getConfigurationSection(configPath);
		if (conf == null)
		{
			conf = from.createSection(configPath);
		}
		return conf;
	}

	@Override
	public void set(ConfigurationSection to, ConfigurationSection value)
	{
		to.createSection(configPath);
	}
}
