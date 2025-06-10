package dev.djlaser.robopets.datagen.client

import dev.djlaser.robopets.RobopetsMod
import dev.djlaser.robopets.registration.DeferredBlockItem
import dev.djlaser.robopets.registries.RobopetsBlocks
import net.minecraft.data.PackOutput
import net.minecraft.resources.ResourceLocation
import net.neoforged.neoforge.client.model.generators.BlockStateProvider
import net.neoforged.neoforge.client.model.generators.ModelFile
import net.neoforged.neoforge.common.data.ExistingFileHelper

class RobopetsBlockStateProvider(output: PackOutput, existingFileHelper: ExistingFileHelper) :
  BlockStateProvider(output, RobopetsMod.MODID, existingFileHelper) {

  override fun registerStatesAndModels() {
    val GENETIC_REPLICATOR = RobopetsBlocks.GENETIC_REPLICATOR
    val replicatorModel: ModelFile =
      models()
        .orientable(
          RobopetsBlocks.GENETIC_REPLICATOR.getName(),
          blockTexture(GENETIC_REPLICATOR, "_side"),
          blockTexture(GENETIC_REPLICATOR, "_front"),
          blockTexture(GENETIC_REPLICATOR, "_top"),
        )

    horizontalBlock(GENETIC_REPLICATOR.getBlock(), replicatorModel)
    autoBlockItem(GENETIC_REPLICATOR)
  }

  fun simpleBlockItem(block: DeferredBlockItem<*, *>, model: ModelFile) {
    itemModels().getBuilder(block.getName()).parent(model)
  }

  fun autoBlockItem(block: DeferredBlockItem<*, *>) {
    itemModels().simpleBlockItem(block.getId())
  }

  fun extend(base: ResourceLocation, suffix: String): ResourceLocation {
    return ResourceLocation.fromNamespaceAndPath(base.getNamespace(), base.getPath() + suffix)
  }

  fun blockTexture(block: DeferredBlockItem<*, *>): ResourceLocation {
    val name = block.getId()
    return ResourceLocation.fromNamespaceAndPath(name.getNamespace(), "block/" + name.getPath())
  }

  fun blockTexture(block: DeferredBlockItem<*, *>, suffix: String): ResourceLocation {
    return extend(blockTexture(block), suffix)
  }
}
