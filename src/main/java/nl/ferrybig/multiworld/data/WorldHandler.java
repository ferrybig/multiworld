package nl.ferrybig.multiworld.data;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Locale;
import nl.ferrybig.multiworld.Utils;
import nl.ferrybig.multiworld.WorldGenException;
import nl.ferrybig.multiworld.command.CommandStack;
import nl.ferrybig.multiworld.command.DebugLevel;
import nl.ferrybig.multiworld.command.MessageType;
import nl.ferrybig.multiworld.translation.Translation;
import nl.ferrybig.multiworld.translation.message.MessageCache;
import nl.ferrybig.multiworld.translation.message.PackedMessageData;
import nl.ferrybig.multiworld.worldgen.WorldGenerator;
import org.bukkit.Bukkit;
import org.bukkit.World;

/**
 * @author ferrybig
 */
public class WorldHandler {

  private final DataHandler data;
  private final WorldUtils worlds;

  public WorldHandler(DataHandler h) {
    this.data = h;
    this.worlds = this.data.getWorldManager();
  }

  public InternalWorld getWorld(String name, boolean mustBeLoaded) {
    if (!Utils.checkWorldName(name)) {
      return null;
    }
    InternalWorld worldObj = this.worlds.getInternalWorld(name, mustBeLoaded);
    if (worldObj == null) {
      return null;
    }
    return worldObj;
  }

  public boolean isWorldExisting(String world) {
    return this.data.getWorldManager().isWorldExisting(world);
  }

  public boolean isWorldExistingAndSendMessage(String world, CommandStack stack) {
    if (!this.isWorldExisting(world)) {
      stack.sendMessage(MessageType.ERROR,
          Translation.WORLD_NOT_FOUND,
          MessageCache.WORLD.get(world));
      return false;
    }
    return true;
  }

  public boolean isWorldLoaded(String world) {
    return this.worlds.isWorldLoaded(world);
  }

  public boolean makeWorld(String name, WorldGenerator env, long seed, String options)
      throws WorldGenException {
    data.scheduleSave();
    return this.worlds.makeWorld(name, env, seed, options);
  }

  public boolean deleteWorld(String world) {
    data.scheduleSave();
    return this.worlds.deleteWorld(world);
  }

  public boolean unloadWorld(String world) {
    data.scheduleSave();
    return this.worlds.unloadWorld(world);
  }

