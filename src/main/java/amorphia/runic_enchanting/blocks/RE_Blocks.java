package amorphia.runic_enchanting.blocks;

import amorphia.runic_enchanting.RuneBaseBlocks;
import amorphia.runic_enchanting.Runes;
import amorphia.runic_enchanting.RunicEnchanting;
import amorphia.runic_enchanting.items.ChalkItem;
import amorphia.runic_enchanting.items.ChiselItem;
import com.google.common.collect.Maps;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.ResourceType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

public class RE_Blocks
{
	public static final Map<String, Block> BLOCKS = Maps.newLinkedHashMap();

	public static void init()
	{
		register("rune_scribing_table", new RuneScribingTable(FabricBlockSettings.copyOf(Blocks.ENCHANTING_TABLE)));
		register("rune_enchanting_table", new RunicEnchantingTable(FabricBlockSettings.copyOf(Blocks.CRAFTING_TABLE)));

		for(RuneBaseBlocks baseBlock : RuneBaseBlocks.VALUES_CACHE)
		{
			makeBlocksForBase(baseBlock);
		}
	}

	public static void initClient()
	{
		for(Block block : BLOCKS.values())
		{
			BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
		}

		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(RuneBlockClientReloadListener.INSTANCE);
		ModelLoadingRegistry.INSTANCE.registerResourceProvider(resourceManager -> new RuneBlockModelProvider());
	}

	private static void makeBlocksForBase(RuneBaseBlocks baseBlock)
	{
		for(Runes rune : Runes.VALUES_CACHE)
		{
			//default
			Block runeBlock = registerGeneratedBlock(baseBlock.name().toLowerCase(Locale.ROOT) + "_" + rune.name().toLowerCase(Locale.ROOT) + "_rune_block",
					new RuneBlock(FabricBlockSettings.copyOf(baseBlock.getReferenceBlock()), rune, baseBlock.getColor()),
					RuneBlockModelBuilder.createRuneBlockModelJson(baseBlock, rune));

			ChiselItem.addBlockstateForRune(baseBlock.getReferenceBlock(), rune, runeBlock.getDefaultState());
		}
	}

	private static Block registerGeneratedBlock(String path, Block block, Supplier<String> modelJsonSupplier)
	{
		Identifier identifier = RunicEnchanting.identify("block/" + path);
		RuneBlockModelBuilder.register(identifier, modelJsonSupplier);
		return register(path, block);
	}

	private static Block register(String path, Block block)
	{
		BLOCKS.put(path, block);
		return Registry.register(Registries.BLOCK, RunicEnchanting.identify(path), block);
	}
}
