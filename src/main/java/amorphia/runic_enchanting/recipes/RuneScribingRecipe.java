package amorphia.runic_enchanting.recipes;

import amorphia.runic_enchanting.Runes;
import amorphia.runic_enchanting.RunicEnchanting;
import com.google.gson.JsonObject;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class RuneScribingRecipe implements Recipe<Inventory>
{
	protected final Runes requiredRune;
	protected final Ingredient input;
	protected final ItemStack output;
	protected final Identifier identifier;

	public RuneScribingRecipe(Identifier identifier, Runes requiredRuneAccess, Ingredient input, ItemStack output)
	{
		this.identifier = identifier;
		this.requiredRune = requiredRuneAccess;
		this.input = input;
		this.output = output;
	}

	@Override
	public DefaultedList<Ingredient> getIngredients()
	{
		return DefaultedList.copyOf(Ingredient.EMPTY, input);
	}

	@Override
	public boolean matches(Inventory inventory, World world)
	{
		return this.input.test(inventory.getStack(0));
	}

	@Override
	public ItemStack craft(Inventory inventory)
	{
		return this.getOutput();
	}

	@Override
	public boolean fits(int width, int height)
	{
		return width * height >= 1;
	}

	public Runes getRequiredRune()
	{
		return this.requiredRune;
	}

	@Override
	public ItemStack getOutput()
	{
		return this.output.copy();
	}

	@Override
	public Identifier getId()
	{
		return this.identifier;
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

	public static class Type implements RecipeType<RuneScribingRecipe>
	{
		public static final Identifier ID = RunicEnchanting.identify("rune_scribing");
		public static final Type INSTANCE = new Type();

		private Type() {} // no op
	}

	public static class Serializer implements RecipeSerializer<RuneScribingRecipe>
	{
		public static Serializer INSTANCE = new Serializer();

		protected Serializer() {}

		@Override
		public RuneScribingRecipe read(Identifier id, JsonObject json)
		{
			final Runes requiredRune = Runes.fromIdentifier(Identifier.tryParse(JsonHelper.getString(json, "required_rune")));
			final Ingredient input = JsonHelper.hasArray(json, "ingredient") ?
					Ingredient.fromJson(JsonHelper.getArray(json, "ingredient")) :
					Ingredient.fromJson(JsonHelper.getObject(json, "ingredient"));
			final ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "result"));
			return new RuneScribingRecipe(id, requiredRune, input, output);
		}

		@Override
		public RuneScribingRecipe read(Identifier id, PacketByteBuf buf)
		{
			final Identifier runeIdentifier = buf.readIdentifier();
			final Runes requiredRune = Runes.fromIdentifier(runeIdentifier);
			final Ingredient input = Ingredient.fromPacket(buf);
			final ItemStack output = buf.readItemStack();
			return new RuneScribingRecipe(id, requiredRune, input, output);
		}

		@Override
		public void write(PacketByteBuf buf, RuneScribingRecipe recipe)
		{
			buf.writeIdentifier(recipe.requiredRune.getRuneTextureIdentifier());
			recipe.input.write(buf);
			buf.writeItemStack(recipe.output);
		}
	}
}
