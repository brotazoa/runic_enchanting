package amorphia.runic_enchanting.compat.rei;

import amorphia.runic_enchanting.blocks.RE_Blocks;
import amorphia.runic_enchanting.recipes.RuneEnchantingRecipe;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
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

public class RuneEnchantingReiCategory implements DisplayCategory<RuneEnchantingReiDisplay>
{
	public static final Text TITLE = Text.translatable("runic_enchanting.emi.rune_enchanting_category");
	public static final EntryStack<ItemStack> ICON = EntryStacks.of(RE_Blocks.BLOCKS.get("rune_enchanting_table"));
	public static final CategoryIdentifier<RuneEnchantingReiDisplay> IDENTIFIER = CategoryIdentifier.of(RuneEnchantingRecipe.Type.ID);

	@Override
	public CategoryIdentifier<? extends RuneEnchantingReiDisplay> getCategoryIdentifier()
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
	public List<Widget> setupDisplay(RuneEnchantingReiDisplay display, Rectangle bounds)
	{
		List<Widget> widgets = new ArrayList<>();
		widgets.add(Widgets.createSlot(new Point(bounds.x, bounds.y)).entries(display.getInputEntries().get(0)).markInput());
		widgets.add(Widgets.createSlot(new Point(bounds.x + 36, bounds.y)).entries(display.getInputEntries().get(1)).markInput());
		widgets.add(Widgets.createSlot(new Point(bounds.x + 54, bounds.y)).entries(display.getInputEntries().get(2)).markInput());
		widgets.add(Widgets.createSlot(new Point(bounds.x + 72, bounds.y)).entries(display.getInputEntries().get(3)).markInput());
		widgets.add(Widgets.createSlot(new Point(bounds.x + 90, bounds.y)).entries(display.getInputEntries().get(4)).markInput());
		widgets.add(Widgets.createArrow(new Point(bounds.x + 110, bounds.y + 1)).disableAnimation());
		widgets.add(Widgets.createSlot(new Point(bounds.x + 136, bounds.y)).entries(display.getOutputEntries().get(0)).disableBackground().disableHighlight().markOutput());
		return widgets;
	}

	@Override
	public int getDisplayWidth(RuneEnchantingReiDisplay display)
	{
		return 154;
	}

	@Override
	public int getDisplayHeight()
	{
		return 18;
	}
}
