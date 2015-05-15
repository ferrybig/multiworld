package me.ferrybig.bukkit.plugins.multiworld.rev2.core;

import java.util.UUID;

public interface Native {
    public void registerWorldGenerator(NativeGenerator generator);
    
    public void createWorld(NativeGenerator generator, UUID uuid, String name, long seed);
}
