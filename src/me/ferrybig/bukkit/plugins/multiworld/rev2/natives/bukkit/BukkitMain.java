package me.ferrybig.bukkit.plugins.multiworld.rev2.natives.bukkit;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.Native;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.NativeGenerator;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.NativePermissionsHolder;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitMain extends JavaPlugin implements Native {

    @Override
    public void registerWorldGenerator(NativeGenerator generator) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void createWorld(NativeGenerator generator, UUID uuid, String name, long seed) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<String, NativeGenerator> getRegisteredGenerators() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<? extends NativePermissionsHolder> getOps() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
