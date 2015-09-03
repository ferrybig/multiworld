/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.natives.events;

import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.location.NativeLocation;

/**
 *
 * @author Fernando
 */
public interface EntityEnteredPortalEvent extends EntityEvent {
    public NativeLocation getFrom();
    public boolean isNetherPortal();
    public boolean isEndPortal();
    public NativeLocation getNonPreciseTargetLocation();
    public NativeLocation getPreciseTargetLocation();
    public void setNonPreciseTargetLocation(NativeLocation loc);
    public void setPreciseTargetLocation(NativeLocation loc);
}
