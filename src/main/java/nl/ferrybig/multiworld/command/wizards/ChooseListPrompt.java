/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ferrybig.multiworld.command.wizards;

import java.util.Collection;
import java.util.Iterator;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;

/**
 *
 * @author Fernando
 */
public class ChooseListPrompt extends MessagePrompt
{
	private final String text;
	private final Prompt next;

	public ChooseListPrompt(String prefix, Collection<String> text, Prompt next)
	{
		this(prefix, text.iterator(), next);

	}

	protected ChooseListPrompt(String prefix, Iterator<String> text, Prompt next)
	{

		StringBuilder b = new StringBuilder(prefix);
		for (int i = 0; i < 10; i++)
		{
			if (text.hasNext())
			{
				if (i != 0)
				{
					b.append(", ");
				}
				b.append(text.next());
			}

		}
		this.text = b.toString();
		this.next = text.hasNext() ? new ChooseListPrompt(prefix, text, next) : next;

	}

	@Override
	protected Prompt getNextPrompt(ConversationContext paramConversationContext)
	{
		return this.next;
	}

	@Override
	public String getPromptText(ConversationContext paramConversationContext)
	{
		return this.text;
	}
}
