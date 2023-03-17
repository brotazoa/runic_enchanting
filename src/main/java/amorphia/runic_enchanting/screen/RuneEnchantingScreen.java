package amorphia.runic_enchanting.screen;

import amorphia.runic_enchanting.RunicEnchanting;
import amorphia.runic_enchanting.recipes.RuneEnchantingRecipe;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;

import java.util.List;

public class RuneEnchantingScreen extends HandledScreen<RuneEnchantingScreenHandler>
{
	public static final Identifier TEXTURE = RunicEnchanting.identify("textures/gui/rune_enchanting_v2.png");
	public static final Text NOT_ENOUGH_LAPIS = Text.translatable("runic_enchanting.screen.rune_enchanting.not_enough_lapis");
	public static final Text NOT_ENOUGH_EXP = Text.translatable("runic_enchanting.screen.rune_enchanting.not_enough_exp");

	private static final int BACKGROUND_WIDTH = 176;
	private static final int BACKGROUND_HEIGHT = 199;

	private static final int ARROW_OFFSET_X = 200;
	private static final int ARROW_OFFSET_Y = 0;
	private static final int ARROW_WIDTH = 12;
	private static final int ARROW_HEIGHT = 12;
	private static final int PLUS_OFFSET_X = 225;
	private static final int PLUS_OFFSET_Y = 0;
	private static final int PLUS_WIDTH = 12;
	private static final int PLUS_HEIGHT = 12;
	private static final int LAPIS_OFFSET_X = 176;
	private static final int LAPIS_OFFSET_Y = 16;
	private static final int LAPIS_WIDTH = 16;
	private static final int LAPIS_HEIGHT = 16;
	private static final int EXP_OFFSET_X = 176;
	private static final int EXP_OFFSET_Y = 32;
	private static final int EXP_WIDTH = 16;
	private static final int EXP_HEIGHT = 16;

	private static final int SCROLLBAR_OFFSET_X = 137;
	private static final int SCROLLBAR_OFFSET_Y = 45;
	private static final int SCROLLBAR_SCROLLABLE_HEIGHT = 43;
	private static final int SCROLLBAR_THUMB_WIDTH = 12;
	private static final int SCROLLBAR_THUMB_HEIGHT = 15;
	private static final int SCROLLBAR_AREA_HEIGHT = 58;

	private static final int RECIPE_LIST_COLUMNS = 1;
	private static final int RECIPE_LIST_ROWS = 3;
	private static final int RECIPE_ENTRY_WIDTH = 108;
	private static final int RECIPE_ENTRY_HEIGHT = 19;
	private static final int RECIPE_LIST_OFFSET_X = 26;
	private static final int RECIPE_LIST_OFFSET_Y = 44;

	private static final int RECIPE_BUTTON_EXP_OFFSET_X = 2;
	private static final int RECIPE_BUTTON_PLUS_OFFSET_X = 22;
	private static final int RECIPE_BUTTON_LAPIS_OFFSET_X = 37;
	private static final int RECIPE_BUTTON_ARROW_OFFSET_X = 62;
	private static final int RECIPE_BUTTON_OUTPUT_OFFSET_X = 81;

	private float scrollAmount = 0.0f;
	private boolean mouseClicked = false;
	private int scrollOffset = 0;
	private boolean canCraft = false;

	public RuneEnchantingScreen(RuneEnchantingScreenHandler handler, PlayerInventory inventory, Text title)
	{
		super(handler, inventory, title);
		handler.setContentsChangedListener(this::onInventoryChanged);
		this.backgroundWidth = BACKGROUND_WIDTH;
		this.backgroundHeight = BACKGROUND_HEIGHT;
		this.playerInventoryTitleY = this.backgroundHeight - 94;
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
		this.renderRecipeIcons(matrices, recipeListX, recipeListY, indexOfLastVisibleRecipe);
	}

