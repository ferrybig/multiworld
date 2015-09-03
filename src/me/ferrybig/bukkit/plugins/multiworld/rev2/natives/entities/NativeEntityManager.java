/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.natives.entities;

import java.util.Collection;
import java.util.UUID;

/**
 *
 * @author Fernando
 */
public interface NativeEntityManager {
    
    public NativePlayer getPlayer(String name);
    
    public NativePlayer getPlayer(UUID uuid);
    
    public Collection<? extends NativePermissionsHolder> getOps();
    
    public NativeConsoleCommandSender getConsoleCommandSender();

    public Collection<? extends NativePlayer> getPlayers();
}
