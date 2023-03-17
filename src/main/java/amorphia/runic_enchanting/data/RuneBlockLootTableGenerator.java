package amorphia.runic_enchanting.data;

import amorphia.runic_enchanting.RuneBaseBlocks;
import amorphia.runic_enchanting.Runes;
import amorphia.runic_enchanting.RunicEnchanting;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.data.server.BlockLootTableGenerator;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.util.Identifier;

import java.util.Locale;
import java.util.function.BiConsumer;

public class RuneBlockLootTableGenerator extends SimpleFabricLootTableProvider
{
	public RuneBlockLootTableGenerator(FabricDataGenerator dataGenerator)
	{
		super(dataGenerator, LootContextTypes.BLOCK);
	}

	@Override
	public void accept(BiConsumer<Identifier, LootTable.Builder> identifierBuilderBiConsumer)
	{
		for(RuneBaseBlocks baseBlock : RuneBaseBlocks.VALUES_CACHE)
		{
			for(Runes rune : Runes.VALUES_CACHE)
			{
				final Identifier identifier = RunicEnchanting.identify("blocks/" + baseBlock.name().toLowerCase(Locale.ROOT) + "_" + rune.name().toLowerCase(Locale.ROOT) + "_rune_block");
				identifierBuilderBiConsumer.accept(identifier, BlockLootTableGenerator.drops(baseBlock.getReferenceBlock().asItem()));
			}
		}
	}
}
