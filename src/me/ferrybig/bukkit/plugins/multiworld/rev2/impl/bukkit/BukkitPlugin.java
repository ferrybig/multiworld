/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.impl.bukkit;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
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
    private final URL website;
    private final List<String> creators;

    public BukkitPlugin(String name, String version, Plugin underlying, String website, List<String> creators) {
        this.name = name;
        this.version = version;
        this.underlying = underlying;
        URL url;
        try {
            url = new URL(website);
        } catch(MalformedURLException ex) {
            url = null;
        }
        this.website = url;
        this.creators = creators;
    }

    @Override
    public String getPluginName() {
        return name;
    }

    @Override
    public Object getUnderlyingObject() {
        return underlying;
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
        return creators;
    }

    @Override
    public URL getOrigin() {
        return website;
    }
    
}
