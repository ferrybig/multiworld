/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ferrybig.multiworld.api;

import nl.ferrybig.multiworld.MultiWorldException;

/**
 * @author Fernando
 */
public class ConfigurationSaveException extends MultiWorldException {

  public ConfigurationSaveException() {
  }

  public ConfigurationSaveException(String msg) {
    super(msg);
  }

  public ConfigurationSaveException(Throwable cause) {
    super(cause);
  }

  public ConfigurationSaveException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
