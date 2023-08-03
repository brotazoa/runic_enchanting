package amorphia.runic_enchanting.compat.rei;

import amorphia.runic_enchanting.blocks.RE_Blocks;
import amorphia.runic_enchanting.recipes.RuneScribingRecipe;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.TooltipContext;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class RuneScribingReiCategory implements DisplayCategory<RuneScribingReiDisplay>
{
	public static final Text TITLE = Text.translatable("emi.category.runic_enchanting.rune_scribing_category");
	public static final EntryStack<ItemStack> ICON = EntryStacks.of(RE_Blocks.BLOCKS.get("rune_scribing_table"));
	public static final CategoryIdentifier<RuneScribingReiDisplay> IDENTIFIER = CategoryIdentifier.of(RuneScribingRecipe.Type.ID);

	@Override
	public CategoryIdentifier<? extends RuneScribingReiDisplay> getCategoryIdentifier()
	{
		return IDENTIFIER;
	}

	@Override
	public Text getTitle()
	{
		return TITLE;
	}

	@Override
	public Renderer getIcon()
	{
		return ICON;
	}

	@Override
	public List<Widget> setupDisplay(RuneScribingReiDisplay display, Rectangle bounds)
	{
		List<Widget> widgets = new ArrayList<>();
		widgets.add(Widgets.withTooltip(Widgets.createSlot(new Point(bounds.x, bounds.y)).entries(display.getRuneBlockIngredient()), Text.translatable("runic_enchanting.emi.rune_scribing.nearby_block")));
		widgets.add(Widgets.createSlot(new Point(bounds.x  + 35, bounds.y)).entries(display.getInputEntries().get(0)).markInput());
		widgets.add(Widgets.createArrow(new Point(bounds.x + 55, bounds.y + 1)));
		widgets.add(Widgets.createSlot(new Point(bounds.x + 81, bounds.y)).entries(display.getOutputEntries().get(0)).disableBackground().disableHighlight().markOutput());
		return widgets;
	}

	@Override
	public int getDisplayWidth(RuneScribingReiDisplay display)
	{
		return 99;
	}

	@Override
	public int getDisplayHeight()
	{
		return 18;
	}
}
