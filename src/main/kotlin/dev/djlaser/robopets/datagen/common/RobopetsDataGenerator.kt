package dev.djlaser.robopets.datagen.common

import dev.djlaser.robopets.RobopetsMod
import dev.djlaser.robopets.datagen.client.model.RobopetsItemModelProvider
import dev.djlaser.robopets.datagen.client.state.RobopetsBlockStateProvider
import dev.djlaser.robopets.datagen.common.loot.RobopetsLootTableProvider
import java.util.concurrent.CompletableFuture
import net.minecraft.core.HolderLookup
import net.minecraft.data.DataGenerator
import net.minecraft.data.PackOutput
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.common.data.ExistingFileHelper
import net.neoforged.neoforge.data.event.GatherDataEvent

@EventBusSubscriber(modid = RobopetsMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public object RobopetsDataGenerator {
  @SubscribeEvent
  @JvmStatic
  public fun gatherData(event: GatherDataEvent) {
    var generator: DataGenerator = event.getGenerator()
    var packOutput: PackOutput = generator.getPackOutput()
    var existingFileHelper: ExistingFileHelper = event.getExistingFileHelper()
    var lookupProvider: CompletableFuture<HolderLookup.Provider> = event.getLookupProvider()

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
  }
}
