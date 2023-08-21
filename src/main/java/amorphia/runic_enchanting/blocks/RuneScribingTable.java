package amorphia.runic_enchanting.blocks;

import amorphia.runic_enchanting.Runes;
import amorphia.runic_enchanting.screen.RuneScribingScreenHandler;
import com.google.common.collect.Lists;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RuneScribingTable extends Block implements Waterloggable
{
	protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);
	public static final List<BlockPos> RUNE_OFFSETS = BlockPos.stream(-2, 0, -2, 2, 1, 2).filter(pos -> Math.abs(pos.getX()) == 2 || Math.abs(pos.getZ()) == 2).map(BlockPos::toImmutable).toList();

	public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
	public static final BooleanProperty WATERLOGED = Properties.WATERLOGGED;

	protected RuneScribingTable(Settings settings)
	{
		super(settings);
		this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGED, false));
	}

	public static boolean canAccessRuneBlock(World world, BlockPos tablePos, BlockPos runeOffset)
	{
		return world.getBlockState(tablePos.add(runeOffset)).getBlock() instanceof RuneBlock && world.isAir(tablePos.add(runeOffset.getX() / 2, runeOffset.getY(), runeOffset.getZ() / 2));
	}

	public static List<Runes> getAccessibleRunes(World world, BlockPos tablePos)
	{
		List<Runes> runes = Lists.newArrayList();
		for (BlockPos offset : RUNE_OFFSETS)
		{
			if (canAccessRuneBlock(world, tablePos, offset) && world.getBlockState(tablePos.add(offset)).getBlock() instanceof RuneBlock runeBlock)
			{
				runes.add(runeBlock.getRune());
			}
		}
		return runes;
	}

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx)
	{
		return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite()).with(WATERLOGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
	{
		builder.add(FACING, WATERLOGED);
	}

	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation)
	{
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	@Override
	public FluidState getFluidState(BlockState state)
	{
		return state.get(WATERLOGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos,
			BlockPos neighborPos)
	{
		if (state.get(WATERLOGED))
		{
			world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
	{
		return SHAPE;
	}

	@Override
	public BlockRenderType getRenderType(BlockState state)
	{
		return BlockRenderType.MODEL;
	}

	@Override
	public boolean hasSidedTransparency(BlockState state)
	{
		return true;
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random)
	{
		super.randomDisplayTick(state, world, pos, random);
		for(BlockPos blockPos : RUNE_OFFSETS)
		{
			if(random.nextInt(16) != 0 || !RuneScribingTable.canAccessRuneBlock(world, pos, blockPos)) continue;
			world.addParticle(ParticleTypes.ENCHANT, pos.getX() + 0.5, pos.getY() + 2.0, pos.getZ() + 0.5, (blockPos.getX() + random.nextFloat()) - 0.5, (blockPos.getY() - random.nextFloat()) - 1.0f, (blockPos.getZ() + random.nextFloat()) - 0.5);
		}
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
	{
		if(world.isClient)
			return ActionResult.SUCCESS;

		player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
		return ActionResult.CONSUME;
	}

	@Nullable
	@Override
	public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos)
	{
		return new SimpleNamedScreenHandlerFactory((syncId, inv, player) -> {
			return new RuneScribingScreenHandler(syncId, inv, ScreenHandlerContext.create(world, pos));
		}, Text.translatable("runic_enchanting.screen.rune_scribing"));
	}
}
