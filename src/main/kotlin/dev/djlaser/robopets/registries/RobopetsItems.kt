package dev.djlaser.robopets.registries

import dev.djlaser.robopets.RobopetsMod
import dev.djlaser.robopets.item.PetTrancieverItem
import net.minecraft.world.item.Item
import net.neoforged.neoforge.registries.DeferredItem
import net.neoforged.neoforge.registries.DeferredRegister

object RobopetsItems {
  val ITEMS: DeferredRegister.Items = DeferredRegister.createItems(RobopetsMod.MODID)

  val PET_TRANCIEVER: DeferredItem<PetTrancieverItem> =
    ITEMS.registerItem("pet_tranciever", ::PetTrancieverItem, Item.Properties())

  val MICROCHIP: DeferredItem<Item> = ITEMS.registerSimpleItem("microchip", Item.Properties())
}
