/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.core;

import me.ferrybig.bukkit.plugins.multiworld.rev2.api.CommandManager;
import me.ferrybig.bukkit.plugins.multiworld.rev2.api.AddonRegistery;
import me.ferrybig.bukkit.plugins.multiworld.rev2.api.ConfigManager;
import me.ferrybig.bukkit.plugins.multiworld.rev2.api.MultiWorldEngine;
import me.ferrybig.bukkit.plugins.multiworld.rev2.api.WorldManager;
import me.ferrybig.bukkit.plugins.multiworld.rev2.api.command.CommandStack;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.Native;

/**
 *
 * @author Fernando
 */
public class MultiWorldCore implements MultiWorldEngine {

    
    private final WorldManager manager;
    private final Native nativeCore;
    private final AddonRegistery addons;
    private final ConfigManager config;
    private final CommandManager commands;

    public MultiWorldCore(WorldManager manager, Native nativeCore, AddonRegistery addons, ConfigManager config, CommandManager commands) {
        this.manager = manager;
        this.nativeCore = nativeCore;
        this.addons = addons;
        this.config = config;
        this.commands = commands;
    }
    
    @Override
    public void saveConfig() {
        config.saveConfig();
    }

    @Override
    public Native getNativePlugin() {
        return nativeCore;
    }

    @Override
    public AddonRegistery getPluginManager() {
        return addons;
    }

    @Override
    public boolean sendCommand(CommandStack stack) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public WorldManager getWorlds() {
        return manager;
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
