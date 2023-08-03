package amorphia.runic_enchanting.data;

import amorphia.runic_enchanting.blocks.RE_Blocks;
import amorphia.runic_enchanting.blocks.RuneBlock;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Optional;

public class RuneBlockModelGenerator extends FabricModelProvider
{
	public RuneBlockModelGenerator(FabricDataOutput dataGenerator)
	{
		super(dataGenerator);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator)
	{
		for (Block block : RE_Blocks.BLOCKS.values())
		{
			if(block instanceof RuneBlock) blockStateModelGenerator.registerSimpleState(block);
		}
	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator)
	{
		//nothing to see here
	}
}
