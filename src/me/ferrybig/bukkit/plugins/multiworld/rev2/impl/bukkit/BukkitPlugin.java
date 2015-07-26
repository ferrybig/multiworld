/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.impl.bukkit;

import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.plugin.NativePlugin;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.plugin.NativePluginProperties;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author Fernando
 */
public class BukkitPlugin implements NativePlugin, NativePluginProperties {
    private final String name;
    private final String version;
    private final Plugin underlying;

    public BukkitPlugin(String name, String version, Plugin underlying, URL website) {
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

    @Override
    public NativePluginProperties getProperties() {
        return this;
    }

    @Override
    public Collection<String> getCreators() {
        return Collections.<String>emptyList();
    }

    @Override
    public URL getOrigin() {
        return null;
    }
    
}
