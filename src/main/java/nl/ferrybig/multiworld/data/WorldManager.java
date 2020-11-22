/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ferrybig.multiworld.data;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import nl.ferrybig.multiworld.Utils;
import nl.ferrybig.multiworld.WorldGenException;
import nl.ferrybig.multiworld.api.MultiWorldWorldData;
import nl.ferrybig.multiworld.api.events.FlagChanceEvent;
import nl.ferrybig.multiworld.api.events.WorldCreateEvent;
import nl.ferrybig.multiworld.api.flag.FlagName;
import nl.ferrybig.multiworld.data.config.ConfigNode;
import nl.ferrybig.multiworld.data.config.DifficultyConfigNode;
import nl.ferrybig.multiworld.flags.FlagMap;
import nl.ferrybig.multiworld.flags.FlagValue;
import nl.ferrybig.multiworld.worldgen.NullGen;
import nl.ferrybig.multiworld.worldgen.WorldGenerator;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.generator.ChunkGenerator;

/**
 * @author Fernando
 */
public class WorldManager implements WorldUtils {

  public final static ConfigNode<Difficulty> WORLD_DIFFICULTY = new DifficultyConfigNode(null,
      "difficulty", Difficulty.NORMAL);
  private final Map<String, WorldContainer> worlds;

  public WorldManager() {
    this.worlds = new HashMap<String, WorldContainer>();
  }

  @Override
  public World getWorld(String name) {
    return this.getWorldMeta(name, true).getBukkitWorld();
  }

  @Override
  public InternalWorld getInternalWorld(String name, boolean mustBeLoaded) {
    if (name == null) {
      throw new IllegalArgumentException("Name may not be null");
    }
    WorldContainer w = this.getWorldMeta(name, mustBeLoaded);
    if (w == null) {
      return null;
    }
    if (!mustBeLoaded) {
      return w.getWorld();
    } else {
      return w.isLoaded() ? w.getWorld() : null;
    }
  }

  @Override
  public boolean isWorldLoaded(String name) {
    WorldContainer world = this.getWorldMeta(name, false);
    if (world == null) {
      return false;
    }
    return world.isLoaded();
  }

  @Override
  public boolean isWorldExisting(String world) {
    WorldContainer w = this.getWorldMeta(world, false);
    return w != null;
  }

  @Override
  public InternalWorld[] getLoadedWorlds() {
    List<World> loadedWorlds = Bukkit.getWorlds();
    int size = loadedWorlds.size();
    InternalWorld[] array = new InternalWorld[size];
    for (int i = 0; i < size; i++) {
      array[i] = getInternalWorld(loadedWorlds.get(i).getName(), true);
    }
    return array;
  }

  @Override
  public WorldContainer[] getWorlds() {
    List<World> loadedWorlds = Bukkit.getWorlds();
    Collection<WorldContainer> registeredWorlds = this.worlds.values();
    Set<WorldContainer> output = new HashSet<WorldContainer>();

    for (World i : loadedWorlds) {
      output.add(this.getWorldMeta(i.getName(), false));
    }
    for (WorldContainer i : registeredWorlds) {
      output.add(i);
    }

    int size = output.size();
    WorldContainer[] array = new WorldContainer[size];
    return output.toArray(array);
  }

  @Override
  public InternalWorld[] getWorlds(boolean listOnlyOnlineWorlds) {
    if (listOnlyOnlineWorlds) {
      return this.getLoadedWorlds();
    }
    List<World> loadedWorlds = Bukkit.getWorlds();
    Collection<WorldContainer> registeredWorlds = this.worlds.values();
    Set<InternalWorld> output = new HashSet<InternalWorld>();

    for (World i : loadedWorlds) {
      output.add(this.getInternalWorld(i.getName(), false));
    }
    for (WorldContainer i : registeredWorlds) {
      output.add(i.getWorld());
    }

    int size = output.size();
    InternalWorld[] array = new InternalWorld[size];
    return output.toArray(array);
  }

  /**
   * Sets an flag on a world
   * <p>
   *
   * @param world the world to set on
   * @param flag  The flag to affect
   * @param value The new value
   */
  @Override
  public void setFlag(String world, FlagName flag, FlagValue value) {
    this.setFlag(world, flag, value, false);
  }

