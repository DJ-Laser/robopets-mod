package dev.djlaser.robopets

import com.mojang.logging.LogUtils
import net.minecraft.client.Minecraft
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.CreativeModeTab.ItemDisplayParameters
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.MapColor
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
import net.neoforged.neoforge.registries.DeferredBlock
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredItem
import net.neoforged.neoforge.registries.DeferredRegister

@Mod(RobopetsMod.MODID)
public class RobopetsMod(modEventBus: IEventBus, modContainer: ModContainer) {
  companion object {
    const val MODID = "robopets"
    private val LOGGER = LogUtils.getLogger()

    val BLOCKS: DeferredRegister.Blocks = DeferredRegister.createBlocks(MODID)
    val ITEMS: DeferredRegister.Items = DeferredRegister.createItems(MODID)
    val CREATIVE_MODE_TABS: DeferredRegister<CreativeModeTab> =
      DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID)

    val EXAMPLE_BLOCK: DeferredBlock<Block> =
      BLOCKS.registerSimpleBlock(
        "example_block",
        BlockBehaviour.Properties.of().mapColor(MapColor.STONE),
      )

    val EXAMPLE_BLOCK_ITEM: DeferredItem<BlockItem> =
      ITEMS.registerSimpleBlockItem("example_block", EXAMPLE_BLOCK)

    val EXAMPLE_ITEM: DeferredItem<Item> =
      ITEMS.registerSimpleItem(
        "example_item",
        Item.Properties()
          .food(FoodProperties.Builder().alwaysEdible().nutrition(1).saturationModifier(2f).build()),
      )

    val EXAMPLE_TAB: DeferredHolder<CreativeModeTab, CreativeModeTab> =
      CREATIVE_MODE_TABS.register(
        "robopets_tab",
        { ->
          CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.robopets"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon({ -> EXAMPLE_ITEM.get().getDefaultInstance() })
            .displayItems { _: ItemDisplayParameters?, output: CreativeModeTab.Output ->
              output.accept(EXAMPLE_ITEM.get())
              output.accept(EXAMPLE_BLOCK_ITEM.get())
            }
            .build()
        },
      )

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
    BLOCKS.register(modEventBus)
    ITEMS.register(modEventBus)
    CREATIVE_MODE_TABS.register(modEventBus)

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

    Config.items.forEach({ item -> LOGGER.info("ITEM >> {}", item.toString()) })
  }

  // You can use SubscribeEvent and let the Event Bus discover methods to call
  @SubscribeEvent
  public fun onServerStarting(@Suppress("UNUSED_PARAMETER") event: ServerStartingEvent) {
    // Do something when the server starts
    LOGGER.info("HELLO from server starting")
  }
}
