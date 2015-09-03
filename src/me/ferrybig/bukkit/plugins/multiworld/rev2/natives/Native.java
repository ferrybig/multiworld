package me.ferrybig.bukkit.plugins.multiworld.rev2.natives;

import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.entities.NativeEntityManager;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.inventory.NativeItemManager;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.materials.NativeMaterialManager;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.plugin.NativePluginManager;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.NativeBiomeManager;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.NativeWorldManager;

public interface Native {

    public NativeWorldManager getWorldManager();

    public NativeEntityManager getEntityManager();

    public NativePluginManager getPluginManager();

    public NativeMaterialManager getMaterialManager();

    public NativeBiomeManager getBiomeManager();

    public NativeItemManager getItemManager();

    public Object getUnderlying();
}
