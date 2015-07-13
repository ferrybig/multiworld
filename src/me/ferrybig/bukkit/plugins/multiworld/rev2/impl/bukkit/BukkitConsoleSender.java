/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.impl.bukkit;

import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.Native;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.NativeConsoleCommandSender;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.NativeLocation;

/**
 *
 * @author Fernando
 */
public class BukkitConsoleSender implements NativeConsoleCommandSender {

    private final BukkitMain plugin;
    private final BukkitLoader bukkitLoader;

    public BukkitConsoleSender(BukkitMain plugin, final BukkitLoader bukkitLoader) {
        this.bukkitLoader = bukkitLoader;
        this.plugin = plugin;
    }

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
        return bukkitLoader;
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

}