  private void setFlag(String worldName, FlagName flag, FlagValue value, boolean isStartingUp) {
    WorldContainer w = this.getWorldMeta(worldName, false);
    InternalWorld world = w.getWorld();
    world.getFlags().put(flag, value);
    if (w.isLoaded()) {
      if (flag == FlagName.SPAWNMONSTER) {
        world.getWorld().setSpawnFlags(value.getAsBoolean(), world.getWorld().getAllowAnimals());
      } else if (flag == FlagName.SPAWNANIMAL) {
        world.getWorld().setSpawnFlags(world.getWorld().getAllowMonsters(), value.getAsBoolean());
      } else if (flag == FlagName.REMEMBERSPAWN) {
        world.getWorld().setKeepSpawnInMemory(value.getAsBoolean());
      } else if (flag == FlagName.PVP) {
        world.getWorld().setPVP(value.getAsBoolean());
      } else if (flag == FlagName.SAVEON) {
        world.getWorld().setAutoSave(value.getAsBoolean());
      }
    }
    if (!isStartingUp) {
      if (value != FlagValue.UNKNOWN) {
        new FlagChanceEvent(w, flag, value.getAsBoolean(flag)).call();
      }
    }
  }

  /**
   * Gets an flag from the specified world
   * <p>
   *
   * @param worldName
   * @param flag      The flag to return
   * @return The value of the specified flag
   */
  @Override
  public FlagValue getFlag(String worldName, FlagName flag) {
    WorldContainer w = this.getWorldMeta(worldName, false);
    if (w.isLoaded()) {
      InternalWorld world = w.getWorld();
      switch (flag) {
        case SPAWNMONSTER: {
          return FlagValue.fromBoolean(world.getWorld().getAllowMonsters());
        }
        case SPAWNANIMAL: {
          return FlagValue.fromBoolean(world.getWorld().getAllowAnimals());
        }
        case REMEMBERSPAWN: {
          return FlagValue.fromBoolean(world.getWorld().getKeepSpawnInMemory());
        }
        case CREATIVEWORLD: {
          FlagValue flagValue = world.getFlags().get(flag);
          if (flagValue == null) {
            return FlagValue
                .fromBoolean(Bukkit.getDefaultGameMode() == org.bukkit.GameMode.CREATIVE);
          }
          return flagValue;
        }
        case SAVEON: {
          return FlagValue.fromBoolean(world.getWorld().isAutoSave());
        }
        case RECIEVECHAT:
        case SENDCHAT: {
          return world.getFlags().containsKey(flag) ? world.getFlags().get(flag) : FlagValue.TRUE;
        }
        case PVP: {
          return FlagValue.fromBoolean(world.getWorld().getPVP());
        }
        default:
          throw new RuntimeException("Cannot find that flag");
      }
    } else {
      if (w.getWorld().getFlags().containsKey(flag)) {
        return w.getWorld().getFlags().get(flag);
      } else {
        return FlagValue.UNKNOWN;
      }
    }

  }

  @Override
  public boolean makeWorld(String name, WorldGenerator env, long seed, String options)
      throws WorldGenException {
    if (this.getWorldMeta(name, false) != null) {
      return false;
    }
    InternalWorld worldData = new InternalWorld(name, seed, World.Environment.NORMAL, null, options,
        new FlagMap(), env.name(), null, null, Difficulty.NORMAL, WorldType.NORMAL);
    env.makeWorld(worldData);
    if (worldData.getEnv() == null) {
      return false;
    }
    this.createWorld(worldData);
    return true;
  }

  @Override
  public boolean deleteWorld(String world) {
    WorldContainer w = this.getWorldMeta(world, false);
    if (w == null) {
      return false;
    }
    if (w.isLoaded()) {
      return false;
    }
    this.worlds.remove(world.toLowerCase());
    return true;
  }

  @Override
  public boolean unloadWorld(String world) {

    if (Bukkit.unloadWorld(world, true)) {
      this.worlds.get(world.toLowerCase(Locale.ENGLISH)).setLoaded(false);
      return true;
    } else {
      boolean isLoaded = Bukkit.getWorld(world) != null;
      this.worlds.get(world.toLowerCase(Locale.ENGLISH)).setLoaded(isLoaded);
      return !isLoaded;
    }
  }

  /**
   * @param name the value of name
   * @return
   */
  @Override
  public World loadWorld(String name) {
    WorldContainer option = this.worlds.get(name.toLowerCase(Locale.ENGLISH));
    if (option.isLoaded()) {
      return Bukkit.getWorld(name);
    }
    InternalWorld world = option.getWorld();
    WorldCreator creator = WorldCreator.name(world.getName()).type(world.getType())
        .seed(world.getSeed()).environment(world.getEnv());
    if (world.getGen() != null) {
      creator = creator.generator(world.getGen());
    }
    World bukkitWorld = null;
    try {
      bukkitWorld = Bukkit.createWorld(creator);
    } finally {
      option.setLoaded(Bukkit.getWorld(name) != null);
    }
    if (bukkitWorld == null) {
      return null;
    }
    bukkitWorld.setDifficulty(option.getWorld().getDifficulty());
    Iterator<Map.Entry<FlagName, FlagValue>> i = world.getFlags().entrySet().iterator();
    while (i.hasNext()) {
      Map.Entry<FlagName, FlagValue> next = i.next();
      if (next.getValue() == FlagValue.UNKNOWN) {
        continue;
      }
      this.setFlag(name, next.getKey(), next.getValue(), true);
    }
    return bukkitWorld;
  }

