package amorphia.runic_enchanting.recipes;

import amorphia.runic_enchanting.RunicEnchanting;
import com.google.gson.JsonObject;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

public class RunicEnchantingShapeless extends ShapelessRecipe
{
	protected final Identifier enchantmentIdentifier;
	protected final int level;

	public RunicEnchantingShapeless(ShapelessRecipe recipe, Identifier enchantmentIdentifier, int level)
	{
		super(recipe.getId(), recipe.getGroup(), recipe.getOutput(), recipe.getIngredients());
		this.enchantmentIdentifier = enchantmentIdentifier;
		this.level = level;
	}

	@Override
	public ItemStack getOutput()
	{
		ItemStack stack = super.getOutput().copy();

		NbtList list = EnchantedBookItem.getEnchantmentNbt(stack);
		list.add(EnchantmentHelper.createNbt(enchantmentIdentifier, level));
		stack.getOrCreateNbt().put(EnchantedBookItem.STORED_ENCHANTMENTS_KEY, list);

		return stack;
	}

	@Override
	public ItemStack craft(CraftingInventory craftingInventory)
	{
		return getOutput();
	}

	public static class Type implements RecipeType<RunicEnchantingShapeless>
	{
		public static final Identifier ID = RunicEnchanting.identify("runic_enchanting_shapeless");
		public static final Type INSTANCE = new Type();

		private Type() {} // no op
	}

	public static class Serializer extends ShapelessRecipe.Serializer
	{
		public static final Serializer INSTANCE = new Serializer();

		protected Serializer() {}

		@Override
		public ShapelessRecipe read(Identifier identifier, PacketByteBuf packetByteBuf)
		{
			return new RunicEnchantingShapeless(super.read(identifier, packetByteBuf), packetByteBuf.readIdentifier(), packetByteBuf.readInt());
		}

		@Override
		public ShapelessRecipe read(Identifier identifier, JsonObject jsonObject)
		{
			return new RunicEnchantingShapeless(super.read(identifier, jsonObject),
					Identifier.tryParse(JsonHelper.getString(jsonObject, "enchantment")),
					JsonHelper.getInt(jsonObject, "level", 1));
		}
	}
}
