package amorphia.runic_enchanting.data;

import amorphia.runic_enchanting.RuneBaseBlocks;
import amorphia.runic_enchanting.Runes;
import amorphia.runic_enchanting.RunicEnchanting;
import amorphia.runic_enchanting.blocks.RE_Blocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.util.Identifier;

import java.util.Locale;
import java.util.function.BiConsumer;

public class RuneBlockLootTableGenerator extends FabricBlockLootTableProvider
{
	public RuneBlockLootTableGenerator(FabricDataOutput dataGenerator)
	{
		super(dataGenerator);
	}

	@Override
	public void generate()
	{
		for (RuneBaseBlocks baseBlock : RuneBaseBlocks.VALUES_CACHE)
		{
			for (Runes rune : Runes.VALUES_CACHE)
			{
				Block runeBlock = RE_Blocks.BLOCKS.get(baseBlock.name().toLowerCase(Locale.ROOT) + "_" + rune.name().toLowerCase(Locale.ROOT) + "_rune_block");
				if (runeBlock != null)
				{
					addDrop(runeBlock, baseBlock.getReferenceBlock().asItem());
				}
			}
		}
	}

//	@Override
//	public void accept(BiConsumer<Identifier, LootTable.Builder> identifierBuilderBiConsumer)
//	{
//		for(RuneBaseBlocks baseBlock : RuneBaseBlocks.VALUES_CACHE)
//		{
//			for(Runes rune : Runes.VALUES_CACHE)
//			{
//				final Identifier identifier = RunicEnchanting.identify("blocks/" + baseBlock.name().toLowerCase(Locale.ROOT) + "_" + rune.name().toLowerCase(Locale.ROOT) + "_rune_block");
//				identifierBuilderBiConsumer.accept(identifier, BlockLootTableGenerator.drops(baseBlock.getReferenceBlock().asItem()));
//			}
//		}
//	}
}
