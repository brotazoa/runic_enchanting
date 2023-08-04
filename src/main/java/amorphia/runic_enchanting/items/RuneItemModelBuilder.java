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
				+ "\"parent\": \"runic_enchanting:item/rune_pattern_template\","
				+ "\"textures\": {"
				+ "\"layer0\": \"runic_enchanting:item/rune_pattern_base\","
				+ "\"layer1\": \"" + new Identifier(rune.getRuneTextureIdentifier().getNamespace(), "item/" + rune.getRuneTextureIdentifier().getPath() + "_item_overlay") + "\"}"
				+ "}";
	}

	public static Supplier<String> createRuneStoneItemModelJson(Runes rune)
	{
		return () -> "{"
				+ "\"parent\": \"runic_enchanting:item/rune_stone_template\","
				+ "\"textures\": {"
				+ "\"layer0\": \"runic_enchanting:item/rune_stone_base\","
				+ "\"layer1\": \"" + new Identifier(rune.getRuneTextureIdentifier().getNamespace(), "item/" + rune.getRuneTextureIdentifier().getPath() + "_item_overlay") + "\"}"
				+ "}";
	}

	public static Supplier<String> createRunePageItemModelJson(Runes rune)
	{
		return () -> "{"
				+ "\"parent\": \"runic_enchanting:item/rune_page_template\","
				+ "\"textures\": {"
				+ "\"layer0\": \"runic_enchanting:item/rune_sheet_base\","
				+ "\"layer1\": \"" + new Identifier(rune.getRuneTextureIdentifier().getNamespace(), "item/" + rune.getRuneTextureIdentifier().getPath() + "_item_overlay") + "\"}"
				+ "}";
	}
}
