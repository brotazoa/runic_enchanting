package amorphia.runic_enchanting.loot;

import amorphia.runic_enchanting.items.RE_Items;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

public class RE_Loot
{
	private static final Identifier ANCIENT_CITY = new Identifier("chests/ancient_city");
	private static final Identifier BASTION = new Identifier("chests/bastion_other");
	private static final Identifier BURIED_TREASURE = new Identifier("chests/buried_treasure");
	private static final Identifier DESERT_PYRAMID = new Identifier("chests/desert_pyramid");
	private static final Identifier JUNGLE_TEMPLE = new Identifier("chests/jungle_temple");
	private static final Identifier NETHER_BRIDGE = new Identifier("chests/nether_bridge");
	private static final Identifier PILLAGER_OUTPOST = new Identifier("chests/pillager_outpost");
	private static final Identifier SHIPWRECK = new Identifier("chests/shipwreck_treasure");
	private static final Identifier DUNGEON = new Identifier("chests/simple_dungeon");
	private static final Identifier STRONGHOLD = new Identifier("chests/stronghold_library");
	private static final Identifier WATER_RUIN = new Identifier("chests/underwater_ruin_big");
	private static final Identifier MANSION = new Identifier("chests/woodland_mansion");


	public static void init()
	{
		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			if (source.isBuiltin() && id.equals(ANCIENT_CITY))
			{
				tableBuilder.pool(addRareLoot());
				tableBuilder.pool(addRareTreasure());
				tableBuilder.pool(addTreasure());

				LootPool.Builder builder = LootPool.builder();
				builder.rolls(UniformLootNumberProvider.create(0.0f, 1.0f));
				builder.with(ItemEntry.builder(RE_Items.ITEMS.get("force_rune_pattern")));
				builder.bonusRolls(ConstantLootNumberProvider.create(1.0f));
				tableBuilder.pool(builder);
			}
		});

		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			if (source.isBuiltin() && id.equals(BASTION))
			{
				tableBuilder.pool(addRareLoot());
				tableBuilder.pool(addRareTreasure());
				tableBuilder.pool(addTreasure());

				LootPool.Builder builder = LootPool.builder();
				builder.rolls(UniformLootNumberProvider.create(0.0f, 1.0f));
				builder.with(ItemEntry.builder(RE_Items.ITEMS.get("fire_rune_pattern")));
				builder.with(ItemEntry.builder(RE_Items.ITEMS.get("cunning_rune_pattern")));
				builder.with(ItemEntry.builder(RE_Items.ITEMS.get("spirit_rune_pattern")));
				builder.bonusRolls(ConstantLootNumberProvider.create(1.0f));
				tableBuilder.pool(builder);
			}
		});

		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			if (source.isBuiltin() && id.equals(BURIED_TREASURE))
			{
				tableBuilder.pool(addCommonTreasure());
				tableBuilder.pool(addTreasure());
			}
		});

		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			if (source.isBuiltin() && id.equals(DESERT_PYRAMID))
			{
				tableBuilder.pool(addCommonTreasure());
				tableBuilder.pool(addUncommonLoot());

				LootPool.Builder builder = LootPool.builder();
				builder.rolls(UniformLootNumberProvider.create(0.0f, 1.0f));
				builder.with(ItemEntry.builder(RE_Items.ITEMS.get("inventory_rune_pattern")));
				builder.with(ItemEntry.builder(RE_Items.ITEMS.get("speed_rune_pattern")));
				builder.bonusRolls(ConstantLootNumberProvider.create(1.0f));
				tableBuilder.pool(builder);
			}
		});

		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			if (source.isBuiltin() && id.equals(JUNGLE_TEMPLE))
			{
				tableBuilder.pool(addCommonLoot());
				tableBuilder.pool(addCommonTreasure());
				tableBuilder.pool(addUncommonLoot());
				tableBuilder.pool(addUncommonTreasure());
			}
		});

		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			if (source.isBuiltin() && id.equals(NETHER_BRIDGE))
			{
				tableBuilder.pool(addUncommonTreasure());
				tableBuilder.pool(addRareLoot());
				tableBuilder.pool(addRareTreasure());

				LootPool.Builder builder = LootPool.builder();
				builder.rolls(UniformLootNumberProvider.create(0.0f, 1.0f));
				builder.with(ItemEntry.builder(RE_Items.ITEMS.get("fire_rune_pattern")));
				builder.with(ItemEntry.builder(RE_Items.ITEMS.get("cunning_rune_pattern")));
				builder.with(ItemEntry.builder(RE_Items.ITEMS.get("spirit_rune_pattern")));
				builder.bonusRolls(ConstantLootNumberProvider.create(1.0f));
				tableBuilder.pool(builder);
			}
		});

		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			if (source.isBuiltin() && id.equals(PILLAGER_OUTPOST))
			{
				tableBuilder.pool(addCommonLoot());
				tableBuilder.pool(addUncommonLoot());
				tableBuilder.pool(addRareLoot());

				LootPool.Builder builder = LootPool.builder();
				builder.rolls(UniformLootNumberProvider.create(0.0f, 1.0f));
				builder.with(ItemEntry.builder(RE_Items.ITEMS.get("revenge_rune_pattern")));
				builder.with(ItemEntry.builder(RE_Items.ITEMS.get("range_rune_pattern")));
				builder.bonusRolls(ConstantLootNumberProvider.create(1.0f));
				tableBuilder.pool(builder);
			}
		});

		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			if (source.isBuiltin() && id.equals(SHIPWRECK))
			{
				tableBuilder.pool(addCommonTreasure());
				tableBuilder.pool(addUncommonLoot());
			}
		});

		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			if (source.isBuiltin() && id.equals(DUNGEON))
			{
				tableBuilder.pool(addCommonLoot());
				tableBuilder.pool(addCommonTreasure());
			}
		});

		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			if (source.isBuiltin() && id.equals(STRONGHOLD))
			{
				tableBuilder.pool(addCommonLoot());
				tableBuilder.pool(addUncommonLoot());
				tableBuilder.pool(addRareLoot());
				tableBuilder.pool(addTreasure());

				LootPool.Builder builder = LootPool.builder();
				builder.rolls(UniformLootNumberProvider.create(0.0f, 1.0f));
				builder.with(ItemEntry.builder(RE_Items.ITEMS.get("time_rune_pattern")));
				builder.bonusRolls(ConstantLootNumberProvider.create(1.0f));
				tableBuilder.pool(builder);
			}
		});

		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			if (source.isBuiltin() && id.equals(WATER_RUIN))
			{
				tableBuilder.pool(addCommonLoot());
				tableBuilder.pool(addCommonTreasure());

				LootPool.Builder builder = LootPool.builder();
				builder.rolls(UniformLootNumberProvider.create(0.0f, 1.0f));
				builder.with(ItemEntry.builder(RE_Items.ITEMS.get("water_rune_stone")));
				builder.bonusRolls(ConstantLootNumberProvider.create(1.0f));
				tableBuilder.pool(builder);
			}
		});

		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			if (source.isBuiltin() && id.equals(MANSION))
			{
				tableBuilder.pool(addUncommonTreasure());
				tableBuilder.pool(addRareLoot());
				tableBuilder.pool(addRareTreasure());
				tableBuilder.pool(addTreasure());

				LootPool.Builder builder = LootPool.builder();
				builder.rolls(UniformLootNumberProvider.create(0.0f, 1.0f));
				builder.with(ItemEntry.builder(RE_Items.ITEMS.get("preservation_rune_pattern")));
				builder.bonusRolls(ConstantLootNumberProvider.create(1.0f));
				tableBuilder.pool(builder);
			}
		});
	}

	public static void initClient()
	{

	}

	private static LootPool.Builder addCommonLoot()
	{
		LootPool.Builder builder = LootPool.builder();

		builder.rolls(UniformLootNumberProvider.create(1.0f, 3.0f));

		builder.with(ItemEntry.builder(RE_Items.ITEMS.get("air_rune_page")));
		builder.with(ItemEntry.builder(RE_Items.ITEMS.get("armor_rune_page")));
		builder.with(ItemEntry.builder(RE_Items.ITEMS.get("earth_rune_page")));
		builder.with(ItemEntry.builder(RE_Items.ITEMS.get("equipment_rune_page")));
		builder.with(ItemEntry.builder(RE_Items.ITEMS.get("fire_rune_page")));
		builder.with(ItemEntry.builder(RE_Items.ITEMS.get("melee_rune_page")));
		builder.with(ItemEntry.builder(RE_Items.ITEMS.get("range_rune_page")));
		builder.with(ItemEntry.builder(RE_Items.ITEMS.get("tool_rune_page")));
		builder.with(ItemEntry.builder(RE_Items.ITEMS.get("water_rune_page")));

		builder.bonusRolls(ConstantLootNumberProvider.create(2.0f));

		return builder;
	}

	private static LootPool.Builder addCommonTreasure()
	{
		LootPool.Builder builder = LootPool.builder();

		builder.rolls(UniformLootNumberProvider.create(1.0f, 2.0f));

		builder.with(ItemEntry.builder(RE_Items.ITEMS.get("air_rune_stone")));
		builder.with(ItemEntry.builder(RE_Items.ITEMS.get("armor_rune_stone")));
		builder.with(ItemEntry.builder(RE_Items.ITEMS.get("earth_rune_stone")));
		builder.with(ItemEntry.builder(RE_Items.ITEMS.get("equipment_rune_stone")));
		builder.with(ItemEntry.builder(RE_Items.ITEMS.get("fire_rune_stone")));
		builder.with(ItemEntry.builder(RE_Items.ITEMS.get("melee_rune_stone")));
		builder.with(ItemEntry.builder(RE_Items.ITEMS.get("range_rune_stone")));
		builder.with(ItemEntry.builder(RE_Items.ITEMS.get("tool_rune_stone")));
		builder.with(ItemEntry.builder(RE_Items.ITEMS.get("water_rune_stone")));

		builder.bonusRolls(ConstantLootNumberProvider.create(1.0f));

		return builder;
	}

	private static LootPool.Builder addUncommonLoot()
	{
		LootPool.Builder builder = LootPool.builder();

		builder.rolls(UniformLootNumberProvider.create(1.0f, 3.0f));

		builder.with(ItemEntry.builder(RE_Items.ITEMS.get("damage_rune_page")));
		builder.with(ItemEntry.builder(RE_Items.ITEMS.get("force_rune_page")));
		builder.with(ItemEntry.builder(RE_Items.ITEMS.get("protection_rune_page")));
		builder.with(ItemEntry.builder(RE_Items.ITEMS.get("spirit_rune_page")));

		builder.bonusRolls(ConstantLootNumberProvider.create(2.0f));

		return builder;
	}

	private static LootPool.Builder addUncommonTreasure()
	{
		LootPool.Builder builder = LootPool.builder();

		builder.rolls(UniformLootNumberProvider.create(1.0f, 2.0f));

		builder.with(ItemEntry.builder(RE_Items.ITEMS.get("damage_rune_stone")));
		builder.with(ItemEntry.builder(RE_Items.ITEMS.get("protection_rune_stone")));

		builder.bonusRolls(ConstantLootNumberProvider.create(1.0f));

		return builder;
	}

	private static LootPool.Builder addRareLoot()
	{
		LootPool.Builder builder = LootPool.builder();

		builder.rolls(UniformLootNumberProvider.create(1.0f, 2.0f));

		builder.with(ItemEntry.builder(RE_Items.ITEMS.get("cunning_rune_page")));
		builder.with(ItemEntry.builder(RE_Items.ITEMS.get("destruction_rune_page")));
		builder.with(ItemEntry.builder(RE_Items.ITEMS.get("life_rune_page")));
		builder.with(ItemEntry.builder(RE_Items.ITEMS.get("luck_rune_page")));
		builder.with(ItemEntry.builder(RE_Items.ITEMS.get("speed_rune_page")));

		builder.bonusRolls(ConstantLootNumberProvider.create(2.0f));

		return builder;
	}

	private static LootPool.Builder addRareTreasure()
	{
		LootPool.Builder builder = LootPool.builder();

		builder.rolls(UniformLootNumberProvider.create(1.0f, 2.0f));

		builder.with(ItemEntry.builder(RE_Items.ITEMS.get("cunning_rune_stone")));
		builder.with(ItemEntry.builder(RE_Items.ITEMS.get("destruction_rune_stone")));
		builder.with(ItemEntry.builder(RE_Items.ITEMS.get("life_rune_stone")));
		builder.with(ItemEntry.builder(RE_Items.ITEMS.get("luck_rune_stone")));
		builder.with(ItemEntry.builder(RE_Items.ITEMS.get("speed_rune_stone")));

		builder.bonusRolls(ConstantLootNumberProvider.create(1.0f));

		return builder;
	}

	private static LootPool.Builder addTreasure()
	{
		LootPool.Builder builder = LootPool.builder();

		builder.rolls(UniformLootNumberProvider.create(1.0f, 2.0f));

		builder.with(ItemEntry.builder(RE_Items.ITEMS.get("inventory_rune_page")));
		builder.with(ItemEntry.builder(RE_Items.ITEMS.get("preservation_rune_page")));
		builder.with(ItemEntry.builder(RE_Items.ITEMS.get("revenge_rune_page")));
		builder.with(ItemEntry.builder(RE_Items.ITEMS.get("time_rune_page")));

		builder.bonusRolls(ConstantLootNumberProvider.create(1.0f));

		return builder;
	}
}
