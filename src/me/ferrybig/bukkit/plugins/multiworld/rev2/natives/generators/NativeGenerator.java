package me.ferrybig.bukkit.plugins.multiworld.rev2.natives.generators;

import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.NativeWorld;

public interface NativeGenerator {
    public int getDimension();
            
    public NativeWorld generateWorld(NativeWorldSettings options);
}
