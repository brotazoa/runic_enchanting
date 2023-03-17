package amorphia.runic_enchanting.screen;

import amorphia.runic_enchanting.RunicEnchanting;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.registry.Registry;

public class RE_Screens
{
	public static ScreenHandlerType<RuneScribingScreenHandler> RUNE_SCRIBING_SCREEN_HANDLER_TYPE;
	public static ScreenHandlerType<RuneEnchantingScreenHandler> RUNE_ENCHANTING_SCREEN_HANDLER_TYPE;

	public static void init()
	{
		RUNE_SCRIBING_SCREEN_HANDLER_TYPE = Registry.register(Registry.SCREEN_HANDLER, RunicEnchanting.identify("rune_scribing"), new ScreenHandlerType<>(RuneScribingScreenHandler::new));
		RUNE_ENCHANTING_SCREEN_HANDLER_TYPE = Registry.register(Registry.SCREEN_HANDLER, RunicEnchanting.identify("rune_enchanting"), new ScreenHandlerType<>(RuneEnchantingScreenHandler::new));
	}

	public static void initClient()
	{
		HandledScreens.register(RUNE_SCRIBING_SCREEN_HANDLER_TYPE, RuneScribingScreen::new);
		HandledScreens.register(RUNE_ENCHANTING_SCREEN_HANDLER_TYPE, RuneEnchantingScreen::new);
	}
}
