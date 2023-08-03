package amorphia.runic_enchanting.compat.emi;

import amorphia.runic_enchanting.RE_Tags;
import amorphia.runic_enchanting.recipes.RuneScribingRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RuneScribingEmiRecipe implements EmiRecipe
{
	private final Identifier identifier;
	private final List<EmiIngredient> inputs;
	private final List<EmiStack> output;
	private final EmiIngredient runeBlock;

	public RuneScribingEmiRecipe(RuneScribingRecipe recipe)
	{
		this.identifier = recipe.getId();
		this.inputs = List.of(EmiIngredient.of(recipe.getIngredients().get(0)));
		this.output = List.of(EmiStack.of(recipe.getOutput(null)));
		runeBlock = EmiIngredient.of(RE_Tags.BLOCK_TAG_BY_RUNE.get(recipe.getRequiredRune()));
	}

	@Override
	public EmiRecipeCategory getCategory()
	{
		return RunicEnchantingEmiPlugin.RUNE_SCRIBING_CATEGORY;
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
		return 99;
	}

	@Override
	public int getDisplayHeight()
	{
		return 18;
	}

	@Override
	public void addWidgets(WidgetHolder widgets)
	{
		widgets.addSlot(runeBlock, 0, 0).appendTooltip(Text.translatable("runic_enchanting.emi.rune_scribing.nearby_block"));
		widgets.addTexture(EmiTexture.PLUS, 20, 1);
		widgets.addSlot(inputs.get(0), 35, 0);
		widgets.addTexture(EmiTexture.EMPTY_ARROW, 55, 1);
		widgets.addSlot(output.get(0), 81, 0).recipeContext(this);
	}
}
