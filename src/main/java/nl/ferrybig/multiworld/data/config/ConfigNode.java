package nl.ferrybig.multiworld.data.config;

import nl.ferrybig.multiworld.MultiWorldPlugin;
import nl.ferrybig.multiworld.data.DataHandler;
import nl.ferrybig.multiworld.data.MyLogger;
import org.bukkit.configuration.ConfigurationSection;

/**
 * @param <T> The type objects this config gives you
 * @author ferrybig
 */
public abstract class ConfigNode<T> {

  protected final String configPath;
  protected final T defaultValue;
  protected final ConfigNode<ConfigurationSection> parent;

  protected ConfigNode(ConfigNode<ConfigurationSection> parent, String configPath, T defaultValue) {
    if (configPath.isEmpty()) {
      throw new IllegalArgumentException("null configPath");
    }
    this.parent = parent;
    this.configPath = configPath;
    this.defaultValue = defaultValue;
  }

  protected String getFullPath() {
    return (parent == null ? "" : (parent.getFullPath() + ".")) + this.configPath;
  }

  protected void set1(ConfigurationSection to, Object value) {
    to.set(this.configPath, value);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    @SuppressWarnings(value = "unchecked") final DefaultConfigNode<?> other = (DefaultConfigNode<?>) obj;
    if ((this.configPath == null) ? (other.configPath != null)
        : !this.configPath.equals(other.configPath)) {
      return false;
    }
    if (this.defaultValue != other.defaultValue && (this.defaultValue == null || !this.defaultValue
        .equals(other.defaultValue))) {
      return false;
    }
    return true;
  }

  public T get(ConfigurationSection from) {
    MyLogger logger = null;
    DataHandler d = MultiWorldPlugin.getInstance().getDataManager();
    if (d != null) {
      logger = d.getLogger();
    }
    if (this.parent != null) {
      from = this.parent.get(from);
    }
    if (!from.contains(configPath)) {
      if (logger != null) {
        logger.fine("Adding missing config node: " + this.getFullPath());
      }
      this.set1(from, pack(defaultValue));
      return defaultValue;
    } else {
      Object get = from.get(configPath, this.pack(defaultValue));

      try {
        return this.unpack(get);
      } catch (DataPackException error) {
        if (logger != null) {
          logger.warning("Error with node \"" + this.getFullPath()
              + "\" plz fix it, it has been replaced by the default value, cause was: " + error
              .getMessage());
        }
        this.set1(from, this.pack(defaultValue));
        return defaultValue;
      }
    }

  }

  /**
   * @return the configName
   */
  public String getConfigPath() {
    return configPath;
  }

  /**
   * @return the defaultValue
   */
  public T getDefaultValue() {
    return defaultValue;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 59 * hash + (this.configPath != null ? this.configPath.hashCode() : 0);
    hash = 59 * hash + (this.defaultValue != null ? this.defaultValue.hashCode() : 0);
    return hash;
  }

  public void set(ConfigurationSection to, T value) {
    if (this.parent != null) {
      to = this.parent.get(to);
    }
    this.set1(to, this.pack(value));
  }

  @Override
  public String toString() {
    return this.getClass().getName() + "{" + "configPath=" + configPath + ", defaultValue="
        + defaultValue + '}';
  }

  protected abstract T unpack(Object rawConfigValue) throws DataPackException;

  protected abstract Object pack(T data);

}
