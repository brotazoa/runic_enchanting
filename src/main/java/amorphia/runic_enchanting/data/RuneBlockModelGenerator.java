package amorphia.runic_enchanting.data;

import amorphia.runic_enchanting.RunicEnchanting;
import amorphia.runic_enchanting.blocks.RE_Blocks;
import amorphia.runic_enchanting.blocks.RuneBlock;
import amorphia.runic_enchanting.items.RE_Items;
import amorphia.runic_enchanting.items.RunePageItem;
import amorphia.runic_enchanting.items.RunePatternItem;
import amorphia.runic_enchanting.items.RuneStoneItem;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

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
		final TextureKey base = TextureKey.of("base");
		final TextureKey rune = TextureKey.of("rune");
		final Model runeBlockModelTemplate = new Model(Optional.of(new Identifier("runic_enchanting", "block/rune_block_template")), Optional.empty(), base, rune);

		for (Block block : RE_Blocks.BLOCKS.values())
		{
			if(block instanceof RuneBlock runeBlock)
			{
				blockStateModelGenerator.registerSimpleState(block);

				final Identifier baseTexture = runeBlock.getBaseBlock().getBaseTexture();
				final Identifier runeTextureIdentifier = runeBlock.getRune().getRuneTextureIdentifier();
				final Identifier runeTexture = new Identifier(runeTextureIdentifier.getNamespace(), "block/" + runeTextureIdentifier.getPath() + "_block_overlay");

				TextureMap textures = new TextureMap();
				textures.put(base, baseTexture);
				textures.put(rune, runeTexture);

				runeBlockModelTemplate.upload(block, textures, blockStateModelGenerator.modelCollector);
			}
		}
	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator)
	{
		final Identifier runePatternBaseTexture = RunicEnchanting.identify("item/rune_pattern_base");
		final Identifier runePageBaseTexture = RunicEnchanting.identify("item/rune_sheet_base");
		final Identifier runeStoneBaseTexture = RunicEnchanting.identify("item/rune_stone_base");

		for(Item item : RE_Items.ITEMS.values())
		{
			if (item instanceof RunePatternItem runePatternItem)
			{
				final Identifier runeTexture = runePatternItem.getRune().getRuneTextureIdentifier();
				Identifier runeItemOverlayTexture = new Identifier(runeTexture.getNamespace(), "item/" + runeTexture.getPath() + "_item_overlay");
				Models.GENERATED_TWO_LAYERS.upload(ModelIds.getItemModelId(item), TextureMap.layered(runePatternBaseTexture, runeItemOverlayTexture), itemModelGenerator.writer);
			}

			if (item instanceof RunePageItem runePageItem)
			{
				final Identifier runeTexture = runePageItem.getRune().getRuneTextureIdentifier();
				Identifier runeItemOverlayTexture = new Identifier(runeTexture.getNamespace(), "item/" + runeTexture.getPath() + "_item_overlay");
				Models.GENERATED_TWO_LAYERS.upload(ModelIds.getItemModelId(item), TextureMap.layered(runePageBaseTexture, runeItemOverlayTexture), itemModelGenerator.writer);
			}

			if (item instanceof RuneStoneItem runeStoneItem)
			{
				final Identifier runeTexture = runeStoneItem.getRune().getRuneTextureIdentifier();
				Identifier runeItemOverlayTexture = new Identifier(runeTexture.getNamespace(), "item/" + runeTexture.getPath() + "_item_overlay");
				Models.GENERATED_TWO_LAYERS.upload(ModelIds.getItemModelId(item), TextureMap.layered(runeStoneBaseTexture, runeItemOverlayTexture), itemModelGenerator.writer);
			}
		}
	}
}
