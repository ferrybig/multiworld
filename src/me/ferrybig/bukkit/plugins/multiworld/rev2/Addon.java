/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2;

import java.util.Collection;
import java.util.Map;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.events.NativeListener;

/**
 *
 * @author Fernando
 */
public interface Addon {
    public String getName();
    
    public String getVersion();
    
    public boolean isEnabled();
    
    public void enable();
    
    public void disable();
    
    public boolean isCoreAddon();
    
    public Collection<? extends NativeListener> getListeners();
    
    public Map<String, ? extends Object> saveToConfig();
    
    public void loadFromConfig(Map<String, ?> map);
    
    public boolean needsConfig();
}
