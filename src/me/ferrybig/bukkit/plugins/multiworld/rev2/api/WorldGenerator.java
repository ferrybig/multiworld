package me.ferrybig.bukkit.plugins.multiworld.rev2.api;

import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.plugin.NativePlugin;

public interface WorldGenerator {
    public boolean isExternal();
    
    public NativePlugin getProvider();
}
