package me.ferrybig.bukkit.plugins.multiworld.rev2.impl.bukkit;

import me.ferrybig.bukkit.plugins.multiworld.rev2.api.MultiWorldEngine;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.plugin.NativePlugin;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitMain extends JavaPlugin {

    private BukkitLoader main;
    private NativePlugin me;
    private MultiWorldEngine core;

    @Override
    public void onEnable() {
        if(!this.getDataFolder().exists()) {
            // copy config example
            // generate default config
        }
        main = new BukkitLoader(this, this.getServer());
        BukkitPluginManager m = new BukkitPluginManager(main);
        main.setPluginManager(m);
        me = m.getUs();
        main = null/* ... */;
    }

    @Override
    public void onDisable() {
        core.close();
        main.close();
        core = null;
        me = null;
        main = null;
        
    }

    public MultiWorldEngine getMultiworld() {
        return core;
    }
}
