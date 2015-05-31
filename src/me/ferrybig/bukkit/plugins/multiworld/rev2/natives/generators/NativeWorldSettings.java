/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.natives.generators;

import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.NativeGamemode;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author Fernando
 */
public interface NativeWorldSettings {
    long getSeed();
    String getName();
    boolean spawnMonsters();
    boolean spawnAnimals();
    Map<String,String> gameRules();
    int difficulty();
    NativeGamemode forcedGamemode();
    Map<UUID, NativeGamemode> gamemodeOverrides();
}
