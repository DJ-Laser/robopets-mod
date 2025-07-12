package dev.djlaser.robopets.common.blockentity

import dev.djlaser.robopets.common.registries.RobopetsBlockEntityTypes
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class GeneticReplicatorBlockEntity(pos: BlockPos, state: BlockState) :
  BlockEntity(RobopetsBlockEntityTypes.GENETIC_REPLICATOR.get(), pos, state)