	private void renderRecipeBackground(MatrixStack matrices, int mouseX, int mouseY, int recipeListX, int recipeListY, int indexOfLastVisibleRecipe)
	{
		for(int i = this.scrollOffset; i < indexOfLastVisibleRecipe && i < this.handler.getAvailableRecipesCount(); i++)
		{
			final int recipeButtonIndex = i - this.scrollOffset;
			final int recipeButtonX = recipeListX + recipeButtonIndex % RECIPE_LIST_COLUMNS * RECIPE_ENTRY_WIDTH;
			final int recipeButtonY = recipeListY + (recipeButtonIndex / RECIPE_LIST_COLUMNS) * RECIPE_ENTRY_HEIGHT + 2;

			int recipeButtonBackgroundV = this.backgroundHeight;
			if (i == this.handler.getSelectedRecipe())
			{
				recipeButtonBackgroundV += RECIPE_ENTRY_HEIGHT;
			}
			else if (mouseX >= recipeButtonX && mouseY >= recipeButtonY && mouseX < recipeButtonX + RECIPE_ENTRY_WIDTH && mouseY < recipeButtonY + RECIPE_ENTRY_HEIGHT)
			{
				recipeButtonBackgroundV += RECIPE_ENTRY_HEIGHT * 2;
			}
			this.drawTexture(matrices, recipeButtonX, recipeButtonY - 1, 0, recipeButtonBackgroundV, RECIPE_ENTRY_WIDTH, RECIPE_ENTRY_HEIGHT);

			drawTexture(matrices, recipeButtonX + RECIPE_BUTTON_PLUS_OFFSET_X, recipeButtonY, 100, PLUS_OFFSET_X, PLUS_OFFSET_Y, PLUS_WIDTH, PLUS_HEIGHT, 256, 256);

			final boolean enoughExp = this.handler.hasEnoughExp(i);
			final int exp_u = EXP_OFFSET_X + (enoughExp ? EXP_WIDTH : 0);
			drawTexture(matrices, recipeButtonX + RECIPE_BUTTON_EXP_OFFSET_X, recipeButtonY - 1, 100, exp_u, EXP_OFFSET_Y, EXP_WIDTH, EXP_HEIGHT, 256, 256);

			final boolean enoughLapis = this.handler.hasEnoughLapis(i);
			final int lapis_u = LAPIS_OFFSET_X + (enoughLapis ? LAPIS_WIDTH : 0);
			drawTexture(matrices, recipeButtonX + RECIPE_BUTTON_LAPIS_OFFSET_X, recipeButtonY - 1, 101, lapis_u, LAPIS_OFFSET_Y, LAPIS_WIDTH, LAPIS_HEIGHT, 256, 256);


			final boolean canAfford = enoughExp && enoughLapis;
			final int arrow_u = ARROW_OFFSET_X + (canAfford ? 0 : ARROW_WIDTH);
			drawTexture(matrices, recipeButtonX + RECIPE_BUTTON_ARROW_OFFSET_X, recipeButtonY, 102, arrow_u, ARROW_OFFSET_Y, ARROW_WIDTH, ARROW_HEIGHT, 256, 256);
		}
	}

	private void renderRecipeIcons(MatrixStack matrices, int recipeListX, int recipeListY, int indexOfLastVisibleRecipe)
	{
		List<RuneEnchantingRecipe> recipes = this.handler.getAvailableRecipes();
		for(int i = this.scrollOffset; i < indexOfLastVisibleRecipe && i < recipes.size(); i++)
		{
			final int recipeButtonIndex = i - this.scrollOffset;
			final int recipeButtonX = recipeListX + recipeButtonIndex % RECIPE_LIST_COLUMNS * RECIPE_ENTRY_WIDTH;
			final int recipeButtonY = recipeListY + (recipeButtonIndex / RECIPE_LIST_COLUMNS) * RECIPE_ENTRY_HEIGHT + 2;

			final ItemStack outputStack = recipes.get(i).getOutput();
			this.client.getItemRenderer().renderInGuiWithOverrides(outputStack, recipeButtonX + RECIPE_BUTTON_OUTPUT_OFFSET_X, recipeButtonY);
			this.client.getItemRenderer().renderGuiItemOverlay(this.textRenderer, outputStack, recipeButtonX + RECIPE_BUTTON_OUTPUT_OFFSET_X, recipeButtonY);

			final ItemStack lapisStack = new ItemStack(Items.LAPIS_LAZULI);
			lapisStack.setCount(recipes.get(i).getLapisCost());
			this.client.getItemRenderer().renderGuiItemOverlay(this.textRenderer, lapisStack, recipeButtonX + RECIPE_BUTTON_LAPIS_OFFSET_X, recipeButtonY, lapisStack.getCount() == 1 ? "1" : null);

			final ItemStack expStack = new ItemStack(Items.STICK);
			expStack.setCount(recipes.get(i).getExpCost());
			this.client.getItemRenderer().renderGuiItemOverlay(this.textRenderer, expStack, recipeButtonX + RECIPE_BUTTON_EXP_OFFSET_X, recipeButtonY, expStack.getCount() == 1 ? "1" : null);
		}
	}

