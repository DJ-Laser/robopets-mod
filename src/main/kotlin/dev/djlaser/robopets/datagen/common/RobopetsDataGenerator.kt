package dev.djlaser.robopets.datagen.common

import dev.djlaser.robopets.common.RobopetsMod
import dev.djlaser.robopets.datagen.client.model.RobopetsItemModelProvider
import dev.djlaser.robopets.datagen.client.state.RobopetsBlockStateProvider
import dev.djlaser.robopets.datagen.common.loot.RobopetsLootTableProvider
import dev.djlaser.robopets.datagen.common.tag.RobopetsBlockTagsProvider
import net.minecraft.core.HolderLookup
import net.minecraft.data.DataGenerator
import net.minecraft.data.PackOutput
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.common.data.ExistingFileHelper
import net.neoforged.neoforge.data.event.GatherDataEvent
import java.util.concurrent.CompletableFuture

@EventBusSubscriber(modid = RobopetsMod.MODID, bus = EventBusSubscriber.Bus.MOD)
object RobopetsDataGenerator {
  @SubscribeEvent
  @JvmStatic
  fun gatherData(event: GatherDataEvent) {
    val generator: DataGenerator = event.generator
    val packOutput: PackOutput = generator.packOutput
    val existingFileHelper: ExistingFileHelper = event.existingFileHelper
    val lookupProvider: CompletableFuture<HolderLookup.Provider> = event.lookupProvider

    generator.addProvider(
      event.includeClient(),
      RobopetsItemModelProvider(packOutput, existingFileHelper),
    )
    generator.addProvider(
      event.includeClient(),
      RobopetsBlockStateProvider(packOutput, existingFileHelper),
    )

    generator.addProvider(
      event.includeServer(),
      RobopetsLootTableProvider(packOutput, lookupProvider),
    )
    generator.addProvider(
      event.includeServer(),
      RobopetsBlockTagsProvider(packOutput, lookupProvider, existingFileHelper),
    )
  }
}
