package amorphia.runic_enchanting.screen;

import amorphia.runic_enchanting.RunicEnchanting;
import amorphia.runic_enchanting.recipes.RuneScribingRecipe;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.List;

public class RuneScribingScreen extends HandledScreen<RuneScribingScreenHandler>
{
	public static final Identifier TEXTURE = RunicEnchanting.identify("textures/gui/rune_scribing.png");

	private static final int SCROLLBAR_OFFSET_X = 119;
	private static final int SCROLLBAR_OFFSET_Y = 14;
	private static final int SCROLLBAR_SCROLLABLE_HEIGHT = 41;
	private static final int SCROLLBAR_THUMB_WIDTH = 12;
	private static final int SCROLLBAR_THUMB_HEIGHT = 15;
	private static final int SCROLLBAR_AREA_HEIGHT = 54;

	private static final int RECIPE_LIST_COLUMNS = 4;
	private static final int RECIPE_LIST_ROWS = 3;
	private static final int RECIPE_ENTRY_WIDTH = 16;
	private static final int RECIPE_ENTRY_HEIGHT = 18;
	private static final int RECIPE_LIST_OFFSET_X = 52;
	private static final int RECIPE_LIST_OFFSET_Y = 14;

	private float scrollAmount = 0.0f;
	private boolean mouseClicked = false;
	private int scrollOffset = 0;
	private boolean canCraft = false;

	public RuneScribingScreen(RuneScribingScreenHandler handler, PlayerInventory inventory, Text title)
	{
		super(handler, inventory, title);
		handler.setContentsChangedListener(this::onInventoryChanged);
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
	{
		super.render(matrices, mouseX, mouseY, delta);
		this.drawMouseoverTooltip(matrices, mouseX, mouseY);
	}

	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY)
	{
		this.renderBackground(matrices);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		RenderSystem.setShaderTexture(0, TEXTURE);
		RenderSystem.enableBlend();

		//draw background
		this.drawTexture(matrices, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight);

		//draw scrollbar thumb
		int thumbPosition = (int) (((float) SCROLLBAR_SCROLLABLE_HEIGHT) * this.scrollAmount);
		this.drawTexture(matrices, this.x + SCROLLBAR_OFFSET_X, this.y + SCROLLBAR_OFFSET_Y + thumbPosition, this.backgroundWidth + (this.shouldScroll() ? 0 : SCROLLBAR_THUMB_WIDTH), 0, SCROLLBAR_THUMB_WIDTH, SCROLLBAR_THUMB_HEIGHT);

		final int recipeListX = this.x + RECIPE_LIST_OFFSET_X;
		final int recipeListY = this.y + RECIPE_LIST_OFFSET_Y;
		final int indexOfLastVisibleRecipe = this.scrollOffset + RECIPE_LIST_ROWS * RECIPE_LIST_COLUMNS;
		this.renderRecipeBackground(matrices, mouseX, mouseY, recipeListX, recipeListY, indexOfLastVisibleRecipe);
		this.renderRecipeIcons(recipeListX, recipeListY, indexOfLastVisibleRecipe);
	}

	@Override
	protected void drawMouseoverTooltip(MatrixStack matrices, int x, int y)
	{
		super.drawMouseoverTooltip(matrices, x, y);
		if (this.canCraft)
		{
			int listOffsetX = this.x + RECIPE_LIST_OFFSET_X;
			int listOffsetY = this.y + RECIPE_LIST_OFFSET_Y;
			int k = this.scrollOffset + RECIPE_LIST_ROWS * RECIPE_LIST_COLUMNS;
			List<RuneScribingRecipe> recipes = this.handler.getAvailableRecipes();
			for(int i = this.scrollOffset; i < k && i < recipes.size(); ++i)
			{
				int recipeIndex = i - this.scrollOffset;
				int recipeX = listOffsetX + recipeIndex % RECIPE_LIST_COLUMNS * RECIPE_ENTRY_WIDTH;
				int recipeY = listOffsetY + recipeIndex / RECIPE_LIST_COLUMNS * RECIPE_ENTRY_HEIGHT + 2;
				if(x >= recipeX && x < recipeX + RECIPE_ENTRY_WIDTH && y >= recipeY && y < recipeY + RECIPE_ENTRY_HEIGHT)
				{
					this.renderTooltip(matrices, recipes.get(i).getOutput(), x, y);
				}
			}
		}
	}

