/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.natives.bukkit;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.Native;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.NativeConsoleCommandSender;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.NativeLocation;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.NativePermissionsHolder;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.NativePlugin;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.NativePluginManager;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.entities.NativePlayer;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.events.NativeListener;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.generators.NativeGenerator;
import org.bukkit.entity.Player;

/**
 *
 * @author Fernando
 */
public class BukkitLoader implements Native {
    private final BukkitMain plugin;
    private final NativeConsoleCommandSender console;
    private final Map<String, NativeGenerator> generators = new HashMap<>();
    private final Map<Player, NativePlayer> players = new WeakHashMap<>();

    public BukkitLoader(BukkitMain plugin) {
        this.plugin = plugin;
        this.console = new BukkitConsoleSender(plugin, this);
        // prepopulate generators here
    }

    @Override
    public void registerWorldGenerator(NativeGenerator generator) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<String, NativeGenerator> getRegisteredGenerators() {
        return Collections.unmodifiableMap(generators);
    }

    @Override
    public void createWorld(NativeGenerator generator, UUID uuid, String name, long seed) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<? extends NativePermissionsHolder> getOps() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public NativeConsoleCommandSender getConsoleCommandSender() {
        return this.console;
    }
    
    public void close() {
        
    }

    @Override
    public NativePluginManager getPluginManager() {
        return new BukkitPluginManager(this);
    }

    @Override
    public NativePlayer getPlayer(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public NativePlayer getPlayer(UUID uuid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
