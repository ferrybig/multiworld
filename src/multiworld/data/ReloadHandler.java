/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.data;

import multiworld.ConfigException;
import multiworld.addons.AddonHandler;

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
			this.d.getLogger().throwing("multiworld.data.ReloadHandler", "reload", e); //NOI18N
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
			this.d.getLogger().throwing("multiworld.data.ReloadHandler", "save", e); //NOI18N
			return false;
		}
		return true;
	}
}
