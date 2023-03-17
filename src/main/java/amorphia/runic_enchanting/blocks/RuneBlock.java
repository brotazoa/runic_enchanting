package amorphia.runic_enchanting.blocks;

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

	public RuneBlock(Settings settings, Runes rune, RuneColors color)
	{
		super(settings);
		this.rune = rune;
		this.color = color;
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
}
