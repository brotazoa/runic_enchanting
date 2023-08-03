package amorphia.runic_enchanting;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class RunicEnchantingCreativeTab
{
	public static final Item ICON = Registry.register(Registries.ITEM, RunicEnchanting.identify("tab_item"), new Item(new Item.Settings()));
	public static final List<Item> ENTRIES = new ArrayList<>();

	public static void init()
	{
		final ItemGroup GROUP = FabricItemGroup.builder().icon(() -> new ItemStack(ICON))
											   .displayName(Text.translatable("itemGroup." + RunicEnchanting.modid + ".group"))
											   .entries((displayContext, items) -> {
												   for(Item item : ENTRIES)
												   {
													   items.add(item);
												   }
											   }).build();

		Registry.register(Registries.ITEM_GROUP, RunicEnchanting.identify("group"), GROUP);
	}

	public static void add(Item item)
	{
		ENTRIES.add(item);
	}
}
