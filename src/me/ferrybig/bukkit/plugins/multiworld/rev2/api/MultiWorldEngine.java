package me.ferrybig.bukkit.plugins.multiworld.rev2.api;

import me.ferrybig.bukkit.plugins.multiworld.rev2.api.command.CommandStack;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.Native;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.plugin.NativePlugin;

/**
 *
 * @author Fernando
 */


public interface MultiWorldEngine {
    public void saveConfig();
    
    public Native getNativeStack();
    
    public NativePlugin getNativePlugin();
    
    public AddonRegistery getPluginManager();
    
    public CommandManager getCommandManager();
    
    public WorldManager getWorlds();

    /**
     * This method stops multiworld
     */
    public void close();
}
