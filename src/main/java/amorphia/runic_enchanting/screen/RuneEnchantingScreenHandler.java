package amorphia.runic_enchanting.screen;

import amorphia.runic_enchanting.blocks.RE_Blocks;
import amorphia.runic_enchanting.blocks.RunicEnchantingTable;
import amorphia.runic_enchanting.items.RunePageItem;
import amorphia.runic_enchanting.recipes.RuneEnchantingRecipe;
import com.google.common.collect.Lists;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.Property;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Comparator;
import java.util.List;

public class RuneEnchantingScreenHandler extends ScreenHandler
{
	private final ScreenHandlerContext context;
	private final Property selectedRecipe;
	private final World world;
	private final PlayerEntity player;

	private List<RuneEnchantingRecipe> availableRecipes;

	Runnable contentsChangedListener;
	long lastTakeTime;

	final Slot bookSlot;
	final Slot lapisSlot;
	final Slot targetSlot;
	final Slot primarySlot;
	final Slot secondarySlot;
	final Slot extraSlot;
	final Slot outputSlot;

	public final Inventory input;
	final CraftingResultInventory output;

	ItemStack bookStack = ItemStack.EMPTY;
	ItemStack lapisStack = ItemStack.EMPTY;
	ItemStack targetStack = ItemStack.EMPTY;
	ItemStack primaryStack = ItemStack.EMPTY;
	ItemStack secondaryStack = ItemStack.EMPTY;
	ItemStack extraStack = ItemStack.EMPTY;

	public RuneEnchantingScreenHandler(int syncID, PlayerInventory playerInventory)
	{
		this(syncID, playerInventory, ScreenHandlerContext.EMPTY);
	}

