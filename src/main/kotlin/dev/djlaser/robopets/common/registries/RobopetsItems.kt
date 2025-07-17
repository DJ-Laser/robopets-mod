package dev.djlaser.robopets.common.registries

import dev.djlaser.robopets.common.RobopetsMod
import dev.djlaser.robopets.common.item.HypodermicNeedleItem
import dev.djlaser.robopets.common.item.PetTransceiverItem
import net.minecraft.world.item.Item
import net.neoforged.neoforge.registries.DeferredItem
import net.neoforged.neoforge.registries.DeferredRegister

object RobopetsItems {
  val ITEMS: DeferredRegister.Items = DeferredRegister.createItems(RobopetsMod.MODID)

  val PET_TRANSCEIVER: DeferredItem<PetTransceiverItem> =
    ITEMS.registerItem("pet_transceiver", ::PetTransceiverItem, Item.Properties())

  val MICROCHIP: DeferredItem<Item> = ITEMS.registerSimpleItem("microchip", Item.Properties())
  val HYPODERMIC_NEEDLE: DeferredItem<Item> =
    ITEMS.registerItem("hypodermic_needle", ::HypodermicNeedleItem, Item.Properties().stacksTo(1))
}
