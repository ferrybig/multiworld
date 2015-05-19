package me.ferrybig.bukkit.plugins.multiworld.rev2;

import java.util.Collection;
import java.util.UUID;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.Native;

/**
 *
 * @author Fernando
 */


public interface MultiWorldEngine {
    public void saveConfig();
    
    public Native getNativePlugin();
    
    public WorldDefinition getWorld(UUID uuid);
    
    public void registerNewGenerator(WorldGenerator gen);
    
    public Collection<? extends WorldDefinition> getWorlds();
    
    public AddonRegistery getPluginManager();
}
