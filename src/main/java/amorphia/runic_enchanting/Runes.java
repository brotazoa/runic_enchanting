package amorphia.runic_enchanting;

import net.minecraft.util.Identifier;

import java.util.Arrays;

public enum Runes
{
	AIR(RunicEnchanting.identify("runes/air_rune")),
	ARMOR(RunicEnchanting.identify("runes/armor_rune")),
	CUNNING(RunicEnchanting.identify("runes/cunning_rune")),
	DAMAGE(RunicEnchanting.identify("runes/damage_rune")),
	DESTRUCTION(RunicEnchanting.identify("runes/destruction_rune")),
	EARTH(RunicEnchanting.identify("runes/earth_rune")),
	EQUIPMENT(RunicEnchanting.identify("runes/equipment_rune")),
	FIRE(RunicEnchanting.identify("runes/fire_rune")),
	FORCE(RunicEnchanting.identify("runes/force_rune")),
	INVENTORY(RunicEnchanting.identify("runes/inventory_rune")),
	LIFE(RunicEnchanting.identify("runes/life_rune")),
	LUCK(RunicEnchanting.identify("runes/luck_rune")),
	MELEE(RunicEnchanting.identify("runes/melee_rune")),
	PRESERVATION(RunicEnchanting.identify("runes/preservation_rune")),
	PROTECTION(RunicEnchanting.identify("runes/protection_rune")),
	RANGE(RunicEnchanting.identify("runes/range_rune")),
	REVENGE(RunicEnchanting.identify("runes/revenge_rune")),
	SPEED(RunicEnchanting.identify("runes/speed_rune")),
	SPIRIT(RunicEnchanting.identify("runes/spirit_rune")),
	TIME(RunicEnchanting.identify("runes/time_rune")),
	TOOL(RunicEnchanting.identify("runes/tool_rune")),
	WATER(RunicEnchanting.identify("runes/water_rune")),
	;

	public static final Runes[] VALUES_CACHE = Runes.values();

	private final Identifier runeTextureIdentifier;

	public static Runes fromIdentifier(Identifier identifier)
	{
		return Arrays.stream(VALUES_CACHE).filter(rune -> rune.getRuneTextureIdentifier().equals(identifier)).findFirst().orElseThrow();
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
