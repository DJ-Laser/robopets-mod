package dev.djlaser.robopets.datagen.common.loot

import dev.djlaser.robopets.datagen.common.loot.table.RobopetsBlockLootSubProvider
import java.util.concurrent.CompletableFuture
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.loot.LootTableProvider
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets

class RobopetsLootTableProvider(
  output: PackOutput,
  lookupProvider: CompletableFuture<HolderLookup.Provider>,
) :
  LootTableProvider(
    output,
    setOf(),
    listOf(SubProviderEntry(::RobopetsBlockLootSubProvider, LootContextParamSets.BLOCK)),
    lookupProvider,
  )
