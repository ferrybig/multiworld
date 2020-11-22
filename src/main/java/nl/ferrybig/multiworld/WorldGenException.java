/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ferrybig.multiworld;

/**
 * @author Fernando
 */
public class WorldGenException extends MultiWorldException {

  private static final long serialVersionUID = 90537768650427224L;

  /**
   * Creates a new instance of <code>WorldGenException</code> without detail message.
   */
  public WorldGenException() {
  }

  /**
   * Constructs an instance of <code>WorldGenException</code> with the specified detail message.
   *
   * @param msg the detail message.
   */
  public WorldGenException(String msg) {
    super(msg);
  }

  /**
   * Creates a new instance of <code>WorldGenException</code> with cause.
   *
   * @param t The <code>Throwable</code> that caused this exception
   */
  public WorldGenException(Throwable t) {
    super(t);
  }

  /**
   * Constructs an instance of <code>WorldGenException</code> with the specified cause and detail
   * message.
   *
   * @param t   The exception that caused this
   * @param msg the detail message.
   */
  public WorldGenException(Throwable t, String msg) {
    super(msg, t);
  }
}
