/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ferrybig.multiworld;

/**
 * @author Fernando
 */
public class CommandException extends MultiWorldException {

  private static final long serialVersionUID = 1L;

  /**
   * Creates a new instance of <code>CommandException</code> without detail message.
   */
  public CommandException() {
  }

  /**
   * Constructs an instance of <code>CommandException</code> with the specified detail message.
   *
   * @param msg the detail message.
   */
  public CommandException(String msg) {
    super(msg);
  }

  public CommandException(String msg, Throwable cause) {
    super(msg, cause);
  }

  public CommandException(Throwable cause) {
    super(cause);
  }
}
