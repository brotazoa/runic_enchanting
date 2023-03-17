package amorphia.runic_enchanting.compat.rei;

import amorphia.runic_enchanting.RE_Tags;
import amorphia.runic_enchanting.recipes.RuneScribingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.recipe.Ingredient;

import java.util.Collections;
import java.util.List;

public class RuneScribingReiDisplay implements Display
{
	private final List<EntryIngredient> inputs;
	private final EntryIngredient output;
	private final EntryIngredient runeBlock;

	public RuneScribingReiDisplay(RuneScribingRecipe recipe)
	{
		inputs = List.of(EntryIngredients.ofIngredient(recipe.getIngredients().get(0)));
		output = EntryIngredients.of(recipe.getOutput());
		this.runeBlock = EntryIngredients.ofIngredient(Ingredient.fromTag(RE_Tags.BLOCK_TAG_BY_RUNE.get(recipe.getRequiredRune())));
	}

	@Override
	public List<EntryIngredient> getInputEntries()
	{
		return this.inputs;
	}

	@Override
	public List<EntryIngredient> getOutputEntries()
	{
		return Collections.singletonList(this.output);
	}

	public EntryIngredient getRuneBlockIngredient()
	{
		return this.runeBlock;
	}

	@Override
	public CategoryIdentifier<?> getCategoryIdentifier()
	{
		return RuneScribingReiCategory.IDENTIFIER;
	}
}
