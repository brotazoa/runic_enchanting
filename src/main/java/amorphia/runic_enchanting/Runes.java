package amorphia.runic_enchanting;

import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Locale;

public enum Runes
{
	AIR(RunicEnchanting.identify("air_rune")),
	ARMOR(RunicEnchanting.identify("armor_rune")),
	CUNNING(RunicEnchanting.identify("cunning_rune")),
	DAMAGE(RunicEnchanting.identify("damage_rune")),
	DESTRUCTION(RunicEnchanting.identify("destruction_rune")),
	EARTH(RunicEnchanting.identify("earth_rune")),
	EQUIPMENT(RunicEnchanting.identify("equipment_rune")),
	FIRE(RunicEnchanting.identify("fire_rune")),
	FORCE(RunicEnchanting.identify("force_rune")),
	INVENTORY(RunicEnchanting.identify("inventory_rune")),
	LIFE(RunicEnchanting.identify("life_rune")),
	LUCK(RunicEnchanting.identify("luck_rune")),
	MELEE(RunicEnchanting.identify("melee_rune")),
	PRESERVATION(RunicEnchanting.identify("preservation_rune")),
	PROTECTION(RunicEnchanting.identify("protection_rune")),
	RANGE(RunicEnchanting.identify("range_rune")),
	REVENGE(RunicEnchanting.identify("revenge_rune")),
	SPEED(RunicEnchanting.identify("speed_rune")),
	SPIRIT(RunicEnchanting.identify("spirit_rune")),
	TIME(RunicEnchanting.identify("time_rune")),
	TOOL(RunicEnchanting.identify("tool_rune")),
	WATER(RunicEnchanting.identify("water_rune")),
	;

	public static final Runes[] VALUES_CACHE = Runes.values();

	private final Identifier runeTextureIdentifier;

	public static Runes fromIdentifier(Identifier identifier)
	{
		return Arrays.stream(VALUES_CACHE).filter(rune -> rune.getRuneTextureIdentifier().equals(identifier)).findFirst().orElseThrow();
	}

	public static Runes byName(String name)
	{
		return Runes.valueOf(name.toUpperCase(Locale.ROOT));
	}

	Runes(Identifier runeTextureIdentifier)
	{
		this.runeTextureIdentifier = runeTextureIdentifier;
	}

	public Identifier getRuneTextureIdentifier()
	{
		return this.runeTextureIdentifier;
	}
}
