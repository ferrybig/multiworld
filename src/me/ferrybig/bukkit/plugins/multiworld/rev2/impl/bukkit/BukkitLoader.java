/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.impl.bukkit;

import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.Native;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.entities.NativeEntityManager;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.inventory.NativeItemManager;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.materials.NativeMaterials;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.plugin.NativePluginManager;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.NativeBiomeManager;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.NativeWorldManager;
import org.bukkit.Server;

/**
 *
 * @author Fernando
 */
public class BukkitLoader implements Native {

    private final BukkitMain plugin;
//    private final Map<String, NativeGenerator> generators = new HashMap<>();
    private NativePluginManager pluginManager;
    private NativeWorldManager worldManager;
    private NativeEntityManager entityManager;
    private NativeMaterials materialManager;
    private NativeBiomeManager biomeManager;
    private NativeItemManager itemManager;
    private final Server server;

    public BukkitLoader(BukkitMain plugin, Server server) {
        this.plugin = plugin;
        this.server = server;
    }

    public void close() {

    }

    @Override
    public NativePluginManager getPluginManager() {
        return pluginManager;
    }

    public BukkitMain getPlugin() {
        return this.plugin;
    }

    @Override
    public NativeWorldManager getWorldManager() {
        return worldManager;
    }

    @Override
    public NativeEntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public NativeMaterials getMaterialManager() {
        return materialManager;
    }

    @Override
    public NativeBiomeManager getBiomeManager() {
        return biomeManager;
    }

    @Override
    public NativeItemManager getItemManager() {
        return itemManager;
    }

    @Override
    public Object getUnderlying() {
        return server;
    }

    public void setWorldManager(NativeWorldManager worldManager) {
        this.worldManager = worldManager;
    }

    public void setEntityManager(NativeEntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setMaterialManager(NativeMaterials materialManager) {
        this.materialManager = materialManager;
    }

    public void setBiomeManager(NativeBiomeManager biomeManager) {
        this.biomeManager = biomeManager;
    }

    public void setItemManager(NativeItemManager itemManager) {
        this.itemManager = itemManager;
    }

    public void setPluginManager(BukkitPluginManager pluginManager) {
        this.pluginManager = pluginManager;
    }

}
