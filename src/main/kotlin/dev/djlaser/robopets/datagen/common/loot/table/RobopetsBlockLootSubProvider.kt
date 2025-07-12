package dev.djlaser.robopets.datagen.common.loot.table

import dev.djlaser.robopets.common.registries.RobopetsBlocks
import net.minecraft.core.HolderLookup
import net.minecraft.data.loot.BlockLootSubProvider
import net.minecraft.world.flag.FeatureFlags
import net.minecraft.world.level.block.Block

class RobopetsBlockLootSubProvider(lookupProvider: HolderLookup.Provider) :
  BlockLootSubProvider(setOf(), FeatureFlags.DEFAULT_FLAGS, lookupProvider) {

  override fun getKnownBlocks(): Iterable<Block> {
    return RobopetsBlocks.BLOCKS.getBlocks().map { it.get() }
  }

  override fun generate() {
    dropSelf(RobopetsBlocks.GENETIC_REPLICATOR.getBlock())
  }
}
