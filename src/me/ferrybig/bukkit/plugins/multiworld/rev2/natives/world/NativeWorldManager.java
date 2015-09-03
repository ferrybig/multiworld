/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.generators.NativeGenerator;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.generators.NativeWorldSettings;

/**
 *
 * @author Fernando
 */
public interface NativeWorldManager {

    public void registerWorldGenerator(NativeGenerator generator);

    public Map<String, NativeGenerator> getRegisteredGenerators();
    
    public NativeWorldSettings createEmptySettings();

    public void createWorld(NativeGenerator generator, UUID uuid, String name, long seed, int dimension, NativeWorldSettings settings);
    
    public Set<? extends NativeWorld> getLoadedWorlds();
}