  private void createWorld(InternalWorld w) {
    this.addWorld(w, false);
    new WorldCreateEvent(this.getWorldMeta(w.getName(), false)).call();
  }

  @Override
  public WorldContainer getWorldMeta(String world, boolean mustLoad) {
    WorldContainer w = worlds.get(world.toLowerCase(Locale.ENGLISH));
    if (w == null) {
      World tmp = Bukkit.getWorld(world);
      if (tmp == null) {
        return null;
      }
      ChunkGenerator tmp1 = tmp.getGenerator();
      if (tmp1 != null) {
        w = this.addWorld(
            new InternalWorld(tmp.getName(), tmp.getSeed(), tmp.getEnvironment(), NullGen.get(),
                tmp1.getClass().getName(),
                new FlagMap(),
                "NULLGEN", null, null, tmp.getDifficulty(), tmp.getWorldType()), true);
      } else {
        w = this.addWorld(
            new InternalWorld(tmp.getName(), tmp.getSeed(), tmp.getEnvironment(), null, "",
                new FlagMap(),
                tmp.getEnvironment().name(), tmp.getDifficulty()), true);
      }
    }
    return w;
  }

  /**
   * Copy the flags from world 1 to another world.
   * <p>
   *
   * @param fromWorld
   * @param destinationWorld
   * @throws ConfigException
   */
  private void copyFlags(InternalWorld fromWorld, InternalWorld destinationWorld) {
    Iterator<Map.Entry<FlagName, FlagValue>> i = fromWorld.getFlags().entrySet().iterator();
    while (i.hasNext()) {
      Map.Entry<FlagName, FlagValue> next = i.next();
      this.setFlag(destinationWorld.getName(), next.getKey(), next.getValue(), true);
    }
  }

  /**
   * Sets the nether portal from world "fromworld" to be redirected to "toworld"
   * <p>
   *
   * @param fromWorld
   * @param toWorld
   * @return
   */
  @Override
  public boolean setPortal(String fromWorld, String toWorld) {
    if (fromWorld == null) {
      throw new NullPointerException("Param fromWorld may not be null");
    }
    WorldContainer w1 = this.getWorldMeta(fromWorld, false);
    WorldContainer w2;
    if (toWorld == null) {
      w2 = null;
    } else {
      w2 = this.getWorldMeta(toWorld, false);
    }
    if (w1 == null) {
      return false;
    }
    if (w2 == null) {
      w1.getWorld().setPortalLink(null);
    } else {
      w1.getWorld().setPortalLink(w2.getWorld().getName());
    }
    return true;
  }

  /**
   * Sets the end portal from world "fromworld" to be redirected to "toworld"
   * <p>
   *
   * @param fromWorld
   * @param toWorld
   * @return
   */
  @Override
  public boolean setEndPortal(String fromWorld, String toWorld) {
    if (fromWorld == null) {
      throw new NullPointerException("Param fromWorld may not be null");
    }
    WorldContainer w1 = this.getWorldMeta(fromWorld, false);
    WorldContainer w2;
    if (toWorld == null) {
      w2 = null;
    } else {
      w2 = this.getWorldMeta(toWorld, false);
    }
    if (w1 == null) {
      return false;
    }
    if (w2 == null) {
      w1.getWorld().setEndLink("");
    } else {
      w1.getWorld().setEndLink(w2.getWorld().getName());
    }
    return true;
  }

  private WorldContainer addWorld(InternalWorld w, boolean isLoaded) {
    WorldContainer world = new WorldContainer(w, isLoaded);
    this.worlds.put(w.getName().toLowerCase(), world);
    return world;
  }

  @Override
  public MultiWorldWorldData[] getAllWorlds() {

    return getWorlds();
  }

