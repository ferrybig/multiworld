package me.ferrybig.bukkit.plugins.multiworld.rev2.natives;

import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.generators.NativeGenerator;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public interface Native {
    public void registerWorldGenerator(NativeGenerator generator);
    
    public Map<String, NativeGenerator> getRegisteredGenerators();
    
    public void createWorld(NativeGenerator generator, UUID uuid, String name, long seed);
    
    public Collection<? extends NativePermissionsHolder> getOps();
    
    public NativeConsoleCommandSender getConsoleCommandSender();
}
