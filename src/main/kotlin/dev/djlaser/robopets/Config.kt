package dev.djlaser.robopets

import java.util.stream.Collectors
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.event.config.ModConfigEvent
import net.neoforged.neoforge.common.ModConfigSpec

@EventBusSubscriber(modid = RoboPetsMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public object Config {
  private val BUILDER: ModConfigSpec.Builder = ModConfigSpec.Builder()

  private val LOG_DIRT_BLOCK: ModConfigSpec.BooleanValue =
    BUILDER.comment("Whether to log the dirt block on common setup").define("logDirtBlock", true)

  private val MAGIC_NUMBER: ModConfigSpec.IntValue =
    BUILDER.comment("A magic number").defineInRange("magicNumber", 42, 0, Integer.MAX_VALUE)

  private val MAGIC_NUMBER_INTRODUCTION: ModConfigSpec.ConfigValue<String> =
    BUILDER.comment("What you want the introduction message to be for the magic number")
      .define("magicNumberIntroduction", "The magic number is... ")

  // a list of strings that are treated as resource locations for items
  private val ITEM_STRINGS: ModConfigSpec.ConfigValue<List<String>> =
    BUILDER.comment("A list of items to log on common setup.")
      .defineListAllowEmpty("items", listOf("minecraft:iron_ingot"), ::validateItemName)

  val SPEC: ModConfigSpec = BUILDER.build()

  public var logDirtBlock: Boolean = false
  public var magicNumber: Int = 0
  lateinit public var magicNumberIntroduction: String
  lateinit public var items: Set<Item>

  private fun validateItemName(itemName: Any): Boolean {
    return itemName is String &&
      BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName))
  }

  @SubscribeEvent
  fun onLoad(@Suppress("UNUSED_PARAMETER") event: ModConfigEvent) {
    logDirtBlock = LOG_DIRT_BLOCK.get()
    magicNumber = MAGIC_NUMBER.get()
    magicNumberIntroduction = MAGIC_NUMBER_INTRODUCTION.get()

    // convert the list of strings into a set of items
    items =
      ITEM_STRINGS.get()
        .stream()
        .map { itemName: String -> BuiltInRegistries.ITEM[ResourceLocation.parse(itemName)] }
        .collect(Collectors.toSet())
  }
}
