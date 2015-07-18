/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.natives;

/**
 *
 * @author Fernando
 */
public interface NativePlugin {
    public Object getUnderlyingObject();
    
    public boolean isEnabled();
    
    public NativePluginProperties getProperties();
    
}
