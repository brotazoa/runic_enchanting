package amorphia.runic_enchanting.compat.rei;

import amorphia.runic_enchanting.recipes.RuneEnchantingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.collection.DefaultedList;

import java.util.Collections;
import java.util.List;

public class RuneEnchantingReiDisplay implements Display
{
	private final List<EntryIngredient> inputs;
	private final EntryIngredient output;

	public RuneEnchantingReiDisplay(RuneEnchantingRecipe recipe)
	{
		DefaultedList<Ingredient> ingredients = recipe.getIngredients();
		this.inputs = List.of(EntryIngredients.ofIngredient(ingredients.get(0)),
				EntryIngredients.ofIngredient(ingredients.get(1)),
				EntryIngredients.ofIngredient(ingredients.get(2)),
				EntryIngredients.ofIngredient(ingredients.get(3)),
				EntryIngredients.ofIngredient(ingredients.get(4)));
		this.output = EntryIngredients.of(recipe.getOutput());
	}

	@Override
	public List<EntryIngredient> getInputEntries()
	{
		return this.inputs;
	}

	@Override
	public List<EntryIngredient> getOutputEntries()
	{
		return Collections.singletonList(output);
	}

	@Override
	public CategoryIdentifier<?> getCategoryIdentifier()
	{
		return RuneEnchantingReiCategory.IDENTIFIER;
	}
}
