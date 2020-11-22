/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ferrybig.multiworld.addons;

import nl.ferrybig.multiworld.data.DataHandler;

/**
 *
 * @author Fernando
 */
public class WorldTextureHandler implements MultiworldAddon
{
	private final DataHandler data;

	public WorldTextureHandler(DataHandler data)
	{
		this.data = data;
	}

	/**
	 * Get the value of data
	 *
	 * @return the value of data
	 */
	public DataHandler getData()
	{
		return data;
	}

	@Override
	public void onDisable()
	{
		
		
	}

	@Override
	public void onEnable()
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean isEnabled()
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
