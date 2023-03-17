package amorphia.runic_enchanting.data;

import amorphia.runic_enchanting.RE_Tags;
import amorphia.runic_enchanting.blocks.RE_Blocks;
import amorphia.runic_enchanting.blocks.RuneBlock;
import amorphia.runic_enchanting.items.RE_Items;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.registry.Registry;

public class RuneBlockTagGenerator extends FabricTagProvider<Block>
{
	public RuneBlockTagGenerator(FabricDataGenerator dataGenerator)
	{
		super(dataGenerator, Registry.BLOCK);
	}

	@Override
	protected void generateTags()
	{
		RE_Blocks.BLOCKS.values().forEach(block -> {
			if(block instanceof RuneBlock runeBlock)
			{
				getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(block);
			}
		});

		getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(RE_Blocks.BLOCKS.get("rune_scribing_table"));
		getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(RE_Blocks.BLOCKS.get("rune_enchanting_table"));
	}
}