  public World loadWorld(String name, CommandStack debugger) {
    WorldLoadCatcher loader = null;
    if (this.data.getNode(DataHandler.OPTIONS_CRAFTBUKKIT_HOOKS)
        && debugger.getSender() != Bukkit.getConsoleSender()) {
      final String magicPrefix =
          "preparing spawn area for " + name.toLowerCase(Locale.ENGLISH) + ",";
      final String magicSuffix = "%";
      try {
        loader = new WorldLoadCatcher(magicPrefix, magicSuffix, name, debugger);
      } catch (NoSuchMethodException | InvocationTargetException |
          IllegalArgumentException | InstantiationException |
          IllegalAccessException | ClassNotFoundException ex) {
        logCraftBukkitException(ex, debugger);
      }
    }
    try {
      this.data.scheduleSave();
      return this.worlds.loadWorld(name);
    } finally {
      if (loader != null) {
        try {
          loader.close();
        } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
          logCraftBukkitException(ex, debugger);
        }
      }
    }
  }

  private void logCraftBukkitException(Exception exception, CommandStack debugger) {
    if (debugger == null) {
      return;
    }
    debugger.sendMessage(null, Translation.CRAFTBUKKIT_HOOKS_FAILED);
    debugger.sendMessageDebug("More information:", DebugLevel.V);
    debugger.sendMessageDebug(exception.toString(), DebugLevel.V);

  }

  private static class WorldLoadCatcher implements AutoCloseable {

    private static Class<?> proxy;
    private final String magicPrefix;
    private final String magicSuffix;
    private final String worldName;
    private final Class<?> logManager;
    private final Class<?> logger;
    private final Class<?> appender;
    private final Class<?> logEvent;
    private final Class<?> message;
    private final Class<?> layout;
    private final Object layoutInstance;
    private final Object appenderInstance;
    private final Object loggerInstance;

    public WorldLoadCatcher(String magicPrefix, String magicSuffix, String worldName,
        final CommandStack output)
        throws NoSuchMethodException, InvocationTargetException, IllegalArgumentException, InstantiationException, IllegalAccessException, ClassNotFoundException {
      this.magicPrefix = magicPrefix;
      this.magicSuffix = magicSuffix;
      this.worldName = worldName;
      InvocationHandler handler = new InvocationHandler() {

        boolean started = true;
        Object errorHandler;

        @Override
        public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
          if (Object.class == method.getDeclaringClass()) {
            String name = method.getName();
            switch (name) {
              case "equals":
                return proxy == arguments[0];
              case "hashCode":
                return System.identityHashCode(proxy);
              case "toString":
                return proxy.getClass().getName() + "@"
                    + Integer.toHexString(System.identityHashCode(proxy))
                    + ", with InvocationHandler " + this;
              default:
                throw new IllegalStateException(String.valueOf(method));
            }
          } else {
            switch (method.getName()) {
              case "append":
                String messageData;

                final Object logEventInstance = arguments[0];

                messageData = message.getMethod("getFormattedMessage")
                    .invoke(logEvent.getMethod("getMessage").invoke(logEventInstance)).toString();
                messageData = messageData.toLowerCase(Locale.ENGLISH);
                output.sendMessageDebug(messageData, DebugLevel.VVVVV);
                if (messageData.startsWith(WorldLoadCatcher.this.magicPrefix) && messageData
                    .endsWith(WorldLoadCatcher.this.magicSuffix)) {
                  messageData = messageData.substring(WorldLoadCatcher.this.magicPrefix.length(),
                      messageData.length() - WorldLoadCatcher.this.magicSuffix.length());
                  output.sendMessageBroadcast(
                      null,
                      Translation.PREPARING_SPAWN_ARENA,
                      MessageCache.NUMBER.get(messageData),
                      MessageCache.WORLD.get(WorldLoadCatcher.this.worldName),
                      PackedMessageData.NO_CONSOLE_MESSAGE);
                }

                return null;
              case "getHandler":
                output.sendMessageDebug("getHandler called", DebugLevel.VVVVV);
                return this.errorHandler;
              case "setHandler":
                this.errorHandler = arguments[0];
                output.sendMessageDebug("setHandler called", DebugLevel.VVVVV);
                return null;
              case "getLayout":
                output.sendMessageDebug("getLayout called", DebugLevel.VVVVV);
                return layoutInstance;
              case "getName":
                output.sendMessageDebug("getName called", DebugLevel.VVVVV);
                return "Multiworld load message catcher";
              case "ignoreExceptions":
                output.sendMessageDebug("ignoreExceptions called", DebugLevel.VVVVV);
                return true;
              case "start":
                output.sendMessageDebug("start called", DebugLevel.VVVVV);
                this.started = true;
                return null;
              case "stop":
                output.sendMessageDebug("stop called", DebugLevel.VVVVV);
                this.started = false;
                return null;
              case "isStarted":
                output.sendMessageDebug("isStarted called", DebugLevel.VVVVV);
                return started;
              default:
                output.sendMessageDebug("unknown method called: method", DebugLevel.VVVVV);
                throw new AssertionError(String.valueOf(method));
            }
          }
        }
      };
      final ClassLoader classLoader = Bukkit.getServer().getClass().getClassLoader();
      logManager = Class.forName("org.apache.logging.log4j.LogManager", true, classLoader);
      logger = Class.forName("org.apache.logging.log4j.core.Logger", true, classLoader);
      appender = Class.forName("org.apache.logging.log4j.core.Appender", true, classLoader);
      logEvent = Class.forName("org.apache.logging.log4j.core.LogEvent", true, classLoader);
      layout = Class.forName("org.apache.logging.log4j.core.Layout", true, classLoader);
      message = Class.forName("org.apache.logging.log4j.message.Message", true, classLoader);
      layoutInstance = Class
          .forName("org.apache.logging.log4j.core.layout.PatternLayout", true, classLoader)
          .getMethod("createDefaultLayout").invoke(null, new Object[0]);
      Class<?> appenderCustom =
          proxy == null ? proxy = Proxy.getProxyClass(appender.getClassLoader(), appender) : proxy;
      appenderInstance = appenderCustom.getConstructor(InvocationHandler.class)
          .newInstance(handler);
      loggerInstance = logManager.getMethod("getRootLogger").invoke(null);
      logger.getMethod("addAppender", appender).invoke(loggerInstance, appenderInstance);
      appender.getMethod("setHandler", Class.forName("org.apache.logging.log4j.core.ErrorHandler"))
          .invoke(appenderInstance,
              Class.forName("org.apache.logging.log4j.core.appender.DefaultErrorHandler")
                  .getConstructor(appender).newInstance(appenderInstance));
    }

    @Override
    public void close()
        throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      logger.getMethod("removeAppender", appender).invoke(loggerInstance, appenderInstance);
    }

  }
}
