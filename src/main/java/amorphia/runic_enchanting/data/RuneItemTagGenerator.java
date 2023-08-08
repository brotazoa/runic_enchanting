package amorphia.runic_enchanting.data;

import amorphia.runic_enchanting.RE_Tags;
import amorphia.runic_enchanting.blocks.RuneBlock;
import amorphia.runic_enchanting.items.RE_Items;
import amorphia.runic_enchanting.items.RunePageItem;
import amorphia.runic_enchanting.items.RunePatternItem;
import amorphia.runic_enchanting.items.RuneStoneItem;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class RuneItemTagGenerator extends FabricTagProvider.ItemTagProvider
{
	public RuneItemTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture)
	{
		super(output, completableFuture);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup arg)
	{
		RE_Items.ITEMS.values().forEach(item -> {
			if (item instanceof BlockItem blockItem && blockItem.getBlock() instanceof RuneBlock runeBlock)
			{
				getOrCreateTagBuilder(RE_Tags.BLOCK_TAG_BY_RUNE.get(runeBlock.getRune())).add(item);
			}

			if (item instanceof RunePatternItem)
			{
				getOrCreateTagBuilder(RE_Tags.RUNE_PATTERNS).add(item);
			}

			if (item instanceof RuneStoneItem)
			{
				getOrCreateTagBuilder(RE_Tags.RUNE_STONES).add(item);
			}

			if (item instanceof RunePageItem)
			{
				getOrCreateTagBuilder(RE_Tags.RUNE_PAGES).add(item);
			}
		});
	}
}
