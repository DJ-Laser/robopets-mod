package dev.djlaser.robopets.datagen.common.tag

import dev.djlaser.robopets.common.RobopetsMod
import dev.djlaser.robopets.common.registries.RobopetsBlocks
import java.util.concurrent.CompletableFuture
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.tags.BlockTags
import net.neoforged.neoforge.common.data.BlockTagsProvider
import net.neoforged.neoforge.common.data.ExistingFileHelper

class RobopetsBlockTagsProvider(
  output: PackOutput,
  lookupProvider: CompletableFuture<HolderLookup.Provider>,
  existingFileHelper: ExistingFileHelper,
) : BlockTagsProvider(output, lookupProvider, RobopetsMod.MODID, existingFileHelper) {

  override fun addTags(lookupProvider: HolderLookup.Provider) {
    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(RobopetsBlocks.GENETIC_REPLICATOR.getBlock())
  }
}
