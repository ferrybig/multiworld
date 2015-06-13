package me.ferrybig.bukkit.plugins.multiworld.rev2;

import java.util.Collection;
import java.util.UUID;
import me.ferrybig.bukkit.plugins.multiworld.rev2.command.CommandStack;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.Native;

/**
 *
 * @author Fernando
 */


public interface MultiWorldEngine {
    public void saveConfig();
    
    public Native getNativePlugin();
    
    public AddonRegistery getPluginManager();
    
    public boolean sendCommand(CommandStack stack);
    
    public WorldManager getWorlds();
}
