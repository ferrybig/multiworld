/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.impl.bukkit;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.entities.NativeConsoleCommandSender;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.entities.NativeEntityManager;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.entities.NativePermissionsHolder;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.entities.NativePlayer;
import org.bukkit.entity.Player;

/**
 *
 * @author Fernando
 */
public class BukkitEntityManager implements NativeEntityManager {

    private final Map<Player, NativePlayer> players = new WeakHashMap<>();
    private final NativeConsoleCommandSender console;

    public BukkitEntityManager(BukkitMain plugin, BukkitLoader nativez) {
        this.console = new BukkitConsoleSender(plugin, nativez);
    }

    @Override
    public NativePlayer getPlayer(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public NativePlayer getPlayer(UUID uuid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<? extends NativePermissionsHolder> getOps() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public NativeConsoleCommandSender getConsoleCommandSender() {
        return console;
    }
}
