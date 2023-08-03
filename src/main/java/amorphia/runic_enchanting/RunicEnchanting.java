package amorphia.runic_enchanting;

import amorphia.runic_enchanting.blocks.RE_Blocks;
import amorphia.runic_enchanting.data.RuneBlockLootTableGenerator;
import amorphia.runic_enchanting.data.RuneBlockModelGenerator;
import amorphia.runic_enchanting.data.RuneBlockTagGenerator;
import amorphia.runic_enchanting.data.RuneItemTagGenerator;
import amorphia.runic_enchanting.items.RE_Items;
import amorphia.runic_enchanting.loot.RE_Loot;
import amorphia.runic_enchanting.recipes.RE_Recipes;
import amorphia.runic_enchanting.screen.RE_Screens;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RunicEnchanting implements ModInitializer, ClientModInitializer, DataGeneratorEntrypoint
{
	public static final String modid = "runic_enchanting";
	public static final Logger LOGGER = LogManager.getLogger();

	@Override
	public void onInitialize()
	{
		RE_Tags.init();
		RE_Blocks.init();
		RE_Items.init();
		RE_Recipes.init();
		RE_Screens.init();
		RE_Loot.init();
		RE_BannerPatterns.init();

		RunicEnchantingCreativeTab.init();

		//fabric enchantments compat
		if (FabricLoader.getInstance().isModLoaded("fabricenchantments"))
		{
			FabricLoader.getInstance().getModContainer(modid).ifPresent(container -> {
				ResourceManagerHelper.registerBuiltinResourcePack(identify("fabric_enchantments_compat"), container, ResourcePackActivationType.DEFAULT_ENABLED);
			});
		}

		//enchanted shulkers compat
		if (FabricLoader.getInstance().isModLoaded("enchantedshulkers"))
		{
			FabricLoader.getInstance().getModContainer(modid).ifPresent(container -> {
				ResourceManagerHelper.registerBuiltinResourcePack(identify("enchanted_shulkers_compat"), container, ResourcePackActivationType.DEFAULT_ENABLED);
			});
		}

		//imperishable items compat
		if (FabricLoader.getInstance().isModLoaded("imperishableitems"))
		{
			FabricLoader.getInstance().getModContainer(modid).ifPresent(container -> {
				ResourceManagerHelper.registerBuiltinResourcePack(identify("imperishable_items_compat"), container, ResourcePackActivationType.DEFAULT_ENABLED);
			});
		}

		//Charm compat
		if (FabricLoader.getInstance().isModLoaded("charm"))
		{
			FabricLoader.getInstance().getModContainer(modid).ifPresent(container -> {
				ResourceManagerHelper.registerBuiltinResourcePack(identify("charm_compat"), container, ResourcePackActivationType.DEFAULT_ENABLED);
			});
		}

		//MoEnchantments compat
		if (FabricLoader.getInstance().isModLoaded("moenchantments"))
		{
			FabricLoader.getInstance().getModContainer(modid).ifPresent(container -> {
				ResourceManagerHelper.registerBuiltinResourcePack(identify("moenchantments_compat"), container, ResourcePackActivationType.DEFAULT_ENABLED);
			});
		}
	}

	@Override
	public void onInitializeClient()
	{
		RE_Tags.initClient();
		RE_Blocks.initClient();
		RE_Items.initClient();
		RE_Recipes.initClient();
		RE_Screens.initClient();
		RE_Loot.initClient();
		RE_BannerPatterns.initClient();
	}

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator)
	{
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(RuneBlockModelGenerator::new);
		pack.addProvider(RuneBlockLootTableGenerator::new);
		pack.addProvider(RuneBlockTagGenerator::new);
		pack.addProvider(RuneItemTagGenerator::new);
	}

	public static Identifier identify(String path)
	{
		return new Identifier(modid, path);
	}
}
