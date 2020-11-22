/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ferrybig.multiworld.data;

import nl.ferrybig.multiworld.ConfigException;
import nl.ferrybig.multiworld.addons.AddonHandler;

/**
 *
 * @author Fernando
 */
public class ReloadHandler
{
	private final DataHandler d;private final AddonHandler p;
	public ReloadHandler(DataHandler data,AddonHandler plugins)
	{
		this.d = data;
		this.p = plugins;
	}
	public boolean reload()
	{
		try
		{
			this.d.load();
			this.p.onSettingsChance();
		}
		catch (ConfigException e)
		{
			this.d.getLogger().throwing("nl.ferrybig.multiworld.data.ReloadHandler", "reload", e); //NOI18N
			return false;
		}
		return true;
	}

	public boolean save()
	{
		try
		{
			this.d.save();
		}
		catch (ConfigException e)
		{
			this.d.getLogger().throwing("nl.ferrybig.multiworld.data.ReloadHandler", "save", e); //NOI18N
			return false;
		}
		return true;
	}
}
