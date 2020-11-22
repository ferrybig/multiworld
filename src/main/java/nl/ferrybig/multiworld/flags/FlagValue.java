/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ferrybig.multiworld.flags;

import java.util.Locale;
import nl.ferrybig.multiworld.InvalidFlagValueException;
import nl.ferrybig.multiworld.api.flag.FlagName;

/**
 * @author Fernando
 */
public enum FlagValue {
  UNKNOWN(false), FALSE(false), TRUE(true);
  boolean value;

  FlagValue(boolean value) {
    this.value = value;

  }

  /**
   * Parse an string as an boolean
   *
   * @param str The string to parse
   * @return The boolean that the input string represents
   * @throws InvalidFlagValueException If it was unable to pars the string
   */
  public static FlagValue parseFlagValue(String str) throws InvalidFlagValueException {
    str = str.toLowerCase(Locale.ENGLISH);
    if (str.equals("true") || str.equals("yes") || str.equals("on") || str.equals("allow") || str
        .equals("1")) {
      return TRUE;
    }
    if (str.equals("false") || str.equals("no") || str.equals("off") || str.equals("deny") || str
        .equals("0")) {
      return FALSE;
    }
    throw new InvalidFlagValueException();
  }

  public static FlagValue fromBoolean(boolean input) {
    return input ? FlagValue.TRUE : FlagValue.FALSE;
  }

  public boolean getAsBoolean() {
    return this.value;
  }

  public boolean getAsBoolean(FlagName forFlag) {
    if (this == UNKNOWN) {
      return forFlag.getDefaultState();
    } else {
      return this.getAsBoolean();
    }
  }
}
