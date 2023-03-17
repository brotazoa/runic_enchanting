package amorphia.runic_enchanting.blocks;

import amorphia.runic_enchanting.RunicEnchanting;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

public class RuneBlockClientReloadListener implements SimpleSynchronousResourceReloadListener
{
	public static final RuneBlockClientReloadListener INSTANCE = new RuneBlockClientReloadListener();
	public static final Identifier RUNE_BLOCK_CLIENT_RELOAD_LISTENER = RunicEnchanting.identify("rune_block_client_reload_listener");

	@Override
	public Identifier getFabricId()
	{
		return RUNE_BLOCK_CLIENT_RELOAD_LISTENER;
	}

	@Override
	public void reload(ResourceManager manager)
	{
		RuneBlock.BLOCKS.forEach(block -> {
			ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
				return tintIndex == 0 ? block.getColor().getColor() : -1;
			}, block);
		});
	}
}
