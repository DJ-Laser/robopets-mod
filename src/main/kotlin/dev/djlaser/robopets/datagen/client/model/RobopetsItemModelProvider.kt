package dev.djlaser.robopets.datagen.client.model

import dev.djlaser.robopets.common.RobopetsMod
import dev.djlaser.robopets.common.registries.RobopetsItems
import net.minecraft.data.PackOutput
import net.neoforged.neoforge.client.model.generators.ItemModelProvider
import net.neoforged.neoforge.common.data.ExistingFileHelper

class RobopetsItemModelProvider(output: PackOutput, existingFileHelper: ExistingFileHelper) :
  ItemModelProvider(output, RobopetsMod.MODID, existingFileHelper) {

  override fun registerModels() {
    basicItem(RobopetsItems.PET_TRANSCEIVER.get())
    basicItem(RobopetsItems.MICROCHIP.get())
    basicItem(RobopetsItems.HYPODERMIC_NEEDLE.get())
  }
}
