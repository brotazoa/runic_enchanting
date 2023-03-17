package amorphia.runic_enchanting.blocks;

import amorphia.runic_enchanting.RuneBaseBlocks;
import amorphia.runic_enchanting.Runes;
import com.google.common.collect.Maps;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class RuneBlockModelBuilder
{
	private static final Map<Identifier, Supplier<String>> MODEL_SUPPLIER_FOR_IDENTIFIER = Maps.newIdentityHashMap();

	public static void register(Identifier identifier, Supplier<String> modelSupplier)
	{
		MODEL_SUPPLIER_FOR_IDENTIFIER.put(identifier, modelSupplier);
	}

	public static Optional<Map.Entry<Identifier, Supplier<String>>> getModelSupplierForIdentifier(Identifier identifier)
	{
		return MODEL_SUPPLIER_FOR_IDENTIFIER.entrySet().stream().filter(entry -> entry.getKey().equals(identifier)).findFirst();
	}

	public static Supplier<String> createRuneBlockModelJson(RuneBaseBlocks baseBlock, Runes rune)
	{
		return () -> "{"
				+ "\"parent\": \"runic_enchanting:block/rune_block_template\","
				+ "\"textures\": {"
				+ "\"particle\": \"" + baseBlock.getBaseTexture() + "\","
				+ "\"base\": \"" + baseBlock.getBaseTexture() + "\","
				+ "\"rune\": \"" + rune.getRuneTextureIdentifier() + "\""
				+ "}"
				+ "}";
	}
}
