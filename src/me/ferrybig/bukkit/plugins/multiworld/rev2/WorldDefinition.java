package me.ferrybig.bukkit.plugins.multiworld.rev2;

import java.util.UUID;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.NativeWorld;

public interface WorldDefinition {

        public NativeWorld getWorld();
        
        public String getName();
        
        public UUID getUUID();
        
        public Map<String,String> getGamerules();
}
