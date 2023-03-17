package amorphia.runic_enchanting.blocks;

import amorphia.runic_enchanting.screen.RuneEnchantingScreenHandler;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
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
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class RunicEnchantingTable extends Block implements Waterloggable
{
	private static final VoxelShape FR_LEG = Block.createCuboidShape(0.5, 0.0, 0.5, 3.5, 8.0, 3.5);
	private static final VoxelShape FL_LEG = Block.createCuboidShape(12.5, 0.0, 0.5, 15.5, 8.0, 3.5);
	private static final VoxelShape BR_LEG = Block.createCuboidShape(0.5, 0.0, 12.5, 3.5, 8.0, 15.5);
	private static final VoxelShape BL_LEG = Block.createCuboidShape(12.5, 0.0, 12.5, 15.5, 8.0, 15.5);
	private static final VoxelShape TOP = Block.createCuboidShape(0.0, 8.0, 0.0, 16.0, 12.0, 16.0);

	protected static final VoxelShape SHAPE = VoxelShapes.union(TOP, FR_LEG, FL_LEG, BR_LEG, BL_LEG);

	public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

	protected RunicEnchantingTable(Settings settings)
	{
		super(settings);
		this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, false));
	}

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx)
	{
		return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite()).with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
	{
		builder.add(FACING, WATERLOGGED);
	}

	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation)
	{
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	@Override
	public FluidState getFluidState(BlockState state)
	{
		return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos,
			BlockPos neighborPos)
	{
		if (state.get(WATERLOGGED))
		{
			world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
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
			return new RuneEnchantingScreenHandler(syncId, inv, ScreenHandlerContext.create(world, pos));
		}, Text.translatable("runic_enchanting.screen.rune_enchanting"));
	}
}
