/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.addons;

import multiworld.data.DataHandler;
import multiworld.data.MyLogger;

/**
 *
 * @author Fernando
 */
public interface MultiworldAddon
{
	public abstract void onDisable();
	public abstract void onEnable();
	public abstract boolean isEnabled();
}
