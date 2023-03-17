package amorphia.runic_enchanting.items;

import amorphia.runic_enchanting.Runes;
import amorphia.runic_enchanting.RunicEnchanting;
import amorphia.runic_enchanting.blocks.RuneBlock;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Vanishable;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChiselItem extends Item implements Vanishable
{
	protected static Map<Block, Map<Runes, BlockState>> BLOCKSTATES_FOR_RUNE = Maps.newHashMap();

	public static void addBlockstateForRune(Block baseBlock, Runes rune, BlockState blockState)
	{
		if(!BLOCKSTATES_FOR_RUNE.containsKey(baseBlock))
			BLOCKSTATES_FOR_RUNE.put(baseBlock, new HashMap<>());

		Map<Runes, BlockState> blockstates = BLOCKSTATES_FOR_RUNE.get(baseBlock);
		blockstates.put(rune, blockState);
	}

	public ChiselItem(Settings settings)
	{
		super(settings);
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
	{
		tooltip.add(Text.translatable("runic_enchanting.tooltip.chisel.crafting_description"));
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context)
	{
		World world = context.getWorld();
		BlockPos blockPos = context.getBlockPos();
		BlockState blockState = world.getBlockState(blockPos);
		PlayerEntity player = context.getPlayer();

		if (player != null && player.getEquippedStack(EquipmentSlot.MAINHAND).getItem() instanceof ChiselItem &&
				player.getEquippedStack(EquipmentSlot.OFFHAND).getItem() instanceof RunePatternItem patternItem &&
				BLOCKSTATES_FOR_RUNE.containsKey(blockState.getBlock()))
		{
			BlockState runeBlockState = BLOCKSTATES_FOR_RUNE.get(blockState.getBlock()).get(patternItem.getRune());
			if (runeBlockState != null)
			{
				//TODO: custom sound event
				world.playSound(player, blockPos, SoundEvents.ENTITY_VILLAGER_WORK_TOOLSMITH, SoundCategory.BLOCKS, 1.0f, 1.0f);

				if (!world.isClient)
				{
					world.setBlockState(blockPos, runeBlockState, Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
					world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(player, runeBlockState));
					context.getStack().damage(1, player, p -> p.sendToolBreakStatus(context.getHand()));
				}
				return ActionResult.success(world.isClient);
			}
		}

		return ActionResult.PASS;
	}
}
