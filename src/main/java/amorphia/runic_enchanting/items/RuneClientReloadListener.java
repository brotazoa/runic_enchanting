package amorphia.runic_enchanting.items;

import amorphia.runic_enchanting.RuneColors;
import amorphia.runic_enchanting.RunicEnchanting;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

public class RuneClientReloadListener implements SimpleSynchronousResourceReloadListener
{
	public static final RuneClientReloadListener INSTANCE = new RuneClientReloadListener();
	public static final Identifier RUNE_PATTERN_CLIENT_RELOAD_LISTENER = RunicEnchanting.identify("rune_client_reload_listener");

	@Override
	public Identifier getFabricId()
	{
		return RUNE_PATTERN_CLIENT_RELOAD_LISTENER;
	}

	@Override
	public void reload(ResourceManager manager)
	{
		RunePatternItem.ITEMS.forEach(item -> {
			ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
				return tintIndex == 1 ? RuneColors.BLACKSTONE.getColor() : -1;
			}, item);
		});

		RunePageItem.ITEMS.forEach(item -> {
			ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
				return tintIndex == 1 ? RuneColors.BLACKSTONE.getColor() : -1;
			}, item);
		});
	}
}
