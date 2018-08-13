package multiworld.command;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

public class ArgumentType
{
	private static final WeakHashMap<String, WeakReference<ArgumentType>> map = new WeakHashMap<String, WeakReference<ArgumentType>>();
	public static final ArgumentType FLAG_VALUE = valueOf("<Flag Value>");
	public static final ArgumentType NEW_WORLD_NAME = valueOf("<New World Name>");
	public static final ArgumentType TARGET_PLAYER = valueOf("<Target Player>");
	public static final ArgumentType TARGET_WORLD = valueOf("<Target World>");

	public static ArgumentType valueOf(String name)
	{
		assert map != null; // If map is null, somebody have moved the map below the argument types definition
		WeakReference<ArgumentType> m = map.get(name);
		ArgumentType s = null;
		if(m != null)
		{
			s = m.get();
		}
		if(s == null)
		{
			s = new ArgumentType(name);
			m = new WeakReference<ArgumentType>(s);
			map.put(name, m);
		}
		return s;
	}

	private final MessageProvider messages;

	private ArgumentType(MessageProvider messages)
	{
		this.messages = messages;
	}

	private ArgumentType(final String message)
	{
		this.messages = new MessageProvider()
		{

			@Override
			public String getMessage()
			{
				return message;
			}
		};
	}

	public String getMessage()
	{
		return this.messages.getMessage();
	}

	private interface MessageProvider
	{
		public String getMessage();
	}
}