  @Override
  public void saveWorlds(ConfigurationSection worldSection, MyLogger log, SpawnWorldControl spawn) {
    ConfigurationSection l2;
    ConfigurationSection l3;
    for (WorldContainer i : new TreeSet<WorldContainer>(new Comparator<WorldContainer>() {
      @Override
      public int compare(WorldContainer t, WorldContainer t1) {
        return t.getName()
            .compareTo(t1.getName()); // This makes the worlds be saved in the same order every time
      }
    }) {
      private static final long serialVersionUID = 1L;


      {
        addAll(Arrays.asList(getWorlds()));
      }
    }) {
      InternalWorld w = i.getWorld();
      if (!Utils.checkWorldName(w.getName())) {
        log.warning("Was not able to save world named: " + w.getName());
        continue;
      }
      if (WorldGenerator.NULLGEN.getName().equals(w.getFullGeneratorName())) {
        continue;
      }
      l2 = worldSection.createSection(w.getName());
      l2.set("seed", w.getSeed());
      l2.set("worldgen", w.getFullGeneratorName());
      l2.set("options", w.getOptions());
      WORLD_DIFFICULTY.set(l2, w.getDifficulty());
      l2.set("autoload", i.isLoaded());
      if (!w.getFlags().isEmpty()) {
        l3 = l2.createSection("flags");
        for (Map.Entry<FlagName, FlagValue> i1 : w.getFlags().entrySet()) {

          FlagValue get = this.getFlag(w.getName(), i1.getKey());
          if (get != FlagValue.UNKNOWN) {
            l3.set(i1.getKey().name(), (get == FlagValue.TRUE));
          }
        }
      }
      if (!w.getPortalWorld().isEmpty()) {
        l2.set("netherportal", w.getPortalWorld());
      } else {
        l2.set("netherportal", null);
      }
      if (!w.getEndPortalWorld().isEmpty()) {
        l2.set("endportal", w.getEndPortalWorld());
      } else {
        l2.set("endportal", null);
      }
      if (spawn != null) {
        l2.set("spawnGroup", spawn.getGroupByWorld(w.getName()));
      }
    }
  }

  @Override
  public void loadWorlds(ConfigurationSection worldList, MyLogger logger, Difficulty baseDifficulty,
      SpawnWorldControl spawn) {
    Iterator<String> list = worldList.getValues(false).keySet().iterator();
    while (list.hasNext()) {
      String worldName = list.next();
      if (!worldList.isConfigurationSection(worldName)) {
        logger.warning(worldName + " = not a valid world, sorry");
        continue;
      }
      try {
        ConfigurationSection world = worldList.getConfigurationSection(worldName);

        /* Get the seed */
        long seed = world.getLong("seed", 0L);

        /* Get the world gen */
        WorldGenerator gen = WorldGenerator
            .valueOf(world.getString("worldgen", "NORMAL").toUpperCase());

        /* Get the options to pass to world gen */
        String options = world.getString("options", "");

        /* Get the flags set on the world */
        FlagMap flags = new FlagMap();
        ConfigurationSection flagList = world.getConfigurationSection("flags");
        if (flagList != null) {
          FlagName[] flagNames = FlagName.class.getEnumConstants();
          for (FlagName flagName : flagNames) {
            if (!flagList.isBoolean(flagName.name())) {
              continue;
            }
            flags.put(flagName, FlagValue.fromBoolean(flagList.getBoolean(flagName.name())));
          }
        }
        String portal = world.getString("netherportal", "");
        String endPortal = world.getString("endportal", "");

        /* Lets configure the internal world object */
        InternalWorld worldData = new InternalWorld();
        worldData.setWorldName(worldName);
        worldData.setWorldSeed(seed);
        worldData.setFullGeneratorName(gen.getName());
        worldData.setPortalLink(portal);
        worldData.setEndLink(endPortal);
        worldData.setFlags(flags);
        worldData.setOptions(options);
        {
          Difficulty diff = WORLD_DIFFICULTY.get(world);
          worldData.setDifficulty(diff);
        }

        /* Passes the object to the world gens for further chances, like other world types */
        gen.makeWorld(worldData);
        if (worldData.getEnv() == null) {
          continue;
        }

        /* Loads the world at the mem of server */
        this.createWorld(worldData);
        if (world.getBoolean("autoload", true)) {
          this.loadWorld(worldData.getName()).setDifficulty(worldData.getDifficulty());
        }
        if (spawn != null) {
          String spawnGroup = world.getString("spawnGroup", "defaultGroup");
          if (!spawn.registerWorldSpawn(worldName, spawnGroup)) {
            world.set("spawnGroup", "defaultGroup");
            spawn.registerWorldSpawn(worldName, "defaultGroup");
          }
        }
      } catch (IllegalArgumentException err) {
        worldList.set(worldName, null);
        logger.warning("Invalid world: " + worldName);
      } catch (WorldGenException err) {
        worldList.set(worldName, null);
        logger.warning(
            "Invalid world gen used for world '" + worldName + "': " + err.getLocalizedMessage());
      } catch (Exception err) {
        logger.throwing(this.getClass().getName(), "load", err,
            "Some error with '" + worldName + "': " + err.getLocalizedMessage());
      }
    }
  }
}
