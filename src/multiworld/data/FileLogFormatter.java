/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.data;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 *
 * @author Fernando
 */
public class FileLogFormatter extends Formatter
{
	private final SimpleDateFormat date;
public FileLogFormatter()
  {
    this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  }
@Override
  public String format(LogRecord record)
  {
    StringBuilder b = new StringBuilder();
    Throwable ex = record.getThrown();

    b.append(this.date.format(Long.valueOf(record.getMillis())));
    b.append(" [");
    b.append(record.getLevel().getLocalizedName().toUpperCase());
    b.append("] ");
    b.append(record.getMessage());
    b.append('\n');

    if (ex != null) {
      StringWriter writer = new StringWriter();
      ex.printStackTrace(new PrintWriter(writer));
      b.append(writer);
    }

    return b.toString();
  }@Override
  public String getHead(Handler h)
  {
	  return "Logging started";
  }
  @Override
  public String getTail(Handler h)
  {
	  return "Logging ended";
  }
}
