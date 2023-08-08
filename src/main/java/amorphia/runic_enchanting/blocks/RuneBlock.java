package amorphia.runic_enchanting.blocks;

import amorphia.runic_enchanting.RuneBaseBlocks;
import amorphia.runic_enchanting.RuneColors;
import amorphia.runic_enchanting.Runes;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;

import java.util.List;

public class RuneBlock extends Block
{
	public static final List<RuneBlock> BLOCKS = Lists.newArrayList();

	protected final Runes rune;
	protected final RuneColors color;
	protected final RuneBaseBlocks baseBlock;

	public RuneBlock(Settings settings, Runes rune, RuneBaseBlocks baseBlock)
	{
		super(settings);
		this.rune = rune;
		this.color = baseBlock.getColor();
		this.baseBlock = baseBlock;
		BLOCKS.add(this);
	}

	public Runes getRune()
	{
		return this.rune;
	}

	public RuneColors getColor()
	{
		return this.color;
	}

	public RuneBaseBlocks getBaseBlock()
	{
		return this.baseBlock;
	}
}
