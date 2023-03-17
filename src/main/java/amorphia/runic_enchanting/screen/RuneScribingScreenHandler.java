package amorphia.runic_enchanting.screen;

import amorphia.runic_enchanting.Runes;
import amorphia.runic_enchanting.blocks.RE_Blocks;
import amorphia.runic_enchanting.blocks.RuneScribingTable;
import amorphia.runic_enchanting.recipes.RuneScribingRecipe;
import com.google.common.collect.Lists;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.Property;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

import java.util.List;

public class RuneScribingScreenHandler extends ScreenHandler
{
	private final ScreenHandlerContext context;
	private final Property selectedRecipe;
	private final World world;

	private final int[] availableRunesAsIntArray = new int[Runes.VALUES_CACHE.length];

	private List<RuneScribingRecipe> allRecipes;
	private final List<RuneScribingRecipe> availableRecipes;
	private ItemStack inputStack;

	Runnable contentsChangedListener;
	long lastTakeTime;

	final Slot inputSlot;
	final Slot outputSlot;

	public final Inventory input;
	final CraftingResultInventory output;

	public RuneScribingScreenHandler(int syncID, PlayerInventory playerInventory)
	{
		this(syncID, playerInventory, ScreenHandlerContext.EMPTY);
	}

	public RuneScribingScreenHandler(int syncID, PlayerInventory playerInventory, ScreenHandlerContext context)
	{
		super(RE_Screens.RUNE_SCRIBING_SCREEN_HANDLER_TYPE, syncID);
		this.context = context;
		this.world = playerInventory.player.world;
		this.selectedRecipe = Property.create();
		this.allRecipes = Lists.newArrayList(); //TODO: recipe lookup here
		this.availableRecipes = Lists.newArrayList();
		this.inputStack = ItemStack.EMPTY;
		this.contentsChangedListener = () -> {};

		this.context.run((world1, blockPos) -> {

			final List<Runes> availableRunes = RuneScribingTable.getAccessibleRunes(world1, blockPos);
			for(int i = 0; i < RuneScribingScreenHandler.this.availableRunesAsIntArray.length && i < Runes.VALUES_CACHE.length; i++)
			{
				Runes rune = Runes.VALUES_CACHE[i];
				final boolean available = availableRunes.contains(rune);
				RuneScribingScreenHandler.this.availableRunesAsIntArray[i] = available ? 1 : 0;
			}
		});

		this.input = new SimpleInventory(1){
			@Override
			public void markDirty()
			{
				RuneScribingScreenHandler.this.onContentChanged(input);
				RuneScribingScreenHandler.this.contentsChangedListener.run();
				super.markDirty();
			}
		};
		this.output = new CraftingResultInventory();

		this.inputSlot = this.addSlot(new Slot(this.input, 0, 20, 33));
		this.outputSlot = this.addSlot(new Slot(this.output, 1, 143, 33){
			@Override
			public boolean canInsert(ItemStack stack)
			{
				return false;
			}

			@Override
			public void onTakeItem(PlayerEntity player, ItemStack stack)
			{
				stack.onCraft(player.world, player, stack.getCount());
				RuneScribingScreenHandler.this.output.unlockLastRecipe(player);

				RuneScribingScreenHandler.this.inputSlot.takeStack(1);
				RuneScribingScreenHandler.this.onContentChanged(RuneScribingScreenHandler.this.input);
				RuneScribingScreenHandler.this.populateResult();

				RuneScribingScreenHandler.this.context.run((world1, blockPos) -> {
					long l = world1.getTime();
					if (RuneScribingScreenHandler.this.lastTakeTime != l)
					{
						//TODO: custom sound event
						world1.playSound(null, blockPos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0f, 1.0f);
						RuneScribingScreenHandler.this.lastTakeTime = l;
					}
				});

				super.onTakeItem(player, stack);
			}
		});

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		for (int i = 0; i < 9; ++i) {
			this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
		}

		this.addProperty(this.selectedRecipe);

		for(int i = 0; i < this.availableRunesAsIntArray.length; i++)
		{
			this.addProperty(Property.create(this.availableRunesAsIntArray, i));
		}

		this.updateInput(this.input);
	}

