package dev.djlaser.robopets.common.item

import dev.djlaser.robopets.common.menu.PetTransceiverMenu
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.SimpleMenuProvider
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

class PetTransceiverItem(properties: Properties) : Item(properties) {
  override fun interactLivingEntity(
    stack: ItemStack,
    player: Player,
    petEntity: LivingEntity,
    usedHand: InteractionHand,
  ): InteractionResult {
    val level = player.level()

    if (!level.isClientSide()) {
      player.openMenu(
        SimpleMenuProvider(
          { containerId, playerInv, _ -> PetTransceiverMenu(containerId, playerInv, petEntity) },
          Component.translatable("gui.robopets.pet_transceiver.title")
        ),
        { buf -> buf.writeInt(petEntity.id) }
      )
    }

    return InteractionResult.sidedSuccess(level.isClientSide())
  }
}
