package amorphia.runic_enchanting;

public enum RuneColors
{
	STONE(4407875),
	SANDSTONE(11179096),
	RED_SANDSTONE(8533504),
	DEEPSLATE(986895),
	BLACKSTONE(1114121),
	NETHER_BRICKS(917504),
	QUARTZ(13025972),
	;

	private final int color;

	RuneColors(int color)
	{
		this.color = color;
	}

	public int getColor()
	{
		return this.color;
	}
}