	public void setContentsChangedListener(Runnable listener)
	{
		this.contentsChangedListener = listener;
	}

	private boolean isInBounds(int id)
	{
		return id >= 0 && id < this.availableRecipes.size();
	}

	@Override
	public void onContentChanged(Inventory inventory)
	{
		ItemStack inputItemStack = this.inputSlot.getStack();
		if (!inputItemStack.isOf(this.inputStack.getItem()))
		{
			this.inputStack = inputItemStack.copy();
			this.updateInput(inventory);
		}
	}

	void updateInput(Inventory inventory)
	{
		this.allRecipes.clear();
		this.availableRecipes.clear();
		this.selectedRecipe.set(-1);
		this.outputSlot.setStack(ItemStack.EMPTY);

		this.contentsChangedListener.run();

		if (!inventory.isEmpty())
		{
			this.allRecipes = this.world.getRecipeManager().getAllMatches(RuneScribingRecipe.Type.INSTANCE, inventory, this.world);
			this.allRecipes.forEach(recipe -> {
				Runes rune = recipe.getRequiredRune();
				final boolean available = availableRunesAsIntArray[rune.ordinal()] > 0;
				if(available) availableRecipes.add(recipe);
			});
		}

		this.sendContentUpdates();
	}

	void populateResult()
	{
		if(!this.availableRecipes.isEmpty() && this.isInBounds(this.selectedRecipe.get()))
		{
			RuneScribingRecipe recipe = this.availableRecipes.get(this.selectedRecipe.get());
			this.output.setLastRecipe(recipe);
			this.outputSlot.setStack(recipe.craft(this.input));
		}
		else
		{
			this.outputSlot.setStack(ItemStack.EMPTY);
		}
		this.sendContentUpdates();
	}

	@Override
	public boolean onButtonClick(PlayerEntity player, int id)
	{
		if (this.isInBounds(id))
		{
			this.selectedRecipe.set(id);
			this.populateResult();
		}
		return true;
	}

	@Override
	public ItemStack transferSlot(PlayerEntity player, int index) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasStack()) {
			ItemStack itemStack2 = slot.getStack();
			Item item = itemStack2.getItem();
			itemStack = itemStack2.copy();
			if (index == 1) {
				item.onCraft(itemStack2, player.world, player);
				if (!this.insertItem(itemStack2, 2, 38, true)) {
					return ItemStack.EMPTY;
				}
				slot.onQuickTransfer(itemStack2, itemStack);
			} else if (index == 0 ? !this.insertItem(itemStack2, 2, 38, false) : (this.world.getRecipeManager().getFirstMatch(RuneScribingRecipe.Type.INSTANCE, new SimpleInventory(itemStack2), this.world).isPresent() ? !this.insertItem(itemStack2, 0, 1, false) : (index >= 2 && index < 29 ? !this.insertItem(itemStack2, 29, 38, false) : index >= 29 && index < 38 && !this.insertItem(itemStack2, 2, 29, false)))) {
				return ItemStack.EMPTY;
			}
			if (itemStack2.isEmpty()) {
				slot.setStack(ItemStack.EMPTY);
			}
			slot.markDirty();
			if (itemStack2.getCount() == itemStack.getCount()) {
				return ItemStack.EMPTY;
			}
			slot.onTakeItem(player, itemStack2);
			this.sendContentUpdates();
		}
		return itemStack;
	}

	@Override
	public boolean canUse(PlayerEntity player)
	{
		return RuneScribingScreenHandler.canUse(this.context, player, RE_Blocks.BLOCKS.get("rune_scribing_table"));
	}

	public int getSelectedRecipe()
	{
		return this.selectedRecipe.get();
	}

	public List<RuneScribingRecipe> getAvailableRecipes()
	{
		return this.availableRecipes;
	}

	public int getAvailableRecipesCount()
	{
		return this.getAvailableRecipes().size();
	}

	public boolean canCraft()
	{
		return !this.availableRecipes.isEmpty();
	}

	@Override
	public void close(PlayerEntity player)
	{
		super.close(player);
		this.output.removeStack(1);
		this.context.run((world1, blockPos) -> this.dropInventory(player, this.input));
	}
}
