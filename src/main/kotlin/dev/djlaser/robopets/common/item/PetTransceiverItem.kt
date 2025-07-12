package dev.djlaser.robopets.common.item

import dev.djlaser.robopets.client.screen.PetTransceiverScreen
import net.minecraft.client.Minecraft
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
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

    if (level.isClientSide()) {
      level.playLocalSound(player, SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 1f, 1f)
      Minecraft.getInstance().setScreen(PetTransceiverScreen(petEntity))
    }

    return InteractionResult.sidedSuccess(level.isClientSide())
  }
}
