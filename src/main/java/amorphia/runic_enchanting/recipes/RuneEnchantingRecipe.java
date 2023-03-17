package amorphia.runic_enchanting.recipes;

import amorphia.runic_enchanting.RunicEnchanting;
import com.google.gson.JsonObject;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class RuneEnchantingRecipe implements Recipe<Inventory>
{
	protected final Identifier identifier;

	protected final Ingredient book;
	protected final Ingredient target;
	protected final Ingredient primary;
	protected final Ingredient secondary;
	protected final Ingredient extra;
	protected final ItemStack output;

	protected final int lapisCost;
	protected final int expCost;
	protected final int level;

	protected final Identifier enchantmentIdentifier;

	public RuneEnchantingRecipe(Identifier identifier, Ingredient book, Ingredient target, Ingredient primary, Ingredient secondary, Ingredient extra, ItemStack output, int lapisCost, int expCost, Identifier enchantmentIdentifier, int level)
	{
		this.identifier = identifier;
		this.book = book;
		this.target = target;
		this.primary = primary;
		this.secondary = secondary;
		this.extra = extra;
		this.output = output;
		this.lapisCost = lapisCost;
		this.expCost = expCost;
		this.level = level;
		this.enchantmentIdentifier = enchantmentIdentifier;
	}

	@Override
	public DefaultedList<Ingredient> getIngredients()
	{
		return DefaultedList.copyOf(Ingredient.EMPTY, book, target, primary, secondary, extra);
	}

	@Override
	public boolean matches(Inventory inventory, World world)
	{
		return this.book.test(inventory.getStack(0)) &&
				this.target.test(inventory.getStack(2)) &&
				this.primary.test(inventory.getStack(3)) &&
				this.secondary.test(inventory.getStack(4)) &&
				this.extra.test(inventory.getStack(5));
	}

	@Override
	public ItemStack getOutput()
	{
		ItemStack stack = output.copy();

//		NbtList list = EnchantedBookItem.getEnchantmentNbt(stack);
//		list.add(EnchantmentHelper.createNbt(enchantmentIdentifier, level));
//		stack.getOrCreateNbt().put(EnchantedBookItem.STORED_ENCHANTMENTS_KEY, list);

		Enchantment enchantment = Registry.ENCHANTMENT.get(enchantmentIdentifier);
		if (enchantment != null && stack.getItem() instanceof EnchantedBookItem)
		{
			EnchantedBookItem.addEnchantment(stack, new EnchantmentLevelEntry(enchantment, level));
		}

		return stack;
	}

	@Override
	public ItemStack craft(Inventory inventory)
	{
		return getOutput();
	}

	public void onCraft(Inventory inventory, PlayerEntity player)
	{
		if(!book.isEmpty())
			inventory.getStack(0).decrement(1);

		inventory.getStack(1).decrement(lapisCost);

		if(!target.isEmpty())
			inventory.getStack(2).decrement(1);

		if(!primary.isEmpty())
			inventory.getStack(3).decrement(1);

		if(!secondary.isEmpty())
			inventory.getStack(4).decrement(1);

		if(!extra.isEmpty())
			inventory.getStack(5).decrement(1);

		if(!player.isCreative())
			player.addExperienceLevels(-expCost);
	}

	@Override
	public boolean fits(int width, int height)
	{
		return width * height >= 6;
	}

	@Override
	public Identifier getId()
	{
		return identifier;
	}

	public boolean hasEnoughLapis(Inventory inventory)
	{
		return inventory.getStack(1).isOf(Items.LAPIS_LAZULI) && inventory.getStack(1).getCount() >= lapisCost;
	}

	public boolean hasEnoughExp(PlayerEntity player)
	{
		return player.experienceLevel >= expCost;
	}

	public int getLapisCost()
	{
		return lapisCost;
	}

	public int getExpCost()
	{
		return expCost;
	}

	public Identifier getEnchantmentIdentifier()
	{
		return this.enchantmentIdentifier;
	}

	public int getEnchantmentLevel()
	{
		return this.level;
	}

	@Override
	public boolean isIgnoredInRecipeBook()
	{
		return true;
	}

	@Override
	public RecipeSerializer<?> getSerializer()
	{
		return Serializer.INSTANCE;
	}

	@Override
	public RecipeType<?> getType()
	{
		return Type.INSTANCE;
	}

	public static class Type implements RecipeType<RuneEnchantingRecipe>
	{
		public static final Identifier ID = RunicEnchanting.identify("rune_enchanting");
		public static final Type INSTANCE = new Type();

		private Type() {} // no op
	}

	public static class Serializer implements RecipeSerializer<RuneEnchantingRecipe>
	{
		public static Serializer INSTANCE = new Serializer();

		protected Serializer() {}

		@Override
		public RuneEnchantingRecipe read(Identifier id, JsonObject json)
		{
			final Ingredient book = Ingredient.fromJson(JsonHelper.getObject(json, "book"));

			final Ingredient target = JsonHelper.hasJsonObject(json, "target") ? Ingredient.fromJson(JsonHelper.getObject(json, "target")) : Ingredient.EMPTY;
			final Ingredient primary = JsonHelper.hasJsonObject(json, "primary") ? Ingredient.fromJson(JsonHelper.getObject(json, "primary")) : Ingredient.EMPTY;
			final Ingredient secondary = JsonHelper.hasJsonObject(json, "secondary") ? Ingredient.fromJson(JsonHelper.getObject(json, "secondary")) : Ingredient.EMPTY;
			final Ingredient extra = JsonHelper.hasJsonObject(json, "extra") ? Ingredient.fromJson(JsonHelper.getObject(json, "extra")) : Ingredient.EMPTY;

			final ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "result"));

			final Identifier enchantmentIdentifier = Identifier.tryParse(JsonHelper.getString(json, "enchantment"));
			final int level = JsonHelper.getInt(json, "level", 1);

			final int lapisCost = JsonHelper.getInt(json, "lapis_count", 1);
			final int expCost = JsonHelper.getInt(json, "exp_amount", 1);

			return new RuneEnchantingRecipe(id, book, target, primary, secondary, extra, output, lapisCost, expCost, enchantmentIdentifier, level);
		}

		@Override
		public RuneEnchantingRecipe read(Identifier id, PacketByteBuf buf)
		{
			final Ingredient book = Ingredient.fromPacket(buf);
			final Ingredient target = Ingredient.fromPacket(buf);
			final Ingredient primary = Ingredient.fromPacket(buf);
			final Ingredient secondary = Ingredient.fromPacket(buf);
			final Ingredient extra = Ingredient.fromPacket(buf);
			final ItemStack output = buf.readItemStack();

			final Identifier enchantmentIdentifier = buf.readIdentifier();
			final int level = buf.readInt();

			final int lapisCost = buf.readInt();
			final int expCost = buf.readInt();

			return new RuneEnchantingRecipe(id, book, target, primary, secondary, extra, output, lapisCost, expCost, enchantmentIdentifier, level);
		}

		@Override
		public void write(PacketByteBuf buf, RuneEnchantingRecipe recipe)
		{
			recipe.book.write(buf);
			recipe.target.write(buf);
			recipe.primary.write(buf);
			recipe.secondary.write(buf);
			recipe.extra.write(buf);
			buf.writeItemStack(recipe.output);

			buf.writeIdentifier(recipe.enchantmentIdentifier);
			buf.writeInt(recipe.level);

			buf.writeInt(recipe.lapisCost);
			buf.writeInt(recipe.expCost);
		}
	}
}
