package dev.djlaser.robopets.registries

import dev.djlaser.robopets.RobopetsMod
import net.minecraft.world.item.Item
import net.neoforged.neoforge.registries.DeferredItem
import net.neoforged.neoforge.registries.DeferredRegister

object RobopetsItems {
  val ITEMS: DeferredRegister.Items = DeferredRegister.createItems(RobopetsMod.MODID)

  val PET_TRANCIEVER: DeferredItem<Item> =
    ITEMS.registerSimpleItem("pet_tranciever", Item.Properties())
}
