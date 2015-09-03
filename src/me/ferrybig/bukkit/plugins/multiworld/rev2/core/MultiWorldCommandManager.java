/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.core;

import me.ferrybig.bukkit.plugins.multiworld.rev2.api.CommandManager;
import me.ferrybig.bukkit.plugins.multiworld.rev2.api.MultiWorldSubCommand;
import me.ferrybig.bukkit.plugins.multiworld.rev2.api.command.CommandStack;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.plugin.NativePlugin;

/**
 *
 * @author Fernando
 */
public class MultiWorldCommandManager implements CommandManager {

    @Override
    public boolean sendCommandStack(CommandStack stack) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addCommand(String name, MultiWorldSubCommand command, NativePlugin provider) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
