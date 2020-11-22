/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ferrybig.multiworld.data;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

/**
 * @author Fernando
 */
public class SpawnWorldControl {

  private final DataHandler data;
  private Map<String, String> groupToWorldSpawn = new HashMap<String, String>();
  private Map<String, String> worldsToGroupSpawn = new HashMap<String, String>();

  public SpawnWorldControl(ConfigurationSection spawnGroups, DataHandler data) {
    groupToWorldSpawn.put("defaultGroup", Bukkit.getWorlds().get(0).getName());
    //System.out.print("Loading spawn groupes");
    if (spawnGroups != null) {
      for (String name : spawnGroups.getKeys(false)) {
        //System.out.print(" - Found "+name);
        String spawnWorld = spawnGroups.getString(name + ".spawn");
        if (spawnWorld == null) {
          continue;
        }
        //System.out.print(" - Registered "+name + " to "+ spawnWorld);
        groupToWorldSpawn.put(name, spawnWorld);
      }
    }
    this.data = data;
  }

  public World resolveWorld(String worldFrom) {
    //System.out.print("Resolving "+worldFrom);
    String spawnGroup = worldsToGroupSpawn.get(worldFrom.toUpperCase());
    if (spawnGroup == null) {
      spawnGroup = "defaultGroup";
      registerWorldSpawn(worldFrom, "defaultGroup");
    }
    //System.out.print(" - SpawnGroup:"+spawnGroup);
    String targetWorld = groupToWorldSpawn.get(spawnGroup);
    if (targetWorld == null) {
      groupToWorldSpawn.put(spawnGroup, Bukkit.getWorlds().get(0).getName());
      targetWorld = groupToWorldSpawn.get(spawnGroup);
      this.data.getLogger()
          .warning("Config error, invalid spawnGroup defined for world " + worldFrom);
    }
    //System.out.print(" - targetWorld:"+targetWorld);
    return Bukkit.getWorld(targetWorld);

  }

  public boolean registerWorldSpawn(String worldName, String spawnGroup) {
    //System.out.print("Registring world "+worldName);
    if (groupToWorldSpawn.containsKey(spawnGroup)) {
      //System.out.print(" - Registered "+worldName + " to "+ spawnGroup);
      worldsToGroupSpawn.put(worldName.toUpperCase(), spawnGroup);
      return true;
    }

    return false;
  }

  public String getGroupByWorld(String name) {
    String spawnGroup = worldsToGroupSpawn.get(name.toUpperCase());
    if (spawnGroup == null) {
      spawnGroup = "defaultGroup";
      registerWorldSpawn(name, "defaultGroup");
    }
    return spawnGroup;
  }

  public void save(ConfigurationSection to) {
    for (String key : groupToWorldSpawn.keySet()) {
      to.set(key + ".spawn", groupToWorldSpawn.get(key));
    }
  }
}
