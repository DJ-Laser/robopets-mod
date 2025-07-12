package dev.djlaser.robopets.datagen.client.state

import dev.djlaser.robopets.common.RobopetsMod
import dev.djlaser.robopets.common.registration.DeferredBlockItem
import dev.djlaser.robopets.common.registries.RobopetsBlocks
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
          GENETIC_REPLICATOR.name,
          blockTexture(GENETIC_REPLICATOR, "_side"),
          blockTexture(GENETIC_REPLICATOR, "_front"),
          blockTexture(GENETIC_REPLICATOR, "_top"),
        )

    horizontalBlock(GENETIC_REPLICATOR.getBlock(), replicatorModel)
    autoBlockItem(GENETIC_REPLICATOR)
  }

  fun simpleBlockItem(block: DeferredBlockItem<*, *>, model: ModelFile) {
    itemModels().getBuilder(block.name).parent(model)
  }

  private fun autoBlockItem(block: DeferredBlockItem<*, *>) {
    itemModels().simpleBlockItem(block.id)
  }

  private fun extend(base: ResourceLocation, suffix: String): ResourceLocation {
    return ResourceLocation.fromNamespaceAndPath(base.namespace, base.path + suffix)
  }

  private fun blockTexture(block: DeferredBlockItem<*, *>): ResourceLocation {
    val name = block.id
    return ResourceLocation.fromNamespaceAndPath(name.namespace, "block/" + name.path)
  }

  private fun blockTexture(block: DeferredBlockItem<*, *>, suffix: String): ResourceLocation {
    return extend(blockTexture(block), suffix)
  }
}
