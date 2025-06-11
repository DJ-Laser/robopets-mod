package dev.djlaser.robopets.block

import com.mojang.serialization.MapCodec
import dev.djlaser.blockentity.GeneticReplicatorBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.Mirror
import net.minecraft.world.level.block.Rotation
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.DirectionProperty

public class GeneticReplicatorBlock(properties: Properties) : Block(properties), EntityBlock {
  companion object {
    val CODEC: MapCodec<GeneticReplicatorBlock> = simpleCodec(::GeneticReplicatorBlock)
    val FACING: DirectionProperty = BlockStateProperties.HORIZONTAL_FACING
  }

  init {
    registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH))
  }

  override fun codec(): MapCodec<GeneticReplicatorBlock> {
    return CODEC
  }

  override fun createBlockStateDefinition(pBuilder: StateDefinition.Builder<Block, BlockState>) {
    pBuilder.add(FACING)
  }

  override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
    return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite())
  }

  override fun rotate(state: BlockState, rotation: Rotation): BlockState {
    return state.setValue(FACING, rotation.rotate(state.getValue(FACING)))
  }

  override fun mirror(state: BlockState, mirror: Mirror): BlockState {
    return rotate(state, mirror.getRotation(state.getValue(FACING)))
  }

  override fun newBlockEntity(pos: BlockPos, state: BlockState): GeneticReplicatorBlockEntity? {
    return GeneticReplicatorBlockEntity(pos, state)
  }
}
