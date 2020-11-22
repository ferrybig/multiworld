/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ferrybig.multiworld;

/**
 * @author Fernando
 */
public class CommandFailedException extends CommandException {

  private static final long serialVersionUID = 1L;

  /**
   * Creates a new instance of
   * <code>CommandFailedException</code> without detail message.
   */
  public CommandFailedException() {
  }

  /**
   * Constructs an instance of
   * <code>CommandFailedException</code> with the specified detail message.
   *
   * @param msg the detail message.
   */
  public CommandFailedException(String msg) {
    super(msg);
  }
}
