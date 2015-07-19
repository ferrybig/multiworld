/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.impl.bukkit;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.NativePlugin;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.NativePluginManager;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.events.NativeListener;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author Fernando
 */
public class BukkitPluginManager implements NativePluginManager {

    private final BukkitLoader bukkitLoader;
    private final Set<NativePlugin> localPlugins = new HashSet<>();
    private final NativePlugin me;

    public BukkitPluginManager(final BukkitLoader bukkitLoader) {
        this.bukkitLoader = bukkitLoader;
        this.me = new BukkitPlugin(bukkitLoader.getPlugin().getDescription().getName(),bukkitLoader.getPlugin().getDescription().getVersion(), bukkitLoader.getPlugin(), bukkitLoader.getPlugin().getDescription().getWebsite());
        for(Plugin plugin: bukkitLoader.getPlugin().getServer().getPluginManager().getPlugins()) {
            if(bukkitLoader.getPlugin() == plugin) {
                localPlugins.add(me);
            } else {
                localPlugins.add(new BukkitPlugin(plugin.getDescription().getName(), plugin.getDescription().getVersion(), plugin, plugin.getDescription().getWebsite()));
            }
        }
    }

    @Override
    public void registerEvents(NativeListener listener) {
    }

    @Override
    public Collection<? extends NativePlugin> getInstalledPlugin() {
        return localPlugins;
    }
    
    public NativePlugin getUs() {
        return me;
    }
    
}
