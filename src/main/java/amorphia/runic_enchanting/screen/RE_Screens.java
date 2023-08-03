package amorphia.runic_enchanting.screen;

import amorphia.runic_enchanting.RunicEnchanting;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;

public class RE_Screens
{
	public static ScreenHandlerType<RuneScribingScreenHandler> RUNE_SCRIBING_SCREEN_HANDLER_TYPE;
	public static ScreenHandlerType<RuneEnchantingScreenHandler> RUNE_ENCHANTING_SCREEN_HANDLER_TYPE;

	public static void init()
	{
		RUNE_SCRIBING_SCREEN_HANDLER_TYPE = Registry.register(Registries.SCREEN_HANDLER, RunicEnchanting.identify("rune_scribing"), new ScreenHandlerType<>(RuneScribingScreenHandler::new, FeatureFlags.VANILLA_FEATURES));
		RUNE_ENCHANTING_SCREEN_HANDLER_TYPE = Registry.register(Registries.SCREEN_HANDLER, RunicEnchanting.identify("rune_enchanting"), new ScreenHandlerType<>(RuneEnchantingScreenHandler::new, FeatureFlags.VANILLA_FEATURES));
	}

	public static void initClient()
	{
		HandledScreens.register(RUNE_SCRIBING_SCREEN_HANDLER_TYPE, RuneScribingScreen::new);
		HandledScreens.register(RUNE_ENCHANTING_SCREEN_HANDLER_TYPE, RuneEnchantingScreen::new);
	}
}
