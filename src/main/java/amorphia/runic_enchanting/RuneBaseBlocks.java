package amorphia.runic_enchanting;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;

public enum RuneBaseBlocks
{
	CHISELED_STONE_BRICKS(Blocks.CHISELED_STONE_BRICKS, RunicEnchanting.identify("block/stone_bricks_rune"), RuneColors.STONE),
	CHISELED_SANDSTONE(Blocks.CHISELED_SANDSTONE, RunicEnchanting.identify("block/sandstone_rune"), RuneColors.SANDSTONE),
	CHISELED_RED_SANDSTONE(Blocks.CHISELED_RED_SANDSTONE, RunicEnchanting.identify("block/red_sandstone_rune"), RuneColors.RED_SANDSTONE),
	CHISELED_DEEPSLATE(Blocks.CHISELED_DEEPSLATE, RunicEnchanting.identify("block/deepslate_rune"), RuneColors.DEEPSLATE),
	CHISELED_POLISHED_BLACKSTONE(Blocks.CHISELED_POLISHED_BLACKSTONE, RunicEnchanting.identify("block/polished_blackstone_rune"), RuneColors.BLACKSTONE),
	CHISELED_NETHER_BRICKS(Blocks.CHISELED_NETHER_BRICKS, RunicEnchanting.identify("block/nether_bricks_rune"), RuneColors.NETHER_BRICKS),
	CHISELED_QUARTZ(Blocks.CHISELED_QUARTZ_BLOCK, RunicEnchanting.identify("block/quartz_rune"), RuneColors.QUARTZ),
	;

	public static final RuneBaseBlocks[] VALUES_CACHE = RuneBaseBlocks.values();

	private final Block referenceBlock;
	private final Identifier baseTexture;
	private final RuneColors color;

	RuneBaseBlocks(Block referenceBlock, Identifier baseTexture, RuneColors color)
	{
		this.referenceBlock = referenceBlock;
		this.baseTexture = baseTexture;
		this.color = color;
	}

	public Block getReferenceBlock()
	{
		return this.referenceBlock;
	}

	public Identifier getBaseTexture()
	{
		return this.baseTexture;
	}

	public RuneColors getColor()
	{
		return this.color;
	}
}
