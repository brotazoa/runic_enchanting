package amorphia.runic_enchanting.items;

import amorphia.runic_enchanting.Runes;
import net.minecraft.item.Item;

public class RuneStoneItem extends Item
{
	protected final Runes rune;

	public RuneStoneItem(Runes rune)
	{
		this(new Settings(), rune);
	}

	public RuneStoneItem(Settings settings, Runes rune)
	{
		super(settings);
		this.rune = rune;
	}

	public Runes getRune()
	{
		return this.rune;
	}
}
