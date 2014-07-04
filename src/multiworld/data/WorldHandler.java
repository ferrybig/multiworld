package multiworld.data;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Locale;
import multiworld.Utils;
import multiworld.WorldGenException;
import multiworld.command.CommandStack;
import multiworld.command.DebugLevel;
import multiworld.command.MessageType;
import multiworld.translation.Translation;
import multiworld.translation.message.MessageCache;
import multiworld.translation.message.PackedMessageData;
import multiworld.worldgen.WorldGenerator;
import org.bukkit.Bukkit;
import org.bukkit.World;

/**
 *
 * @author ferrybig
 */
public class WorldHandler
{
	private final DataHandler data;
	private final WorldUtils worlds;

	public WorldHandler(DataHandler h)
	{
		this.data = h;
		this.worlds = this.data.getWorldManager();
	}

	public InternalWorld getWorld(String name, boolean mustBeLoaded)
	{
		if (!Utils.checkWorldName(name))
		{
			return null;
		}
		InternalWorld worldObj = this.worlds.getInternalWorld(name, mustBeLoaded);
		if (worldObj == null)
		{
			return null;
		}
		return worldObj;
	}

	public boolean isWorldExisting(String world)
	{
		return this.data.getWorldManager().isWorldExisting(world);
	}

	public boolean isWorldExistingAndSendMessage(String world, CommandStack stack)
	{
		if (!this.isWorldExisting(world))
		{
			stack.sendMessage(MessageType.ERROR,
					  Translation.WORLD_NOT_FOUND,
					  MessageCache.WORLD.get(world));
			return false;
		}
		return true;
	}

	public boolean isWorldLoaded(String world)
	{
		return this.worlds.isWorldLoaded(world);
	}

	public boolean makeWorld(String name, WorldGenerator env, long seed, String options) throws WorldGenException
	{
		data.scheduleSave();
		return this.worlds.makeWorld(name, env, seed, options);
	}

	public boolean deleteWorld(String world)
	{
		data.scheduleSave();
		return this.worlds.deleteWorld(world);
	}

	public boolean unloadWorld(String world)
	{
		data.scheduleSave();
		return this.worlds.unloadWorld(world);
	}

	public World loadWorld(String name, CommandStack debugger)
	{
		WorldLoadCatcher loader = null;
		if (this.data.getNode(DataHandler.OPTIONS_CRAFTBUKKIT_HOOKS) 
                        && debugger.getSender() == Bukkit.getConsoleSender())
		{
			final String magicPrefix = "preparing spawn area for " + name.toLowerCase(Locale.ENGLISH) + ",";
			final String magicSuffix = "%";
			try
			{
				loader = new WorldLoadCatcher(magicPrefix, magicSuffix, name, debugger);
			}
			catch (NoSuchMethodException ex) // TODO: when java 7 comes out, fix this mess
			{
				logCraftBukkitException(ex, debugger);
			}
			catch (InvocationTargetException ex)
			{
				logCraftBukkitException(ex, debugger);
			}
			catch (IllegalArgumentException ex)
			{
				logCraftBukkitException(ex, debugger);
			}
			catch (InstantiationException ex)
			{
				logCraftBukkitException(ex, debugger);
			}
			catch (IllegalAccessException ex)
			{
				logCraftBukkitException(ex, debugger);
			}
			catch (ClassNotFoundException ex)
			{
				logCraftBukkitException(ex, debugger);
			}
		}
		try
		{
			this.data.scheduleSave();
			return this.worlds.loadWorld(name);
		}
		finally
		{
			if (loader != null)
			{
				try
				{
					loader.close();
				}
				catch (NoSuchMethodException ex)
				{
					logCraftBukkitException(ex, debugger);
				}
				catch (IllegalAccessException ex)
				{
					logCraftBukkitException(ex, debugger);
				}
				catch (IllegalArgumentException ex)
				{
					logCraftBukkitException(ex, debugger);
				}
				catch (InvocationTargetException ex)
				{
					logCraftBukkitException(ex, debugger);
				}
			}
		}
	}

	private void logCraftBukkitException(Exception exception, CommandStack debugger)
	{
		if (debugger == null)
		{
			return;
		}
		debugger.sendMessage(null, Translation.CRAFTBUKKIT_HOOKS_FAILED);
		debugger.sendMessageDebug("More information:", DebugLevel.V);
		debugger.sendMessageDebug(exception.toString(), DebugLevel.V);

	}

	private static class WorldLoadCatcher implements AutoCloseable
	{
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
		private static Class<?> proxy;

