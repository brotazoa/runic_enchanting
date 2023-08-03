package amorphia.runic_enchanting;

import net.minecraft.block.entity.BannerPattern;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.Locale;

public class RE_BannerPatterns
{
	public static void init()
	{
		for (Runes rune : Runes.VALUES_CACHE)
		{
			Registry.register(Registries.BANNER_PATTERN,
					RegistryKey.of(RegistryKeys.BANNER_PATTERN, RunicEnchanting.identify(rune.name().toLowerCase(Locale.ROOT))),
					new BannerPattern(rune.name().toLowerCase(Locale.ROOT)));
		}
	}

	public static void initClient()
	{

	}
}
