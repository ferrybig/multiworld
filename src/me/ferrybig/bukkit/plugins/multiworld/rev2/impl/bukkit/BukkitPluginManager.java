/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.impl.bukkit;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.plugin.NativePlugin;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.plugin.NativePluginManager;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.events.NativeListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

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
        BukkitMain pluginOfMe = bukkitLoader.getPlugin();
        PluginDescriptionFile descriptionOfMe = pluginOfMe.getDescription();
        this.me = new BukkitPlugin(descriptionOfMe.getName(),
                descriptionOfMe.getVersion(), pluginOfMe,
                descriptionOfMe.getWebsite(),
                descriptionOfMe.getAuthors());
        for (Plugin plugin : pluginOfMe.getServer().getPluginManager().getPlugins()) {
            if (pluginOfMe == plugin) {
                localPlugins.add(me);
            } else {
                PluginDescriptionFile descriptionOfPlugin = plugin.getDescription();
                localPlugins.add(new BukkitPlugin(descriptionOfPlugin.getName(),
                        descriptionOfPlugin.getVersion(), plugin,
                        descriptionOfPlugin.getWebsite(),
                        descriptionOfPlugin.getAuthors()));
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
