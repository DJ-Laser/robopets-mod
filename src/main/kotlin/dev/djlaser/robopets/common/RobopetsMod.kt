package dev.djlaser.robopets.common

import com.mojang.logging.LogUtils
import dev.djlaser.robopets.RobopetsMetadata
import dev.djlaser.robopets.common.registries.RobopetsBlockEntityTypes
import dev.djlaser.robopets.common.registries.RobopetsBlocks
import dev.djlaser.robopets.common.registries.RobopetsCreativeTabs
import dev.djlaser.robopets.common.registries.RobopetsItems
import net.minecraft.client.Minecraft
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Blocks
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.IEventBus
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.ModContainer
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.common.Mod
import net.neoforged.fml.config.ModConfig
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
import net.neoforged.neoforge.common.NeoForge
import net.neoforged.neoforge.event.server.ServerStartingEvent

@Mod(RobopetsMod.MODID)
class RobopetsMod(modEventBus: IEventBus, modContainer: ModContainer) {
  companion object {
    const val MODID = RobopetsMetadata.MODID
    private val LOGGER = LogUtils.getLogger()

    fun loc(path: String): ResourceLocation {
      return ResourceLocation.fromNamespaceAndPath(MODID, path)
    }

    // You can use EventBusSubscriber to automatically register all static methods in
    // the class
    // annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = [Dist.CLIENT])
    object ClientModEvents {
      @SubscribeEvent
      @JvmStatic
      fun onClientSetup(@Suppress("UNUSED_PARAMETER") event: FMLClientSetupEvent?) {
        // Some client setup code
        LOGGER.info("HELLO FROM CLIENT SETUP")
        LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().user.name)
      }
    }
  }

  init {
    // Register the commonSetup method for modloading
    modEventBus.addListener(::commonSetup)

    // Register the Deferred Registers to the mod event bus so stuff gets registered
    RobopetsItems.ITEMS.register(modEventBus)
    RobopetsBlocks.BLOCKS.register(modEventBus)
    RobopetsBlockEntityTypes.BLOCK_ENTITY_TYPES.register(modEventBus)
    RobopetsCreativeTabs.CREATIVE_TABS.register(modEventBus)

    // Register ourselves for server and other game events we are interested in.
    // Note that this is necessary if and only if we want *this* class (ExampleMod) to
    // respond
    // directly to events.
    // Do not add this line if there are no @SubscribeEvent-annotated functions in this
    // class, like
    // onServerStarting() below.
    NeoForge.EVENT_BUS.register(this)

    // Register our mod's ModConfigSpec so that FML can create and load the config file
    // for us
    modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC)
  }

  private fun commonSetup(@Suppress("UNUSED_PARAMETER") event: FMLCommonSetupEvent) {
    // Some common setup code
    LOGGER.info("HELLO FROM COMMON SETUP")

    if (Config.logDirtBlock)
      LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT))

    LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber)

    Config.items.forEach { item -> LOGGER.info("ITEM >> {}", item.toString()) }
  }

  // You can use SubscribeEvent and let the Event Bus discover methods to call
  @SubscribeEvent
  fun onServerStarting(@Suppress("UNUSED_PARAMETER") event: ServerStartingEvent) {
    // Do something when the server starts
    LOGGER.info("HELLO from server starting")
  }
}
