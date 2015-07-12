/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.natives.bukkit;

import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.NativePlugin;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author Fernando
 */
public class BukkitPlugin implements NativePlugin {
    private final String name;
    private final String version;
    private final Plugin underlying;

    public BukkitPlugin(String name, String version, Plugin underlying) {
        this.name = name;
        this.version = version;
        this.underlying = underlying;
    }

    @Override
    public String getPluginName() {
        return name;
    }

    @Override
    public Object getUnderlyingObject() {
        return null;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public boolean isEnabled() {
        return underlying.isEnabled();
    }
    
}
