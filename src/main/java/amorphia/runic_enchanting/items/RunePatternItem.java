package amorphia.runic_enchanting.items;

import amorphia.runic_enchanting.RE_Tags;
import amorphia.runic_enchanting.Runes;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.item.BannerPatternItem;
import net.minecraft.item.Item;

import java.util.List;
import java.util.Map;

public class RunePatternItem extends BannerPatternItem
{
	public static final Map<Runes, RunePatternItem> PATTERN_ITEM_BY_RUNE = Maps.newHashMap();

	public static final List<RunePatternItem> ITEMS = Lists.newArrayList();

	protected final Runes rune;

	public RunePatternItem(Runes rune)
	{
		this(new Settings(), rune);
	}

	public RunePatternItem(Settings settings, Runes rune)
	{
		super(RE_Tags.BANNER_PATTERN_TAG_BY_RUNE.get(rune), settings);
		this.rune = rune;
		PATTERN_ITEM_BY_RUNE.put(rune, this);
		ITEMS.add(this);
	}

	public Runes getRune()
	{
		return this.rune;
	}
}