	private void renderRecipeBackground(MatrixStack matrices, int mouseX, int mouseY, int x, int y, int indexOfLastVisibleRecipe)
	{
		for(int i = this.scrollOffset; i < indexOfLastVisibleRecipe && i < this.handler.getAvailableRecipesCount(); i++)
		{
			final int recipeButtonIndex = i - this.scrollOffset;
			final int recipeButtonX = x + recipeButtonIndex % RECIPE_LIST_COLUMNS * RECIPE_ENTRY_WIDTH;
			final int recipeButtonY = y + (recipeButtonIndex / RECIPE_LIST_COLUMNS) * RECIPE_ENTRY_HEIGHT + 2;

			int n = this.backgroundHeight;
			if (i == this.handler.getSelectedRecipe())
			{
				n += RECIPE_ENTRY_HEIGHT;
			}
			else if(mouseX >= recipeButtonX && mouseY >= recipeButtonY && mouseX < recipeButtonX + RECIPE_ENTRY_WIDTH && mouseY < recipeButtonY + RECIPE_ENTRY_HEIGHT)
			{
				n += RECIPE_ENTRY_HEIGHT * 2;
			}

			//render button
			this.drawTexture(matrices, recipeButtonX, recipeButtonY - 1, 0, n, RECIPE_ENTRY_WIDTH, RECIPE_ENTRY_HEIGHT);
		}
	}

	private void renderRecipeIcons(int x, int y, int scrollOffset)
	{
		List<RuneScribingRecipe> recipes = this.handler.getAvailableRecipes();
		for(int i = this.scrollOffset; i < scrollOffset && i < recipes.size(); ++i)
		{
			int recipeButtonIndex = i - this.scrollOffset;
			int recipeButtonX = x + recipeButtonIndex % RECIPE_LIST_COLUMNS * RECIPE_ENTRY_WIDTH;
			int recipeButtonY = y + (recipeButtonIndex / RECIPE_LIST_COLUMNS) * RECIPE_ENTRY_HEIGHT + 2;
			this.client.getItemRenderer().renderInGuiWithOverrides(recipes.get(i).getOutput(), recipeButtonX, recipeButtonY);
		}
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button)
	{
		this.mouseClicked = false;
		if (this.canCraft)
		{
			int listOffsetX = this.x + RECIPE_LIST_OFFSET_X;
			int listOffsetY = this.y + RECIPE_LIST_OFFSET_Y;
			int k = this.scrollOffset + RECIPE_LIST_ROWS * RECIPE_LIST_COLUMNS;
			List<RuneScribingRecipe> recipes = this.handler.getAvailableRecipes();
			for(int i = this.scrollOffset; i < k && i < recipes.size(); ++i)
			{
				int recipeIndex = i - this.scrollOffset;
				int recipeX = listOffsetX + recipeIndex % RECIPE_LIST_COLUMNS * RECIPE_ENTRY_WIDTH;
				int recipeY = listOffsetY + recipeIndex / 4 * RECIPE_ENTRY_HEIGHT + 2;
				if(mouseX >= recipeX && mouseX < recipeX + RECIPE_ENTRY_WIDTH && mouseY >= recipeY && mouseY < recipeY + RECIPE_ENTRY_HEIGHT)
				{
					MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.ITEM_BOOK_PAGE_TURN, 1.0f));
					this.client.interactionManager.clickButton(this.handler.syncId, i);
					return true;
				}
			}

			int scrollbarx = this.x + SCROLLBAR_OFFSET_X;
			int scrollbary = this.y + SCROLLBAR_OFFSET_Y;
			if(mouseX >= (double)scrollbarx && mouseX < (double)(scrollbarx + SCROLLBAR_THUMB_WIDTH) && mouseY >= (double)scrollbary && mouseY < (double)(scrollbary + SCROLLBAR_AREA_HEIGHT))
			{
				this.mouseClicked = true;
			}
		}

		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY)
	{
		if (this.mouseClicked && this.shouldScroll())
		{
			int i = this.y + RECIPE_LIST_OFFSET_Y;
			int j = i + SCROLLBAR_AREA_HEIGHT;
			this.scrollAmount = ((float)mouseY - (float)i - 7.5f) / ((float)(j - i) - SCROLLBAR_THUMB_HEIGHT);
			this.scrollAmount = MathHelper.clamp(this.scrollAmount, 0.0f, 1.0f);
			this.scrollOffset = (int)((double)(this.scrollAmount * (float)this.getMaxScroll()) + 0.5d) * RECIPE_LIST_COLUMNS;
			return true;
		}
		else
		{
			return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
		}
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double amount)
	{
		if (this.shouldScroll())
		{
			int i = this.getMaxScroll();
			this.scrollAmount = (float)((double)this.scrollAmount - amount / (double) i);
			this.scrollAmount = MathHelper.clamp(this.scrollAmount, 0.0f, 1.0f);
			this.scrollOffset = (int)((double)(this.scrollAmount * (float)i) + 0.5d) * RECIPE_LIST_COLUMNS;
		}
		return true;
	}

	protected int getMaxScroll()
	{
		return (this.handler.getAvailableRecipesCount() + RECIPE_LIST_COLUMNS - 1) / RECIPE_LIST_COLUMNS - RECIPE_LIST_ROWS;
	}

	private boolean shouldScroll()
	{
		return this.handler.canCraft() && this.handler.getAvailableRecipesCount() > RECIPE_LIST_ROWS * RECIPE_LIST_COLUMNS;
	}

	private void onInventoryChanged()
	{
		this.canCraft = this.handler.canCraft();
		if(!this.canCraft)
		{
			this.scrollAmount = 0.0f;
			this.scrollOffset = 0;
		}
	}
}
