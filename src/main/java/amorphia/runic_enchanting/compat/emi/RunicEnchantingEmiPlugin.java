package amorphia.runic_enchanting.compat.emi;

import amorphia.runic_enchanting.RunicEnchanting;
import amorphia.runic_enchanting.RunicEnchantingCreativeTab;
import amorphia.runic_enchanting.blocks.RE_Blocks;
import amorphia.runic_enchanting.recipes.RuneEnchantingRecipe;
import amorphia.runic_enchanting.recipes.RuneScribingRecipe;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;

public class RunicEnchantingEmiPlugin implements EmiPlugin
{
	public static EmiRecipeCategory RUNE_SCRIBING_CATEGORY;
	public static EmiRecipeCategory RUNE_ENCHANTING_CATEGORY;

	@Override
	public void register(EmiRegistry registry)
	{
		RUNE_SCRIBING_CATEGORY = new EmiRecipeCategory(RunicEnchanting.identify("rune_scribing_category"), EmiStack.of(RE_Blocks.BLOCKS.get("rune_scribing_table")));
		RUNE_ENCHANTING_CATEGORY = new EmiRecipeCategory(RunicEnchanting.identify("rune_enchanting_category"), EmiStack.of(RE_Blocks.BLOCKS.get("rune_enchanting_table")));

		registry.addCategory(RUNE_SCRIBING_CATEGORY);
		registry.addCategory(RUNE_ENCHANTING_CATEGORY);

		registry.addWorkstation(RUNE_SCRIBING_CATEGORY, EmiStack.of(RE_Blocks.BLOCKS.get("rune_scribing_table")));
		registry.addWorkstation(RUNE_ENCHANTING_CATEGORY, EmiStack.of(RE_Blocks.BLOCKS.get("rune_enchanting_table")));

		for(RuneScribingRecipe recipe : registry.getRecipeManager().listAllOfType(RuneScribingRecipe.Type.INSTANCE))
		{
			registry.addRecipe(new RuneScribingEmiRecipe(recipe));
		}

		for(RuneEnchantingRecipe recipe : registry.getRecipeManager().listAllOfType(RuneEnchantingRecipe.Type.INSTANCE))
		{
			Enchantment enchantment = Registries.ENCHANTMENT.get(recipe.getEnchantmentIdentifier());
			if(enchantment == null)
				continue;

			if(!recipe.shouldGenerateAdditionalLeveledRecipes())
			{
				registry.addRecipe(new RuneEnchantingEmiRecipe(recipe));
				continue;
			}

			registry.addRecipe(new RuneEnchantingEmiRecipe(new RuneEnchantingRecipe(recipe, enchantment.getMaxLevel())));
		}

		registry.removeEmiStacks(EmiStack.of(RunicEnchantingCreativeTab.ICON));
	}
}
