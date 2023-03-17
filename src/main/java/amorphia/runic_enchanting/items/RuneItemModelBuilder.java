package amorphia.runic_enchanting.items;

import amorphia.runic_enchanting.Runes;
import com.google.common.collect.Maps;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class RuneItemModelBuilder
{
	private static final Map<Identifier, Supplier<String>> MODEL_SUPPLIER_FOR_IDENTIFIER = Maps.newIdentityHashMap();

	public static void register(Identifier identifier, Supplier<String> jsonModelSupplier)
	{
		MODEL_SUPPLIER_FOR_IDENTIFIER.put(identifier, jsonModelSupplier);
	}

	public static Optional<Map.Entry<Identifier, Supplier<String>>> getModelSupplierForIdentifier(Identifier identifier)
	{
		return MODEL_SUPPLIER_FOR_IDENTIFIER.entrySet().stream().filter(entry -> entry.getKey().equals(identifier)).findFirst();
	}

	public static Supplier<String> createRunePatternItemModelJson(Runes rune)
	{
		return () -> "{"
				+ "\"parent\": \"minecraft:item/generated\","
				+ "\"textures\": {"
				+ "\"layer0\": \"runic_enchanting:item/rune_pattern_base\","
				+ "\"layer1\": \"" + rune.getRuneTextureIdentifier() + "\"}"
				+ "}";
	}

	public static Supplier<String> createRuneStoneItemModelJson(Runes rune)
	{
		return () -> "{"
				+ "\"parent\": \"minecraft:item/generated\","
				+ "\"textures\": {"
				+ "\"layer0\": \"runic_enchanting:item/rune_stone_base\","
				+ "\"layer1\": \"" + rune.getRuneTextureIdentifier() + "\"}"
				+ "}";
	}

	public static Supplier<String> createRunePageItemModelJson(Runes rune)
	{
		return () -> "{"
				+ "\"parent\": \"minecraft:item/generated\","
				+ "\"textures\": {"
				+ "\"layer0\": \"runic_enchanting:item/rune_sheet_base\","
				+ "\"layer1\": \"" + rune.getRuneTextureIdentifier() + "\"}"
				+ "}";
	}
}
