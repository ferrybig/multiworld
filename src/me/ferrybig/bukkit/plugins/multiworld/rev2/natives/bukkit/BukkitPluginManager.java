/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.natives.bukkit;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.NativePlugin;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.NativePluginManager;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.events.NativeListener;

/**
 *
 * @author Fernando
 */
public class BukkitPluginManager implements NativePluginManager {

    private final BukkitLoader bukkitLoader;
    private final Set<NativePlugin> localPlugins = new HashSet<>();

    public BukkitPluginManager(final BukkitLoader bukkitLoader) {
        this.bukkitLoader = bukkitLoader;
    }

    @Override
    public void registerEvents(NativeListener listener) {
    }

    @Override
    public Collection<? extends NativePlugin> getInstalledPlugin() {
        return localPlugins;
    }

}
