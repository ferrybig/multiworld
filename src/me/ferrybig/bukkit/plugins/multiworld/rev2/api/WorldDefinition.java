package me.ferrybig.bukkit.plugins.multiworld.rev2.api;

import java.util.Map;
import java.util.UUID;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.NativeWorld;

public interface WorldDefinition {

    public NativeWorld getWorld();

    public String getName();

    public UUID getUUID();
    
    public NativeWorld.Environment getEnviroment();

    public Map<String, String> getGamerules();
}
