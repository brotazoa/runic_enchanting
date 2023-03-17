package amorphia.runic_enchanting;

import net.minecraft.block.entity.BannerPattern;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

import java.util.Locale;

public class RE_BannerPatterns
{
	public static void init()
	{
		for (Runes rune : Runes.VALUES_CACHE)
		{
			Registry.register(Registry.BANNER_PATTERN,
					RegistryKey.of(Registry.BANNER_PATTERN_KEY, RunicEnchanting.identify(rune.name().toLowerCase(Locale.ROOT))),
					new BannerPattern(rune.name().toLowerCase(Locale.ROOT)));
		}
	}

	public static void initClient()
	{

	}
}
