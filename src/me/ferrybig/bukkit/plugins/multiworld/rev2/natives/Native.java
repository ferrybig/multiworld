package me.ferrybig.bukkit.plugins.multiworld.rev2.natives;

import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.generators.NativeGenerator;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.entities.NativePlayer;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.materials.NativeMaterials;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.NativeBiomes;

public interface Native {
    public void registerWorldGenerator(NativeGenerator generator);
    
    public Map<String, NativeGenerator> getRegisteredGenerators();
    
    public void createWorld(NativeGenerator generator, UUID uuid, String name, long seed);
    
    public Collection<? extends NativePermissionsHolder> getOps();
    
    public NativeConsoleCommandSender getConsoleCommandSender();
    
    public NativePluginManager getPluginManager();
    
    public NativePlayer getPlayer(String name);
    
    public NativePlayer getPlayer(UUID uuid);
    
    public Object getUnderlying();
    
    public NativeMaterials getMaterials();
    
    public NativeBiomes getBiomes();
}
