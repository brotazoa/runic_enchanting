package amorphia.runic_enchanting.items;

import amorphia.runic_enchanting.Runes;
import amorphia.runic_enchanting.RunicEnchanting;
import amorphia.runic_enchanting.RunicEnchantingCreativeTab;
import amorphia.runic_enchanting.blocks.RE_Blocks;
import com.google.common.collect.Maps;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.ResourceType;

import java.util.Locale;
import java.util.Map;

public class RE_Items
{
	public static final Map<String, Item> ITEMS = Maps.newLinkedHashMap();

	public static void init()
	{
		for (Map.Entry<String, Block> entry : RE_Blocks.BLOCKS.entrySet())
		{
			register(entry.getKey(), new BlockItem(entry.getValue(), new Item.Settings()));
		}

		register("rune_chalk", new ChalkItem(new Item.Settings().maxDamage(8)));
		register("rune_chisel", new ChiselItem(new Item.Settings().maxDamage(64)));

		makePatternItems();
		makeRuneStones();
		makeRunePages();
	}

	private static void makePatternItems()
	{
		for(Runes rune : Runes.VALUES_CACHE)
		{
			register(rune.name().toLowerCase(Locale.ROOT) + "_rune_pattern", new RunePatternItem(rune));
		}
	}

	private static void makeRuneStones()
	{
		for(Runes rune : Runes.VALUES_CACHE)
		{
			register(rune.name().toLowerCase(Locale.ROOT) + "_rune_stone", new RuneStoneItem(rune));
		}
	}

	private static void makeRunePages()
	{
		for(Runes rune : Runes.VALUES_CACHE)
		{
			register(rune.name().toLowerCase(Locale.ROOT) + "_rune_page", new RunePageItem(rune));
		}
	}

	public static void initClient()
	{
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(RuneClientReloadListener.INSTANCE);
	}

	private static Item register(String path, Item item)
	{
		ITEMS.put(path, item);
		RunicEnchantingCreativeTab.add(item);
		return Registry.register(Registries.ITEM, RunicEnchanting.identify(path), item);
	}
}
