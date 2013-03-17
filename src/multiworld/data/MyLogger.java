package multiworld.data;

import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * The class used for logging
 * @author Fernando
 */
public class MyLogger extends Object {

	protected static final Logger log = Logger.getLogger("Minecraft.multiworld");
	protected final String name;
	protected final boolean debug;

	public MyLogger(boolean debug, String name) {
		this.debug = debug;
		this.name = new StringBuilder(name.length() + 3).append("[").append(name).append("] ").toString();
	}

	public MyLogger(String name) {
		this(false, name);
	}

	public boolean getDebug() {
		return this.debug;
	}

	private void logException(Throwable e) {
		CharArrayWriter buffer = new CharArrayWriter();
		PrintWriter tmp = new PrintWriter(buffer);
		e.printStackTrace(tmp);
		tmp.close();
		for (String line : buffer.toString().split(Pattern.quote("\n")))
		{
			this.severe(line);
		}
	}

	public void throwing(String sourceClass, String sourceMethod, Throwable thrown) {
		this.severe("ERROR HAPPEND:");
		this.logException(thrown);
	}

	public void throwing(String sourceClass, String sourceMethod, Throwable thrown, String error) {
		this.severe("ERROR HAPPEND: ".concat(error));
		this.logException(thrown);
	}

	public void severe(String msg) {
		MyLogger.log.severe(this.name.concat(msg));
	}

	public void warning(String msg) {
		MyLogger.log.warning(this.name.concat(msg));
	}

	public void info(String msg) {
		MyLogger.log.info(this.name.concat(msg));
	}

	public void config(String msg) {
		if (!debug)
		{
			return;
		}
		MyLogger.log.info(this.name.concat(msg));
	}

	public void fine(String msg) {
		if (!debug)
		{
			return;
		}
		MyLogger.log.info(this.name.concat(msg));
	}

	public void finer(String msg) {
		if (!debug)
		{
			return;
		}
		MyLogger.log.info(this.name.concat(msg));
	}

	public void finest(String msg) {
		if (!debug)
		{
			return;
		}
		MyLogger.log.info(this.name.concat(msg));
	}
	/**
	 *
	 */
}