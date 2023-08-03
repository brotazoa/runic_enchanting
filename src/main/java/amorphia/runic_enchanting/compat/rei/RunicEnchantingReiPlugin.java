package amorphia.runic_enchanting.compat.rei;

import amorphia.runic_enchanting.RunicEnchanting;
import amorphia.runic_enchanting.RunicEnchantingCreativeTab;
import amorphia.runic_enchanting.blocks.RE_Blocks;
import amorphia.runic_enchanting.recipes.RuneEnchantingRecipe;
import amorphia.runic_enchanting.recipes.RuneScribingRecipe;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.entry.EntryRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;

public class RunicEnchantingReiPlugin implements REIClientPlugin
{
	@Override
	public void registerCategories(CategoryRegistry registry)
	{
		registry.add(new RuneScribingReiCategory());
		registry.addWorkstations(RuneScribingReiCategory.IDENTIFIER, RuneScribingReiCategory.ICON);

		registry.add(new RuneEnchantingReiCategory());
		registry.addWorkstations(RuneEnchantingReiCategory.IDENTIFIER, RuneEnchantingReiCategory.ICON);
	}

	@Override
	public void registerDisplays(DisplayRegistry registry)
	{
		registry.registerFiller(RuneScribingRecipe.class, RuneScribingReiDisplay::new);

		registry.registerFiller(RuneEnchantingRecipe.class, recipe -> {
			Enchantment enchantment = Registries.ENCHANTMENT.get(recipe.getEnchantmentIdentifier());
			if(enchantment == null)
				return null;

			if(!recipe.shouldGenerateAdditionalLeveledRecipes())
				return new RuneEnchantingReiDisplay(recipe);

			return new RuneEnchantingReiDisplay(new RuneEnchantingRecipe(recipe, enchantment.getMaxLevel()));
		});
	}

	@Override
	public void registerEntries(EntryRegistry registry)
	{
		registry.removeEntry(EntryStacks.of(RunicEnchantingCreativeTab.ICON));
	}
}
