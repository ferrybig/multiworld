package me.ferrybig.bukkit.plugins.multiworld.rev2.natives;

import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.entities.NativeEntityManager;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.inventory.NativeItemManager;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.materials.NativeMaterials;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.plugin.NativePluginManager;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.NativeBiomes;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.NativeWorldManager;

public interface Native {

    public NativeWorldManager getWorldManager();

    public NativeEntityManager getEntityManager();

    public NativePluginManager getPluginManager();

    public NativeMaterials getMaterialManager();

    public NativeBiomes getBiomeManager();

    public NativeItemManager getItemManager();

    public Object getUnderlying();
}
