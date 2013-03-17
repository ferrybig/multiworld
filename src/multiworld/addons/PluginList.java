/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.addons;

/**
 *
 * @author Fernando
 */
public interface PluginList
{
	public boolean isLoaded(String plugin);
	public boolean isEnabled(String plugin);
	public String[] getPlugins();
	public void disableAll();
	public boolean enabledInsideConfig(String plugin);
}
