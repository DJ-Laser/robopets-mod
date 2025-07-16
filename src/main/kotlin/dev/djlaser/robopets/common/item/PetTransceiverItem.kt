package dev.djlaser.robopets.common.item

import dev.djlaser.robopets.common.item.util.InteractLivingEntityFirst
import dev.djlaser.robopets.common.menu.PetTransceiverMenu
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.SimpleMenuProvider
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.OwnableEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.common.Tags

class PetTransceiverItem(properties: Properties) : Item(properties), InteractLivingEntityFirst {
  private fun canInteractWith(player: Player, target: LivingEntity): Boolean {
    return when {
      target is Player
          || !target.isAlive
          || target.type.`is`(Tags.EntityTypes.BOSSES)
          || (target is OwnableEntity && target.owner != player)
        -> false

      else -> true
    }
  }

  override fun interactLivingEntity(
    stack: ItemStack,
    player: Player,
    target: LivingEntity,
    usedHand: InteractionHand,
  ): InteractionResult {
    val level = player.level()

    if (player.isSecondaryUseActive || usedHand == InteractionHand.OFF_HAND) {
      return InteractionResult.PASS
    }

    if (!canInteractWith(player, target)) {
      return InteractionResult.PASS
    }

    if (!level.isClientSide()) {
      player.openMenu(
        SimpleMenuProvider(
          { containerId, playerInv, _ -> PetTransceiverMenu(containerId, playerInv, target) },
          Component.translatable("gui.robopets.pet_transceiver.title"),
        ),
      ) { buf -> buf.writeInt(target.id) }
    }

    return InteractionResult.sidedSuccess(level.isClientSide())
  }
}
