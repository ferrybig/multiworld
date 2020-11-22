/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ferrybig.multiworld.data;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;

/**
 * @author Fernando
 */
public class FileLogger extends FileHandler {

  public FileLogger(File baseDir, MyLogger m) throws IOException {
    super(baseDir.getAbsolutePath() + "/%u", true);
    this.setFormatter(new FileLogFormatter());
    if (m != null) {
      m.finest("Logging to file started.");
    }
  }

  public FileLogger(File baseDir) throws IOException {
    this(baseDir, null);
  }

}
