/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.addons;

import multiworld.data.DataHandler;

/**
 *
 * @author Fernando
 */
public abstract class AddonBase implements MultiworldAddon
{
	protected final DataHandler data;
	private boolean isEnabled;

	public AddonBase(DataHandler data)
	{
		this.data = data;
	}

	/**
	 * called when this plugin is disabled
	 */
	@Override
	public void onDisable()
	{
		this.isEnabled = false;
	}

	/**
	 * called when this plugin is enabled
	 */
	@Override
	public void onEnable()
	{
		this.isEnabled = true;
	}

	@Override
	public boolean isEnabled()
	{
		return this.isEnabled;
	}
}
