/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.api;

import me.ferrybig.bukkit.plugins.multiworld.rev2.api.command.CommandStack;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.plugin.NativePlugin;

/**
 *
 * @author Fernando
 */
public interface CommandManager {
    public boolean sendCommandStack(CommandStack stack);
    
    public void addCommand(String name, MultiWorldSubCommand command, NativePlugin provider);
}
