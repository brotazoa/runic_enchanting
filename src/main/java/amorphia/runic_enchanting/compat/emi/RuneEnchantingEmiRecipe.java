package amorphia.runic_enchanting.compat.emi;

import amorphia.runic_enchanting.recipes.RuneEnchantingRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RuneEnchantingEmiRecipe implements EmiRecipe
{
	private final Identifier identifier;
	private final List<EmiIngredient> inputs;
	private final List<EmiStack> output;

	public RuneEnchantingEmiRecipe(RuneEnchantingRecipe recipe)
	{
		this.identifier = recipe.getId();
		this.inputs = List.of(EmiIngredient.of(recipe.getIngredients().get(0)),
				EmiIngredient.of(recipe.getIngredients().get(1)),
				EmiIngredient.of(recipe.getIngredients().get(2)),
				EmiIngredient.of(recipe.getIngredients().get(3)),
				EmiIngredient.of(recipe.getIngredients().get(4)));
		this.output = List.of(EmiStack.of(recipe.getOutput()));
	}

	@Override
	public EmiRecipeCategory getCategory()
	{
		return RunicEnchantingEmiPlugin.RUNE_ENCHANTING_CATEGORY;
	}

	@Override
	public @Nullable Identifier getId()
	{
		return this.identifier;
	}

	@Override
	public List<EmiIngredient> getInputs()
	{
		return this.inputs;
	}

	@Override
	public List<EmiStack> getOutputs()
	{
		return this.output;
	}

	@Override
	public int getDisplayWidth()
	{
		return 154;
	}

	@Override
	public int getDisplayHeight()
	{
		return 18;
	}

	@Override
	public void addWidgets(WidgetHolder widgets)
	{
		widgets.addSlot(inputs.get(0), 0, 0);
		widgets.addTexture(EmiTexture.PLUS, 20, 1);
		widgets.addSlot(inputs.get(1), 36, 0);
		widgets.addSlot(inputs.get(2), 54, 0);
		widgets.addSlot(inputs.get(3), 72, 0);
		widgets.addSlot(inputs.get(4), 90, 0);
		widgets.addTexture(EmiTexture.EMPTY_ARROW, 110, 1);
		widgets.addSlot(output.get(0), 136, 0).recipeContext(this);
	}
}
