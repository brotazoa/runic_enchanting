package amorphia.runic_enchanting.items;

import amorphia.runic_enchanting.Runes;
import amorphia.runic_enchanting.blocks.RuneBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChalkItem extends Item implements Vanishable
{
	public ChalkItem(Settings settings)
	{
		super(settings);
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
	{
		tooltip.add(Text.translatable("runic_enchanting.tooltip.chalk.crafting_description"));
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context)
	{
		World world = context.getWorld();
		BlockPos blockPos = context.getBlockPos();
		BlockState blockState = world.getBlockState(blockPos);
		PlayerEntity player = context.getPlayer();

		if (player != null &&
				blockState.getBlock() instanceof RuneBlock runeBlock &&
				player.getEquippedStack(EquipmentSlot.MAINHAND).getItem() instanceof ChalkItem &&
				player.getEquippedStack(EquipmentSlot.OFFHAND).getItem() == Items.PAPER)
		{
			ItemStack paperStack = player.getEquippedStack(EquipmentSlot.OFFHAND);
			Runes rune = runeBlock.getRune();

			RunePatternItem patternItem = RunePatternItem.PATTERN_ITEM_BY_RUNE.get(rune);
			if (patternItem != null)
			{
				//TODO: change sound to custom
				world.playSound(player, blockPos, SoundEvents.ENTITY_VILLAGER_WORK_CARTOGRAPHER, SoundCategory.BLOCKS, 1.0f, 1.0f);

				if (!world.isClient)
				{
					ItemStack patternStack = new ItemStack(patternItem);
					patternStack.setCount(1);

					paperStack.decrement(1);

					if (paperStack.isEmpty())
					{
						player.setStackInHand(Hand.OFF_HAND, paperStack);
					}
					else
					{
						player.getInventory().offerOrDrop(patternStack);
					}

					world.emitGameEvent(GameEvent.ITEM_INTERACT_FINISH, blockPos, GameEvent.Emitter.of(player));
					context.getStack().damage(1, player, p -> p.sendToolBreakStatus(context.getHand()));
				}
				return ActionResult.success(world.isClient);
			}

		}

		return ActionResult.PASS;
	}
}
