/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.natives.generators;

import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.entities.NativeGamemode;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author Fernando
 */
public interface NativeWorldSettings {
    long getSeed();
    void setSeed(long seed);
    boolean spawnMonsters();
    void spawnMonsters(boolean monsters);
    boolean spawnAnimals();
    void spawnAnimals(boolean animals);
    Map<String,String> gamerules();
    void gamerules(Map<String,String> rules);
    int difficulty();
    void difficulty(int difficulty);
    NativeGamemode forcedGamemode();
    void forcedGamemode(NativeGamemode game);
}
