package amorphia.runic_enchanting.recipes;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class RE_Recipes
{
	public static void init()
	{
		Registry.register(Registries.RECIPE_TYPE, ChalkDamagingRecipeShapless.Type.ID, ChalkDamagingRecipeShapless.Type.INSTANCE);
		Registry.register(Registries.RECIPE_SERIALIZER, ChalkDamagingRecipeShapless.Type.ID, ChalkDamagingRecipeShapless.Serializer.INSTANCE);

		Registry.register(Registries.RECIPE_TYPE, RuneScribingRecipe.Type.ID, RuneScribingRecipe.Type.INSTANCE);
		Registry.register(Registries.RECIPE_SERIALIZER, RuneScribingRecipe.Type.ID, RuneScribingRecipe.Serializer.INSTANCE);

		Registry.register(Registries.RECIPE_TYPE, RunicEnchantingShapeless.Type.ID, RunicEnchantingShapeless.Type.INSTANCE);
		Registry.register(Registries.RECIPE_SERIALIZER, RunicEnchantingShapeless.Type.ID, RunicEnchantingShapeless.Serializer.INSTANCE);

		Registry.register(Registries.RECIPE_TYPE, RuneEnchantingRecipe.Type.ID, RuneEnchantingRecipe.Type.INSTANCE);
		Registry.register(Registries.RECIPE_SERIALIZER, RuneEnchantingRecipe.Type.ID, RuneEnchantingRecipe.Serializer.INSTANCE);
	}

	public static void initClient()
	{

	}
}
