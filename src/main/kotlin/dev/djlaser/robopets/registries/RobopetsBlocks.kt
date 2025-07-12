package dev.djlaser.robopets.registries

import dev.djlaser.robopets.RobopetsMod
import dev.djlaser.robopets.block.GeneticReplicatorBlock
import dev.djlaser.robopets.registration.BlockItemDeferredRegister
import dev.djlaser.robopets.registration.DeferredBlockItem
import net.minecraft.world.item.BlockItem
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.minecraft.world.level.material.MapColor

object RobopetsBlocks {
  val BLOCKS: BlockItemDeferredRegister = BlockItemDeferredRegister.create(RobopetsMod.MODID)

  val GENETIC_REPLICATOR: DeferredBlockItem<GeneticReplicatorBlock, BlockItem> =
    BLOCKS.registerBlock(
      "genetic_replicator",
      ::GeneticReplicatorBlock,
      Properties.of()
        .strength(2.5f, 6.0f)
        .requiresCorrectToolForDrops()
        .sound(SoundType.METAL)
        .mapColor(MapColor.STONE),
    )
}
