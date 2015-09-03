package me.ferrybig.bukkit.plugins.multiworld.rev2.impl.bukkit;

import java.util.logging.Level;
import me.ferrybig.bukkit.plugins.multiworld.rev2.api.MultiWorldEngine;
import me.ferrybig.bukkit.plugins.multiworld.rev2.core.MultiWorldCore;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.plugin.NativePlugin;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitMain extends JavaPlugin {
    
    private BukkitLoader main;
    private NativePlugin me;
    private MultiWorldEngine core;
    
    @Override
    public void onEnable() {
        if (!this.getDataFolder().exists()) {
            // copy config example
            // generate default config
        }
        boolean debug = false;
        long starttime = System.currentTimeMillis();
        
        main = new BukkitLoader(this, this.getServer());
        BukkitPluginManager m = new BukkitPluginManager(main);
        main.setPluginManager(m);
        BukkitEntityManager ent = new BukkitEntityManager(this, main);
        main.setEntityManager(ent);
        me = m.getUs();
        
        long midtime = System.currentTimeMillis();
        if(debug) {
            this.getLogger().log(Level.INFO, "Multiworld loaded nativestack functions in {0} ms", (midtime-starttime));
        }
        
        core = null;
        
        
        core.enable();
        
        long endtime = System.currentTimeMillis();
        if(debug) {
            this.getLogger().log(Level.INFO, "Multiworld loaded core in {0} ms", (endtime-midtime));
        }
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
