package me.ferrybig.bukkit.plugins.multiworld.rev2.api;

import me.ferrybig.bukkit.plugins.multiworld.rev2.api.command.CommandStack;
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
