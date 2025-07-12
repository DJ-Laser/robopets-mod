package dev.djlaser.robopets.common.item

import dev.djlaser.robopets.common.menu.PetTransceiverMenu
import net.minecraft.network.chat.Component
import net.minecraft.stats.Stats
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.SimpleMenuProvider
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent

class PetTransceiverItem(properties: Properties) : Item(properties) {
  fun handleInteractEvent(event: PlayerInteractEvent.EntityInteractSpecific) {
    val player = event.entity
    val target = event.target
    val hand = event.hand

    if (target !is LivingEntity) {
      return
    }

    val result = interactLivingEntity(
      event.itemStack,
      player,
      target,
      hand
    )

    event.cancellationResult = result
    event.isCanceled = result != InteractionResult.PASS

    if (result.shouldSwing() && event.level.isClientSide) {
      player.swing(hand)
    }

    if (result.indicateItemUse()) {
      player.awardStat(Stats.ITEM_USED[this])
    }
  }

  override fun interactLivingEntity(
    stack: ItemStack,
    player: Player,
    petEntity: LivingEntity,
    usedHand: InteractionHand,
  ): InteractionResult {
    val level = player.level()

    if (player.isSecondaryUseActive || usedHand == InteractionHand.OFF_HAND) {
      return  InteractionResult.PASS
    }

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
