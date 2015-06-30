/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2;

import java.util.Collection;

/**
 *
 * @author Fernando
 */
public interface ConfigurationHandler {
    public void saveConfig();
    
    public void reloadConfig();
    
    public boolean isConfigUpdatedWithoutNotice();
    
    public void saveWorld(WorldDefinition world);
    
    public Collection<? extends WorldDefinition> loadWorlds();
}
