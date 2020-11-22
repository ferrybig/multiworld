package nl.ferrybig.multiworld;

/**
 * Throws when it was unable to parse the value as boolean
 *
 * @author Fernando
 */
public class InvalidFlagValueException extends CommandException {

  private static final long serialVersionUID = 984984267265589118L;

  /**
   * The no argument constructor
   */
  public InvalidFlagValueException() {
    super("Unknown value specified");
  }
}