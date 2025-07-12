package dev.djlaser.robopets.common

import com.mojang.logging.LogUtils
import dev.djlaser.robopets.RobopetsMetadata
import dev.djlaser.robopets.common.registries.*
import net.minecraft.resources.ResourceLocation
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.ModContainer
import net.neoforged.fml.common.Mod
import org.slf4j.Logger

@Mod(RobopetsMod.MODID)
class RobopetsMod(modEventBus: IEventBus, modContainer: ModContainer) {
  companion object {
    const val MODID = RobopetsMetadata.MODID
    val LOGGER: Logger = LogUtils.getLogger()

    fun loc(path: String): ResourceLocation {
      return ResourceLocation.fromNamespaceAndPath(MODID, path)
    }
  }

  init {
    // Register the Deferred Registers to the mod event bus so stuff gets registered
    RobopetsItems.ITEMS.register(modEventBus)
    RobopetsBlocks.BLOCKS.register(modEventBus)
    RobopetsBlockEntityTypes.BLOCK_ENTITY_TYPES.register(modEventBus)
    RobopetsCreativeTabs.CREATIVE_TABS.register(modEventBus)
    RobopetsMenuTypes.MENU_TYPES.register(modEventBus)
  }
}
