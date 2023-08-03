package amorphia.runic_enchanting;

import com.google.common.collect.Maps;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

import java.util.Locale;
import java.util.Map;

public class RE_Tags
{
	public static TagKey<Item> RUNE_PATTERNS;
	public static TagKey<Item> RUNE_STONES;
	public static TagKey<Item> RUNE_PAGES;

	public static TagKey<Item> CAN_BE_CONVERTED_TO_PATTERN;

	public static TagKey<Item> AIR_RUNE_BLOCKS;
	public static TagKey<Item> ARMOR_RUNE_BLOCKS;
	public static TagKey<Item> CUNNING_RUNE_BLOCKS;
	public static TagKey<Item> DAMAGE_RUNE_BLOCKS;
	public static TagKey<Item> DESTRUCTION_RUNE_BLOCKS;
	public static TagKey<Item> EARTH_RUNE_BLOCKS;
	public static TagKey<Item> EQUIPMENT_RUNE_BLOCKS;
	public static TagKey<Item> FIRE_RUNE_BLOCKS;
	public static TagKey<Item> FORCE_RUNE_BLOCKS;
	public static TagKey<Item> INVENTORY_RUNE_BLOCKS;
	public static TagKey<Item> LIFE_RUNE_BLOCKS;
	public static TagKey<Item> LUCK_RUNE_BLOCKS;
	public static TagKey<Item> MELEE_RUNE_BLOCKS;
	public static TagKey<Item> PRESERVATION_RUNE_BLOCKS;
	public static TagKey<Item> PROTECTION_RUNE_BLOCKS;
	public static TagKey<Item> RANGE_RUNE_BLOCKS;
	public static TagKey<Item> REVENGE_RUNE_BLOCKS;
	public static TagKey<Item> SPEED_RUNE_BLOCKS;
	public static TagKey<Item> SPIRIT_RUNE_BLOCKS;
	public static TagKey<Item> TIME_RUNE_BLOCKS;
	public static TagKey<Item> TOOL_RUNE_BLOCKS;
	public static TagKey<Item> WATER_RUNE_BLOCKS;

	public static Map<Runes, TagKey<Item>> BLOCK_TAG_BY_RUNE = Maps.newHashMap();

	public static Map<Runes, TagKey<BannerPattern>> BANNER_PATTERN_TAG_BY_RUNE = Maps.newHashMap();

