/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ferrybig.multiworld.worldgen;

import nl.ferrybig.multiworld.InvalidWorldGenOptionsException;
import nl.ferrybig.multiworld.data.InternalWorld;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World.Environment;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

/**
 * @author Fernando
 */
public class PluginGen extends MultiWorldChunkGen {

  private final Environment environment;

  public PluginGen(Environment environment) {
    this.environment = environment;

  }

  @Override
  public void makeWorld(InternalWorld options) throws InvalidWorldGenOptionsException {
    String genId = ""; //NOI18N
    String pluginName = options.getOptions();
    int index = pluginName.indexOf(':'); //NOI18N
    if (index != -1) {
      genId = pluginName.substring(index + 1);
      pluginName = pluginName.substring(0, index);
    }
    if ("".equals(pluginName)) {
      throw new InvalidWorldGenOptionsException("You need to specifi a plugin name");
    }
    Server s = Bukkit.getServer();
    PluginManager pm = s.getPluginManager();
    final Plugin p = pm.getPlugin(pluginName);
    if (p == null) {
      throw new InvalidWorldGenOptionsException("Unknown plugin");
    }
    try {
      final ChunkGenerator generator = p.getDefaultWorldGenerator(options.getName(), genId);
      if (generator == null) {
        throw new InvalidWorldGenOptionsException(
            "Was not able to find the worldgenenerator with id '" + genId + "' at plugin '" + p
                .getDescription().getFullName() + "'");
      }
      options.setWorldType(environment);
      options.setWorldGen(generator);
    } catch (Throwable e) {
      e.printStackTrace(System.err);
      throw (InvalidWorldGenOptionsException) (new InvalidWorldGenOptionsException(
          "Error with custom plugin generator")).initCause(e);
    }
  }
}
