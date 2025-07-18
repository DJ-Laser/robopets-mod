package dev.djlaser.robopets.common.item

import dev.djlaser.robopets.common.item.util.InteractLivingEntityFirst
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.OwnableEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.common.Tags

class HypodermicNeedleItem(properties: Properties) : Item(properties), InteractLivingEntityFirst {
  companion object {
    private fun canInteractWith(target: LivingEntity): Boolean {
      return when {
        target is Player || !target.isAlive || target.type.`is`(Tags.EntityTypes.BOSSES) -> false

        else -> true
      }
    }
  }

  override fun interactLivingEntity(
    stack: ItemStack,
    player: Player,
    target: LivingEntity,
    usedHand: InteractionHand,
  ): InteractionResult {
    val level = player.level()

    if (!player.isSecondaryUseActive || usedHand == InteractionHand.OFF_HAND) {
      return InteractionResult.PASS
    }

    if (!canInteractWith(target)) {
      return InteractionResult.FAIL
    }

    if (target is OwnableEntity && target.ownerUUID != player.uuid) {
      return InteractionResult.FAIL
    }

    player.inventory.getSelected().shrink(1)
    if (level.isClientSide()) {
      player.displayClientMessage(Component.literal("Not yet implemented"), true)
    }

    return InteractionResult.sidedSuccess(level.isClientSide())
  }
}