		public WorldLoadCatcher(String magicPrefix, String magicSuffix, String worldName, final CommandStack output) throws NoSuchMethodException, InvocationTargetException, IllegalArgumentException, InstantiationException, IllegalAccessException, ClassNotFoundException
		{
			this.magicPrefix = magicPrefix;
			this.magicSuffix = magicSuffix;
			this.worldName = worldName;
			InvocationHandler handler = new InvocationHandler()
			{

				boolean started = true;
				Object errorHandler;

				@Override
				public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable
				{
					if (Object.class == method.getDeclaringClass())
					{
						String name = method.getName();
						if ("equals".equals(name))
						{
							return proxy == arguments[0];
						}
						else if ("hashCode".equals(name))
						{
							return System.identityHashCode(proxy);
						}
						else if ("toString".equals(name))
						{
							return proxy.getClass().getName() + "@"
								+ Integer.toHexString(System.identityHashCode(proxy))
								+ ", with InvocationHandler " + this;
						}
						else
						{
							throw new IllegalStateException(String.valueOf(method));
						}
					}
					else
					{
						if (method.getName().equals("append"))
						{
							String messageData;

							final Object logEventInstance = arguments[0];

							messageData = message.getMethod("getFormattedMessage").invoke(logEvent.getMethod("getMessage").invoke(logEventInstance)).toString();
							messageData = messageData.toLowerCase(Locale.ENGLISH);
							output.sendMessageDebug(messageData, DebugLevel.VVVVV);
							if (messageData.startsWith(WorldLoadCatcher.this.magicPrefix) && messageData.endsWith(WorldLoadCatcher.this.magicSuffix))
							{
								messageData = messageData.substring(WorldLoadCatcher.this.magicPrefix.length(), messageData.length() - WorldLoadCatcher.this.magicSuffix.length());
								output.sendMessageBroadcast(
									null,
									Translation.PREPARING_SPAWN_ARENA,
									MessageCache.NUMBER.get(messageData),
									MessageCache.WORLD.get(WorldLoadCatcher.this.worldName),
									PackedMessageData.NO_CONSOLE_MESSAGE);
							}

							return null;
						}
						else if (method.getName().equals("getHandler"))
						{
							output.sendMessageDebug("getHandler called", DebugLevel.VVVVV);
							return this.errorHandler;
						}
						else if (method.getName().equals("setHandler"))
						{
							this.errorHandler = arguments[0];
							output.sendMessageDebug("setHandler called", DebugLevel.VVVVV);
							return null;
						}
						else if (method.getName().equals("getLayout"))
						{
							output.sendMessageDebug("getLayout called", DebugLevel.VVVVV);
							return layoutInstance;
						}
						else if (method.getName().equals("getName"))
						{
							output.sendMessageDebug("getName called", DebugLevel.VVVVV);
							return "Multiworld load message catcher";
						}
						else if (method.getName().equals("ignoreExceptions"))
						{
							output.sendMessageDebug("ignoreExceptions called", DebugLevel.VVVVV);
							return true;
						}
						else if (method.getName().equals("start"))
						{
							output.sendMessageDebug("start called", DebugLevel.VVVVV);
							this.started = true;
							return null;
						}
						else if (method.getName().equals("stop"))
						{
							output.sendMessageDebug("stop called", DebugLevel.VVVVV);
							this.started = false;
							return null;
						}
						else if (method.getName().equals("isStarted"))
						{
							output.sendMessageDebug("isStarted called", DebugLevel.VVVVV);
							return started;
						}
						else
						{
							output.sendMessageDebug("unknown method called: method", DebugLevel.VVVVV);
							throw new IllegalStateException(String.valueOf(method));
						}
					}
				}
			};
			logManager = Class.forName("org.apache.logging.log4j.LogManager", true, Bukkit.getServer().getClass().getClassLoader());
			logger = Class.forName("org.apache.logging.log4j.core.Logger", true, Bukkit.getServer().getClass().getClassLoader());
			appender = Class.forName("org.apache.logging.log4j.core.Appender", true, Bukkit.getServer().getClass().getClassLoader());
			logEvent = Class.forName("org.apache.logging.log4j.core.LogEvent", true, Bukkit.getServer().getClass().getClassLoader());
			layout = Class.forName("org.apache.logging.log4j.core.Layout", true, Bukkit.getServer().getClass().getClassLoader());
			message = Class.forName("org.apache.logging.log4j.message.Message", true, Bukkit.getServer().getClass().getClassLoader());
			layoutInstance = Class.forName("org.apache.logging.log4j.core.layout.PatternLayout", true, Bukkit.getServer().getClass().getClassLoader())
				.getMethod("createLayout",
					   String.class,
					   Class.forName("org.apache.logging.log4j.core.config.Configuration", true, Bukkit.getServer().getClass().getClassLoader()),
					   Class.forName("org.apache.logging.log4j.core.pattern.RegexReplacement", true, Bukkit.getServer().getClass().getClassLoader()),
					   String.class,
					   String.class
				).invoke(null, new Object[5]);
			Class<?> appenderCustom = proxy == null ? proxy = Proxy.getProxyClass(appender.getClassLoader(), appender) : proxy;
			appenderInstance = appenderCustom.getConstructor(InvocationHandler.class).newInstance(handler);
			loggerInstance = logManager.getMethod("getRootLogger").invoke(null);
			logger.getMethod("addAppender", appender).invoke(loggerInstance, appenderInstance);
			appender.getMethod("setHandler", Class.forName("org.apache.logging.log4j.core.ErrorHandler"))
				.invoke(appenderInstance, Class.forName("org.apache.logging.log4j.core.appender.DefaultErrorHandler").getConstructor(appender).newInstance(appenderInstance));
		}

		@Override
		public void close() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
		{
			logger.getMethod("removeAppender", appender).invoke(loggerInstance, appenderInstance);
		}

	}
}
