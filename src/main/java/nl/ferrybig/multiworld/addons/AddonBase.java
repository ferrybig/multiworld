package nl.ferrybig.multiworld.addons;

import nl.ferrybig.multiworld.data.DataHandler;

public abstract class AddonBase implements MultiworldAddon {

  protected final DataHandler data;
  private boolean isEnabled;

  public AddonBase(DataHandler data) {
    this.data = data;
  }

  @Override
  public void onDisable() {
    this.isEnabled = false;
  }

  @Override
  public void onEnable() {
    this.isEnabled = true;
  }

  @Override
  public boolean isEnabled() {
    return this.isEnabled;
  }
}
