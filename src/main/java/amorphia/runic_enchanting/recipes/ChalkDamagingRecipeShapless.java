package amorphia.runic_enchanting.recipes;

import amorphia.runic_enchanting.RunicEnchanting;
import amorphia.runic_enchanting.items.ChalkItem;
import com.google.gson.JsonObject;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

public class ChalkDamagingRecipeShapless extends ShapelessRecipe
{
	public ChalkDamagingRecipeShapless(ShapelessRecipe recipe)
	{
		super(recipe.getId(), recipe.getGroup(), recipe.getOutput(), recipe.getIngredients());
	}

	@Override
	public DefaultedList<ItemStack> getRemainder(CraftingInventory inventory)
	{
		DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(inventory.size(), ItemStack.EMPTY);

		for(int i = 0; i < defaultedList.size(); i++)
		{
			ItemStack stack = inventory.getStack(i).copy();
			if (stack != null && !stack.isEmpty() && stack.getItem() instanceof ChalkItem)
			{
				if (stack.isDamageable())
				{
					stack.setDamage(stack.getDamage() + 1);
					if(stack.getDamage() > stack.getMaxDamage())
						stack = ItemStack.EMPTY;
				}

				defaultedList.set(i, stack);
			}
		}

		return defaultedList;
	}

	public static class Type implements RecipeType<ChalkDamagingRecipeShapless>
	{
		public static final Identifier ID = RunicEnchanting.identify("chalk_damaging_shapeless");
		public static final Type INSTANCE = new Type();

		private Type() {} //no op
	}

	public static class Serializer extends ShapelessRecipe.Serializer
	{
		public static final Serializer INSTANCE = new Serializer();

		protected Serializer() {}

		@Override
		public ShapelessRecipe read(Identifier identifier, PacketByteBuf packetByteBuf)
		{
			return new ChalkDamagingRecipeShapless(super.read(identifier, packetByteBuf));
		}

		@Override
		public ShapelessRecipe read(Identifier identifier, JsonObject jsonObject)
		{
			return new ChalkDamagingRecipeShapless(super.read(identifier, jsonObject));
		}
	}
}
