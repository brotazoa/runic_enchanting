package amorphia.runic_enchanting.items;

import amorphia.runic_enchanting.Runes;
import com.google.common.collect.Lists;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class RunePageItem extends Item
{
	public static final List<RunePageItem> ITEMS = Lists.newArrayList();

	protected final Runes rune;

	public RunePageItem(Runes rune)
	{
		this(new Settings(), rune);
	}

	public RunePageItem(Settings settings, Runes rune)
	{
		super(settings);
		this.rune = rune;
		ITEMS.add(this);
	}

	public Runes getRune()
	{
		return this.rune;
	}

	@Override
	public boolean hasGlint(ItemStack stack)
	{
		return true;
	}
}
