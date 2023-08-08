package amorphia.runic_enchanting.data;

import amorphia.runic_enchanting.blocks.RE_Blocks;
import amorphia.runic_enchanting.blocks.RuneBlock;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;

public class RuneBlockLootTableGenerator extends FabricBlockLootTableProvider
{
	public RuneBlockLootTableGenerator(FabricDataOutput dataGenerator)
	{
		super(dataGenerator);
	}

	@Override
	public void generate()
	{
		for(Block block : RE_Blocks.BLOCKS.values())
		{
			if (block instanceof RuneBlock runeBlock)
			{
				addDrop(runeBlock, runeBlock.getBaseBlock().getReferenceBlock().asItem());
			}
		}
	}
}
