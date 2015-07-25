/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world;

import java.util.Map;
import java.util.UUID;

/**
 *
 * @author Fernando
 */
public interface NativeWorld {

    public long getSeed();
    
    public UUID getID();

    public String getName();

    public int getDimension();
    
    public Environment getEnvironment();

    public Object getNative();
    
    public Map<String,String> getGamerules();

    public String getGameRuleValue(String gamerule);

    public String setGameRuleValue(String gamerule, String value);

    public NativeBlock getBlockAt(int x, int y, int z);

    public int getSeaLevel();

    public int getMaxHeight();
    
    public enum Environment {
        UNKNOWN, NORMAL, NETHER, END
    }
}