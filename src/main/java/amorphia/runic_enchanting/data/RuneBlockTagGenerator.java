package amorphia.runic_enchanting.data;

import amorphia.runic_enchanting.blocks.RE_Blocks;
import amorphia.runic_enchanting.blocks.RuneBlock;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class RuneBlockTagGenerator extends FabricTagProvider.BlockTagProvider
{
	public RuneBlockTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture)
	{
		super(output, registriesFuture);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup arg)
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
