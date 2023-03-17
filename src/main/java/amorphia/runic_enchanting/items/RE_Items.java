package amorphia.runic_enchanting.items;

import amorphia.runic_enchanting.Runes;
import amorphia.runic_enchanting.RunicEnchanting;
import amorphia.runic_enchanting.blocks.RE_Blocks;
import com.google.common.collect.Maps;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

public class RE_Items
{
	public static final Map<String, Item> ITEMS = Maps.newLinkedHashMap();

	public static void init()
	{
		for (Map.Entry<String, Block> entry : RE_Blocks.BLOCKS.entrySet())
		{
			register(entry.getKey(), new BlockItem(entry.getValue(), new Item.Settings().group(RunicEnchanting.group)));
		}

		register("rune_chalk", new ChalkItem(new Item.Settings().maxDamage(8).group(RunicEnchanting.group)));
		register("rune_chisel", new ChiselItem(new Item.Settings().maxDamage(64).group(RunicEnchanting.group)));

		makePatternItems();
		makeRuneStones();
		makeRunePages();
	}

	private static void makePatternItems()
	{
		for(Runes rune : Runes.VALUES_CACHE)
		{
			registerGeneratedItem(rune.name().toLowerCase(Locale.ROOT) + "_rune_pattern",
					new RunePatternItem(new Item.Settings().group(RunicEnchanting.group), rune),
					RuneItemModelBuilder.createRunePatternItemModelJson(rune));
		}
	}

	private static void makeRuneStones()
	{
		for(Runes rune : Runes.VALUES_CACHE)
		{
			registerGeneratedItem(rune.name().toLowerCase(Locale.ROOT) + "_rune_stone",
					new RuneStoneItem(new Item.Settings().group(RunicEnchanting.group), rune),
					RuneItemModelBuilder.createRuneStoneItemModelJson(rune));
		}
	}

	private static void makeRunePages()
	{
		for(Runes rune : Runes.VALUES_CACHE)
		{
			registerGeneratedItem(rune.name().toLowerCase(Locale.ROOT) + "_rune_page",
					new RunePageItem(new Item.Settings().group(RunicEnchanting.group), rune),
					RuneItemModelBuilder.createRunePageItemModelJson(rune));
		}
	}

	public static void initClient()
	{
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(RuneClientReloadListener.INSTANCE);
	}

	private static Item registerGeneratedItem(String path, Item item, Supplier<String> modelSupplier)
	{
		Identifier identifier = RunicEnchanting.identify("item/" + path);
		RuneItemModelBuilder.register(identifier, modelSupplier);
		return register(path, item);
	}

	private static Item register(String path, Item item)
	{
		ITEMS.put(path, item);
		return Registry.register(Registry.ITEM, RunicEnchanting.identify(path), item);
	}
}
