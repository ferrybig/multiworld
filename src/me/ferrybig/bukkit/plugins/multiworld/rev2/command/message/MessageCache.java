/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.command.message;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author ferrybig
 */
public enum MessageCache
{
	WORLD("%world%"),
	PLAYER("%player%"),
	SEED("%seed%"),
	ENVIOMENT("%envioment%"),
	GENERATOR("%generator%"),
	GENERATOR_OPTION("%option%"),
	NUMBER("%percent%"),
	DIFFICULTY("%difficulty%"),
	TARGET("%target%"),
	FLAG("%flag%"),
	FLAG_VALUE("%value%"),
	;
	private final CachingMap cache;
	private final String replacePattern;

	private MessageCache(String replacePattern)
	{
		this.replacePattern = replacePattern;
		this.cache = new CachingMap(10);
	}

	public PackedMessageData get(final String data)
	{
		PackedMessageData tmp = this.cache.get(data);
		if (tmp == null)
		{
			this.cache.put(data, tmp = new PackedMessageData()
			{
				String binary = MessageCache.this.replacePattern + "\n" + data;

				@Override
				public String getBinary()
				{
					return binary;
				}

				@Override
				public String transformMessage(String prevFormat)
				{
					return prevFormat.replace(MessageCache.this.replacePattern, data);
				}
			});
		}
		return tmp;
	}

	private static class CachingMap extends LinkedHashMap<String, PackedMessageData>
	{
		private static final long serialVersionUID = 14436556L;
		private final int cacheSize;

		public CachingMap(int cacheSize)
		{
			this.cacheSize = cacheSize;
		}

		@Override
		protected boolean removeEldestEntry(Map.Entry<String, PackedMessageData> entry)
		{
			return this.size() > cacheSize;
		}

	}

	public static PackedMessageData custom(final String pattern, final String data)
	{
		return new PackedMessageData()
		{
			String binary = pattern + "\n" + data;

			@Override
			public String getBinary()
			{
				return binary;
			}

			@Override
			public String transformMessage(String prevFormat)
			{
				return prevFormat.replace(pattern, data);
			}
		};
	}

}
