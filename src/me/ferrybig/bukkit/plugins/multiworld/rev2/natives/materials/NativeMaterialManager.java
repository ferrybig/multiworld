/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.natives.materials;

import java.util.Collection;

/**
 *
 * @author Fernando
 */
public interface NativeMaterialManager {

    public NativeMaterial requireMaterial(String fullname);

    public NativeMaterial requireMaterial(short id);

    public NativeMaterial getMaterial(String fullname);

    public NativeMaterial getMaterial(short id);

    public NativeMaterial[] getClosestMatches(String name, int distance);

    public boolean hasDynamicItems();

    public boolean canRegisterItems();
    
    public Collection<? extends NativeMaterial> getAllItems();

}
