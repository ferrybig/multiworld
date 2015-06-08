/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.natives.entities;

import java.util.UUID;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.NativeCommandSender;

/**
 *
 * @author Fernando
 */
public interface NativePlayer extends NativeCommandSender, NativeLivingEntity {
    public UUID getUUID();
    
}
