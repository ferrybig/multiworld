package me.ferrybig.bukkit.plugins.multiworld.rev2.natives.bukkit;

import org.bukkit.plugin.java.JavaPlugin;

public class BukkitMain extends JavaPlugin {

    private BukkitLoader main;

    @Override
    public void onEnable() {
        if(!this.getDataFolder().exists()) {
            // copy config example
            // generate default config
        }
        main = new BukkitLoader(this);
    }

    @Override
    public void onDisable() {
        main = null;
    }

}
