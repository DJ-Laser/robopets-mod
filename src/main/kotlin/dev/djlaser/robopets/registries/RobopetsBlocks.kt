package dev.djlaser.robopets.registries

import dev.djlaser.robopets.RobopetsMod
import dev.djlaser.robopets.registration.BlockItemDefferedRegister
import dev.djlaser.robopets.registration.DeferredBlockItem
import net.minecraft.world.item.BlockItem
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.MapColor

public object RobopetsBlocks {
  val BLOCKS: BlockItemDefferedRegister = BlockItemDefferedRegister.create(RobopetsMod.MODID)

  val EXAMPLE_BLOCK: DeferredBlockItem<Block, BlockItem> =
    BLOCKS.registerSimpleBlock(
      "example_block",
      BlockBehaviour.Properties.of().mapColor(MapColor.STONE),
    )
}
