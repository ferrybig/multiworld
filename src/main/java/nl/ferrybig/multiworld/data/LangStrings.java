/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ferrybig.multiworld.data;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import org.bukkit.plugin.Plugin;

/**
 * @author Fernando
 * @deprecated Will be removed in the future update to make place for a better translation system
 */
@Deprecated
public class LangStrings {

  private final Locale currentLocale;
  private final ResourceBundle messages;
  private final MessageFormat formatter = new MessageFormat("");

  public LangStrings(String language, String country, String variant, final Plugin main) {
    this.currentLocale = new Locale(language, country, variant);
    this.formatter.setLocale(this.currentLocale);
    this.messages = ResourceBundle
        .getBundle("lang", currentLocale, this.getClass().getClassLoader(), new Controller(main));
  }

  public LangStrings(Plugin main) {
    this("en", "US", "", main);
  }

  public String getString(String input) {
    if (this.messages.containsKey(input)) {
      return this.messages.getString(input);
    } else if (this.messages.containsKey(input + ".1")) {
      StringBuilder build = new StringBuilder();
      int i = 1;
      while (this.messages.containsKey(input + "." + i)) {
        build.append(this.messages.getString(input + "." + i)).append("\n");
        i++;
      }
      return build.toString();
    }
    return "Missing key: " + input;

  }

  public String getString(String input, Object[] data) {
    if (data.length == 0) {
      return this.getString(input);
    }
    this.formatter.applyPattern(this.getString(input));
    return formatter.format(data);
  }

  public Locale getLocale() {
    return this.currentLocale;
  }

  private static class Controller extends ResourceBundle.Control {

    private final Plugin main;

    Controller(Plugin m) {
      this.main = m;
    }

    @Override
    public List<String> getFormats(String baseName) {
      if (baseName == null) {
        throw new NullPointerException();
      }
      return Arrays.asList("properties");
    }

    @Override
    public ResourceBundle newBundle(String baseName,
        Locale locale,
        String format,
        ClassLoader loader,
        boolean reload)
        throws IllegalAccessException,
        InstantiationException,
        IOException {
      ResourceBundle bundle = null;
      if (format.equals("properties")) {
        String bundleName = toBundleName(baseName, locale);
        String resourceName = toResourceName(bundleName, format);
        InputStream stream;
        stream = main.getResource(resourceName);
        if (stream != null) {
          BufferedInputStream bis = new BufferedInputStream(stream);
          bundle = new PropertyResourceBundle(bis);
          bis.close();
        }
      }
      return bundle;
    }
  }
}