	public RuneEnchantingScreenHandler(int syncID, PlayerInventory playerInventory, ScreenHandlerContext context)
	{
		super(RE_Screens.RUNE_ENCHANTING_SCREEN_HANDLER_TYPE, syncID);
		this.context = context;
		this.world = playerInventory.player.world;
		this.player = playerInventory.player;
		this.selectedRecipe = Property.create();
		this.contentsChangedListener = () -> {};
		this.availableRecipes = Lists.newLinkedList();

		this.input = new SimpleInventory(6){
			@Override
			public void markDirty()
			{
				RuneEnchantingScreenHandler.this.onContentChanged(this);
				RuneEnchantingScreenHandler.this.contentsChangedListener.run();
				super.markDirty();
			}
		};
		this.output = new CraftingResultInventory();

		this.bookSlot = this.addSlot(new Slot(this.input, 0, 8, 19));
		this.lapisSlot = this.addSlot(new Slot(this.input, 1, 28, 19));
		this.targetSlot = this.addSlot(new Slot(this.input, 2, 52, 19));
		this.primarySlot = this.addSlot(new Slot(this.input, 3, 73, 19));
		this.secondarySlot = this.addSlot(new Slot(this.input, 4, 94, 19));
		this.extraSlot = this.addSlot(new Slot(this.input, 5, 115, 19));
		this.outputSlot = this.addSlot(new Slot(this.output, 6, 145, 19){
			@Override
			public boolean canInsert(ItemStack stack)
			{
				return false;
			}

			@Override
			public void onTakeItem(PlayerEntity player, ItemStack stack)
			{
				stack.onCraft(player.world, player, stack.getCount());
				RuneEnchantingScreenHandler.this.output.unlockLastRecipe(player);

				if (isInBounds(getSelectedRecipe()) && getAvailableRecipes().get(getSelectedRecipe()) != null)
				{
					getAvailableRecipes().get(getSelectedRecipe()).onCraft(RuneEnchantingScreenHandler.this.input, RuneEnchantingScreenHandler.this.player);
				}

				RuneEnchantingScreenHandler.this.onContentChanged(RuneEnchantingScreenHandler.this.input);
				RuneEnchantingScreenHandler.this.context.run((world1, blockPos) -> {
					long l = world1.getTime();
					if(RuneEnchantingScreenHandler.this.lastTakeTime != l)
					{
						//TODO: custom sound event
						world1.playSound(null, blockPos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0f, 1.0f);
						RuneEnchantingScreenHandler.this.lastTakeTime = l;
					}
				});

				super.onTakeItem(player, stack);
			}
		});

		final int inventoryStartX = 8;
		final int inventoryStartY = 117;
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				this.addSlot(new Slot(playerInventory, j + i * 9 + 9, inventoryStartX + j * 18, inventoryStartY + i * 18));
			}
		}

		final int barStartX = 8;
		final int barStartY = 175;
		for(int i = 0; i < 9; i++)
		{
			this.addSlot(new Slot(playerInventory, i, barStartX + i * 18, barStartY));
		}

		this.addProperty(this.selectedRecipe);
	}

	@Override
	public ItemStack transferSlot(PlayerEntity player, int index)
	{
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot.hasStack())
		{
			ItemStack slotStack = slot.getStack();
			Item slotItem = slotStack.getItem();
			itemStack = slotStack.copy();

			//output to inventory
			if (index == outputSlot.getIndex())
			{
				slotItem.onCraft(slotStack, player.world, player);
				if (!this.insertItem(slotStack, outputSlot.getIndex() + 1, slots.size(), true))
				{
					return ItemStack.EMPTY;
				}
				slot.onQuickTransfer(slotStack, itemStack);
			}
			//any other slot to inventory
			else if (index < outputSlot.getIndex())
			{
				if(!this.insertItem(slotStack, outputSlot.getIndex() + 1, slots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			}

			//inventory to book slot
			else if(slotStack.isOf(Items.BOOK))
			{
				if(!this.insertItem(slotStack, bookSlot.getIndex(), bookSlot.getIndex() + 1, false))
					return ItemStack.EMPTY;
			}
			//inventory to lapis slot
			else if (slotStack.isOf(Items.LAPIS_LAZULI))
			{
				if(!this.insertItem(slotStack, lapisSlot.getIndex(), lapisSlot.getIndex() + 1, false))
					return ItemStack.EMPTY;
			}
			//inventory to page slots
			else if (slotStack.getItem() instanceof RunePageItem)
			{
				if (!this.insertItem(slotStack, targetSlot.getIndex(), extraSlot.getIndex() + 1, false))
					return ItemStack.EMPTY;
			}

			//inventory to hot bar
			else if(index < slots.size() - 9)
			{
				if (!this.insertItem(slotStack, slots.size() - 9, slots.size(), false))
					return ItemStack.EMPTY;
			}
			//hot bar to inventory
			else if(!this.insertItem(slotStack, outputSlot.getIndex() + 1, this.slots.size() - 9, false))
				return ItemStack.EMPTY;

			if(slotStack.isEmpty())
				slot.setStack(ItemStack.EMPTY);

			slot.markDirty();

			if(slotStack.getCount() == itemStack.getCount())
				return ItemStack.EMPTY;

			slot.onTakeItem(player, slotStack);
			this.sendContentUpdates();
		}

		return itemStack;
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
	public void onContentChanged(Inventory inventory)
	{
		if(stackChanged(bookStack, bookSlot) || stackChanged(lapisStack, lapisSlot) || stackChanged(targetStack, targetSlot) || stackChanged(primaryStack, primarySlot) || stackChanged(secondaryStack, secondarySlot) || stackChanged(extraStack, extraSlot))
		{
			updateInput(inventory);
		}

		if (this.getSelectedRecipe() >= 0 && lapisStack.getCount() != lapisSlot.getStack().getCount())
		{
			populateResult();
		}

		updateTrackedStacks();
	}

	private void updateInput(Inventory inventory)
	{
		this.availableRecipes.clear();
		this.selectedRecipe.set(-1);
		this.outputSlot.setStack(ItemStack.EMPTY);
		this.contentsChangedListener.run();

		if (!inventory.isEmpty())
		{
			this.world.getRecipeManager().getAllMatches(RuneEnchantingRecipe.Type.INSTANCE, inventory, this.world).forEach(recipe -> {
				Enchantment enchantment = Registry.ENCHANTMENT.get(recipe.getEnchantmentIdentifier());

				if(enchantment == null)
					return;

				if (!recipe.shouldGenerateAdditionalLeveledRecipes())
				{
					this.availableRecipes.add(recipe);
					return;
				}

				for(int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); i++)
				{
					this.availableRecipes.add(new RuneEnchantingRecipe(recipe, i));
				}
			});
			this.availableRecipes.sort(Comparator.comparingInt(RuneEnchantingRecipe::getEnchantmentLevel));
		}

		this.sendContentUpdates();
	}

	private void populateResult()
	{
		if (!this.availableRecipes.isEmpty() && this.isInBounds(this.selectedRecipe.get()) && this.hasEnoughLapis(this.selectedRecipe.get()) && this.hasEnoughExp(this.selectedRecipe.get()))
		{
			RuneEnchantingRecipe recipe = this.availableRecipes.get(this.selectedRecipe.get());
			this.output.setLastRecipe(recipe);
			this.outputSlot.setStack(recipe.craft(this.input));
		}
		else
		{
			this.outputSlot.setStack(ItemStack.EMPTY);
		}

		this.sendContentUpdates();
	}

	private boolean stackChanged(ItemStack oldStack, Slot slot)
	{
		return !oldStack.isOf(slot.getStack().getItem());
	}

	private void updateTrackedStacks()
	{
		this.bookStack = this.bookSlot.getStack().copy();
		this.lapisStack = this.lapisSlot.getStack().copy();
		this.targetStack = this.targetSlot.getStack().copy();
		this.primaryStack = this.primarySlot.getStack().copy();
		this.secondaryStack = this.secondarySlot.getStack().copy();
		this.extraStack = this.extraSlot.getStack().copy();
	}

	@Override
	public boolean canUse(PlayerEntity player)
	{
		return RuneEnchantingScreenHandler.canUse(this.context, player, RE_Blocks.BLOCKS.get("rune_enchanting_table"));
	}

	public void setContentsChangedListener(Runnable listener)
	{
		this.contentsChangedListener = listener;
	}

	private boolean isInBounds(int id)
	{
		return id >= 0 && id < this.availableRecipes.size();
	}

	public boolean hasEnoughLapis(int id)
	{
		return isInBounds(id) && this.availableRecipes.get(id).hasEnoughLapis(this.input);
	}

	public boolean hasEnoughExp(int id)
	{
		return isInBounds(id) && this.availableRecipes.get(id).hasEnoughExp(this.player) || this.player.isCreative();
	}

	public int getSelectedRecipe()
	{
		return this.selectedRecipe.get();
	}

	public List<RuneEnchantingRecipe> getAvailableRecipes()
	{
		return this.availableRecipes;
	}

	public int getAvailableRecipesCount()
	{
		return availableRecipes.size();
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
