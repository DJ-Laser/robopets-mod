package dev.djlaser.robopets.registries

import dev.djlaser.robopets.RobopetsMod
import dev.djlaser.robopets.block.GeneticReplicatorBlock
import dev.djlaser.robopets.registration.BlockItemDefferedRegister
import dev.djlaser.robopets.registration.DeferredBlockItem
import net.minecraft.world.item.BlockItem
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.MapColor

public object RobopetsBlocks {
  val BLOCKS: BlockItemDefferedRegister = BlockItemDefferedRegister.create(RobopetsMod.MODID)

  val GENETIC_REPLICATOR: DeferredBlockItem<GeneticReplicatorBlock, BlockItem> =
    BLOCKS.registerBlock(
      "genetic_replicator",
      { GeneticReplicatorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)) },
    )
}
