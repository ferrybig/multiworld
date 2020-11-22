package nl.ferrybig.multiworld.addons;

public interface MultiworldAddon {

  public abstract void onDisable();

  public abstract void onEnable();

  public abstract boolean isEnabled();
}
