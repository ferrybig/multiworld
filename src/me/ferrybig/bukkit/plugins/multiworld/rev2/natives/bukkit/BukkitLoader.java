/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.natives.bukkit;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.Native;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.NativeConsoleCommandSender;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.NativeLocation;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.NativePermissionsHolder;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.NativePluginManager;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.generators.NativeGenerator;

/**
 *
 * @author Fernando
 */
public class BukkitLoader implements Native {
    private final BukkitMain plugin;
    private final NativeConsoleCommandSender console;
    private final Map<String, NativeGenerator> generators = new HashMap<>();

    public BukkitLoader(BukkitMain plugin) {
        this.plugin = plugin;
        this.console = new NativeConsoleCommandSender() {

            @Override
            public void sendMessage(String message) {
                plugin.getServer().getConsoleSender().sendMessage(message);
            }

            @Override
            public String getName() {
                return plugin.getServer().getConsoleSender().getName();
            }

            @Override
            public Native getNative() {
                return BukkitLoader.this;
            }

            @Override
            public boolean hasPermision(String permission) {
                return plugin.getServer().getConsoleSender().hasPermission(permission);
            }

            @Override
            public boolean hasLocation() {
                return false;
            }

            @Override
            public NativeLocation getLocation() {
                return null;
            }

            @Override
            public boolean canTeleport() {
                return false;
            }

            @Override
            public boolean teleport(NativeLocation location) {
                return false;
            }
        };
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
