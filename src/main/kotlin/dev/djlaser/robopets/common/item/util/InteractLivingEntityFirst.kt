package dev.djlaser.robopets.common.item.util

import net.minecraft.stats.Stats
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent

interface InteractLivingEntityFirst {
  fun handleInteractEvent(event: PlayerInteractEvent.EntityInteractSpecific) {
    val player = event.entity
    val target = event.target
    val hand = event.hand

    if (target !is LivingEntity) {
      return
    }

    val result = interactLivingEntity(event.itemStack, player, target, hand)

    event.cancellationResult = result
    event.isCanceled = result != InteractionResult.PASS

    if (result.shouldSwing() && event.level.isClientSide) {
      player.swing(hand)
    }

    if (result.indicateItemUse()) {
      player.awardStat(Stats.ITEM_USED[event.itemStack.item])
    }
  }

  fun interactLivingEntity(
    stack: ItemStack,
    player: Player,
    target: LivingEntity,
    usedHand: InteractionHand,
  ): InteractionResult
}
