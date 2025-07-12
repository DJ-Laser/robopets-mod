package dev.djlaser.robopets.common.registries

import dev.djlaser.robopets.common.RobopetsMod
import dev.djlaser.robopets.common.registration.BlockItemDeferredRegister
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.CreativeModeTab.ItemDisplayParameters
import net.minecraft.world.item.CreativeModeTabs
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister

object RobopetsCreativeTabs {
  val CREATIVE_TABS: DeferredRegister<CreativeModeTab> =
    DeferredRegister.create(Registries.CREATIVE_MODE_TAB, RobopetsMod.MODID)

  val ROBOPETS_TAB: DeferredHolder<CreativeModeTab, CreativeModeTab> =
    CREATIVE_TABS.register("robopets_tab") { ->
      CreativeModeTab.builder()
        .title(Component.translatable("itemGroup.robopets"))
        .withTabsBefore(CreativeModeTabs.COMBAT)
        .icon { RobopetsItems.PET_TRANSCEIVER.get().defaultInstance }
        .displayItems { _: ItemDisplayParameters?, output: CreativeModeTab.Output ->
          acceptOutput(RobopetsItems.ITEMS, output)
          acceptOutput(RobopetsBlocks.BLOCKS, output)
        }
        .build()
    }

  private fun acceptOutput(register: DeferredRegister.Items, output: CreativeModeTab.Output) {
    for (entry in register.entries) {
      output.accept(entry.get())
    }
  }

  private fun acceptOutput(register: BlockItemDeferredRegister, output: CreativeModeTab.Output) {
    for (entry in register.getItems()) {
      output.accept(entry.get())
    }
  }
}