	@Override
	protected void drawMouseoverTooltip(MatrixStack matrices, int x, int y)
	{
		super.drawMouseoverTooltip(matrices, x, y);
		if (this.handler.canCraft())
		{
			final int recipeListX = this.x + RECIPE_LIST_OFFSET_X;
			final int recipeListY = this.y + RECIPE_LIST_OFFSET_Y;
			final int indexOfLastVisibleRecipe = this.scrollOffset + RECIPE_LIST_ROWS * RECIPE_LIST_COLUMNS;
			List<RuneEnchantingRecipe> recipes = this.handler.getAvailableRecipes();
			for(int i = this.scrollOffset; i < indexOfLastVisibleRecipe && i < recipes.size(); i++)
			{
				final int recipeIndex = i - this.scrollOffset;
				final int recipeX = recipeListX + recipeIndex % RECIPE_LIST_COLUMNS * RECIPE_ENTRY_WIDTH;
				final int recipeY = recipeListY + recipeIndex / RECIPE_LIST_COLUMNS * RECIPE_ENTRY_HEIGHT + 2;
				if (x >= recipeX && x < recipeX + RECIPE_ENTRY_WIDTH && y >= recipeY && y < recipeY + RECIPE_ENTRY_HEIGHT)
				{
					boolean overOutput = x >= recipeX + RECIPE_BUTTON_OUTPUT_OFFSET_X && x < recipeX + RECIPE_BUTTON_OUTPUT_OFFSET_X + 16;
					boolean overExp = x >= recipeX + RECIPE_BUTTON_EXP_OFFSET_X && x < recipeX + RECIPE_BUTTON_EXP_OFFSET_X + EXP_WIDTH;
					boolean overLapis = x >= recipeX + RECIPE_BUTTON_LAPIS_OFFSET_X && x < recipeX + RECIPE_BUTTON_LAPIS_OFFSET_X + LAPIS_WIDTH;
					final ItemStack outputStack = recipes.get(i).getOutput();

					if (overOutput)
					{
						this.renderTooltip(matrices, outputStack, x, y);
					}
					else if (overLapis && !this.handler.hasEnoughLapis(i))
					{
						this.renderTooltip(matrices, NOT_ENOUGH_LAPIS, x, y);
					}
					else if (overExp && !this.handler.hasEnoughExp(i))
					{
						this.renderTooltip(matrices, NOT_ENOUGH_EXP, x, y);
					}
					else if (!this.handler.hasEnoughExp(i))
					{
						this.renderTooltip(matrices, NOT_ENOUGH_EXP, x, y);
					}
					else if(!this.handler.hasEnoughLapis(i))
					{
						this.renderTooltip(matrices, NOT_ENOUGH_LAPIS, x, y);
					}
					else
					{
						this.renderTooltip(matrices, outputStack, x, y);
					}
				}
			}
		}
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button)
	{
		this.mouseClicked = false;
		if (this.canCraft)
		{
			final int recipeListOffsetX = this.x + RECIPE_LIST_OFFSET_X;
			final int recipeListOffsetY = this.y + RECIPE_LIST_OFFSET_Y;
			final int indexOfLastVisibleRecipe = this.scrollOffset + RECIPE_LIST_COLUMNS * RECIPE_LIST_ROWS;
			List<RuneEnchantingRecipe> recipes = this.handler.getAvailableRecipes();
			for(int i = this.scrollOffset; i < indexOfLastVisibleRecipe && i < recipes.size(); i++)
			{
				final int recipeButtonIndex = i - this.scrollOffset;
				final int recipeButtonX = recipeListOffsetX + recipeButtonIndex % RECIPE_LIST_COLUMNS * RECIPE_LIST_ROWS;
				final int recipeButtonY = recipeListOffsetY + (recipeButtonIndex / RECIPE_LIST_COLUMNS) * RECIPE_ENTRY_HEIGHT + 2;
				if (mouseX >= recipeButtonX && mouseX < recipeButtonX + RECIPE_ENTRY_WIDTH && mouseY >= recipeButtonY && mouseY < recipeButtonY + RECIPE_ENTRY_HEIGHT)
				{
					MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.ITEM_BOOK_PAGE_TURN, 1.0f));
					this.client.interactionManager.clickButton(this.handler.syncId, i);
					return true;
				}
			}

			final int scrollBarX = this.x + SCROLLBAR_OFFSET_X;
			final int scollBarY = this.y + SCROLLBAR_OFFSET_Y;
			if (mouseX >= scrollBarX && mouseX < scrollBarX + SCROLLBAR_THUMB_WIDTH && mouseY >= scollBarY && mouseY < scollBarY + SCROLLBAR_AREA_HEIGHT)
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
			this.scrollOffset = (int)((double)(this.scrollAmount * (float)this.getMaxScroll()) + 0.5d);
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
			this.scrollOffset = (int)((double)(this.scrollAmount * (float)i) + 0.5d);
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
		if (!this.canCraft)
		{
			this.scrollAmount = 0.0f;
			this.scrollOffset = 0;
		}
	}
}