	public static void init()
	{
		RUNE_PATTERNS = TagKey.of(RegistryKeys.ITEM, RunicEnchanting.identify("rune_patterns"));
		RUNE_STONES = TagKey.of(RegistryKeys.ITEM, RunicEnchanting.identify("rune_stones"));
		RUNE_PAGES = TagKey.of(RegistryKeys.ITEM, RunicEnchanting.identify("rune_pages"));

		CAN_BE_CONVERTED_TO_PATTERN = TagKey.of(RegistryKeys.ITEM, RunicEnchanting.identify("can_be_converted_to_pattern"));

		AIR_RUNE_BLOCKS = TagKey.of(RegistryKeys.ITEM, RunicEnchanting.identify("air_rune_blocks"));
		ARMOR_RUNE_BLOCKS = TagKey.of(RegistryKeys.ITEM, RunicEnchanting.identify("armor_rune_blocks"));
		CUNNING_RUNE_BLOCKS = TagKey.of(RegistryKeys.ITEM, RunicEnchanting.identify("cunning_rune_blocks"));
		DAMAGE_RUNE_BLOCKS = TagKey.of(RegistryKeys.ITEM, RunicEnchanting.identify("damage_rune_blocks"));
		DESTRUCTION_RUNE_BLOCKS = TagKey.of(RegistryKeys.ITEM, RunicEnchanting.identify("destruction_rune_blocks"));
		EARTH_RUNE_BLOCKS = TagKey.of(RegistryKeys.ITEM, RunicEnchanting.identify("earth_rune_blocks"));
		EQUIPMENT_RUNE_BLOCKS = TagKey.of(RegistryKeys.ITEM, RunicEnchanting.identify("equipment_rune_blocks"));
		FIRE_RUNE_BLOCKS = TagKey.of(RegistryKeys.ITEM, RunicEnchanting.identify("fire_rune_blocks"));
		FORCE_RUNE_BLOCKS = TagKey.of(RegistryKeys.ITEM, RunicEnchanting.identify("force_rune_blocks"));
		INVENTORY_RUNE_BLOCKS = TagKey.of(RegistryKeys.ITEM, RunicEnchanting.identify("inventory_rune_blocks"));
		LIFE_RUNE_BLOCKS = TagKey.of(RegistryKeys.ITEM, RunicEnchanting.identify("life_rune_blocks"));
		LUCK_RUNE_BLOCKS = TagKey.of(RegistryKeys.ITEM, RunicEnchanting.identify("luck_rune_blocks"));
		MELEE_RUNE_BLOCKS = TagKey.of(RegistryKeys.ITEM, RunicEnchanting.identify("melee_rune_blocks"));
		PRESERVATION_RUNE_BLOCKS = TagKey.of(RegistryKeys.ITEM, RunicEnchanting.identify("preservation_rune_blocks"));
		PROTECTION_RUNE_BLOCKS = TagKey.of(RegistryKeys.ITEM, RunicEnchanting.identify("protection_rune_blocks"));
		RANGE_RUNE_BLOCKS = TagKey.of(RegistryKeys.ITEM, RunicEnchanting.identify("range_rune_blocks"));
		REVENGE_RUNE_BLOCKS = TagKey.of(RegistryKeys.ITEM, RunicEnchanting.identify("revenge_rune_blocks"));
		SPEED_RUNE_BLOCKS = TagKey.of(RegistryKeys.ITEM, RunicEnchanting.identify("speed_rune_blocks"));
		SPIRIT_RUNE_BLOCKS = TagKey.of(RegistryKeys.ITEM, RunicEnchanting.identify("spirit_rune_blocks"));
		TIME_RUNE_BLOCKS = TagKey.of(RegistryKeys.ITEM, RunicEnchanting.identify("time_rune_blocks"));
		TOOL_RUNE_BLOCKS = TagKey.of(RegistryKeys.ITEM, RunicEnchanting.identify("tool_rune_blocks"));
		WATER_RUNE_BLOCKS = TagKey.of(RegistryKeys.ITEM, RunicEnchanting.identify("water_rune_blocks"));

		BLOCK_TAG_BY_RUNE.put(Runes.AIR, AIR_RUNE_BLOCKS);
		BLOCK_TAG_BY_RUNE.put(Runes.ARMOR, ARMOR_RUNE_BLOCKS);
		BLOCK_TAG_BY_RUNE.put(Runes.CUNNING, CUNNING_RUNE_BLOCKS);
		BLOCK_TAG_BY_RUNE.put(Runes.DAMAGE, DAMAGE_RUNE_BLOCKS);
		BLOCK_TAG_BY_RUNE.put(Runes.DESTRUCTION, DESTRUCTION_RUNE_BLOCKS);
		BLOCK_TAG_BY_RUNE.put(Runes.EARTH, EARTH_RUNE_BLOCKS);
		BLOCK_TAG_BY_RUNE.put(Runes.EQUIPMENT, EQUIPMENT_RUNE_BLOCKS);
		BLOCK_TAG_BY_RUNE.put(Runes.FIRE, FIRE_RUNE_BLOCKS);
		BLOCK_TAG_BY_RUNE.put(Runes.FORCE, FORCE_RUNE_BLOCKS);
		BLOCK_TAG_BY_RUNE.put(Runes.INVENTORY, INVENTORY_RUNE_BLOCKS);
		BLOCK_TAG_BY_RUNE.put(Runes.LIFE, LIFE_RUNE_BLOCKS);
		BLOCK_TAG_BY_RUNE.put(Runes.LUCK, LUCK_RUNE_BLOCKS);
		BLOCK_TAG_BY_RUNE.put(Runes.MELEE, MELEE_RUNE_BLOCKS);
		BLOCK_TAG_BY_RUNE.put(Runes.PRESERVATION, PRESERVATION_RUNE_BLOCKS);
		BLOCK_TAG_BY_RUNE.put(Runes.PROTECTION, PROTECTION_RUNE_BLOCKS);
		BLOCK_TAG_BY_RUNE.put(Runes.RANGE, RANGE_RUNE_BLOCKS);
		BLOCK_TAG_BY_RUNE.put(Runes.REVENGE, REVENGE_RUNE_BLOCKS);
		BLOCK_TAG_BY_RUNE.put(Runes.SPEED, SPEED_RUNE_BLOCKS);
		BLOCK_TAG_BY_RUNE.put(Runes.SPIRIT, SPIRIT_RUNE_BLOCKS);
		BLOCK_TAG_BY_RUNE.put(Runes.TIME, TIME_RUNE_BLOCKS);
		BLOCK_TAG_BY_RUNE.put(Runes.TOOL, TOOL_RUNE_BLOCKS);
		BLOCK_TAG_BY_RUNE.put(Runes.WATER, WATER_RUNE_BLOCKS);

		for (Runes rune : Runes.VALUES_CACHE)
		{
			BANNER_PATTERN_TAG_BY_RUNE.put(rune, TagKey.of(RegistryKeys.BANNER_PATTERN, RunicEnchanting.identify("pattern_item/" + rune.name().toLowerCase(Locale.ROOT))));
		}
	}

	public static void initClient()
	{

	}
}
