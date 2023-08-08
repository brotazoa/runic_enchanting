package amorphia.runic_enchanting.blocks;

import amorphia.runic_enchanting.RuneBaseBlocks;
import amorphia.runic_enchanting.Runes;
import amorphia.runic_enchanting.RunicEnchanting;
import amorphia.runic_enchanting.items.ChiselItem;
import com.google.common.collect.Maps;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.ResourceType;

import java.util.Locale;
import java.util.Map;

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
	}

	private static void makeBlocksForBase(RuneBaseBlocks baseBlock)
	{
		for(Runes rune : Runes.VALUES_CACHE)
		{
			Block runeBlock = register(baseBlock.name().toLowerCase(Locale.ROOT) + "_" + rune.name().toLowerCase(Locale.ROOT) + "_rune_block", new RuneBlock(FabricBlockSettings.copyOf(baseBlock.getReferenceBlock()), rune, baseBlock));
			ChiselItem.addBlockstateForRune(baseBlock.getReferenceBlock(), rune, runeBlock.getDefaultState());
		}
	}

	private static Block register(String path, Block block)
	{
		BLOCKS.put(path, block);
		return Registry.register(Registries.BLOCK, RunicEnchanting.identify(path), block);
	}
}
