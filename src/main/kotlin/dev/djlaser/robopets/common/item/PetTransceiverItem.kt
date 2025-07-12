package dev.djlaser.robopets.common.item

import dev.djlaser.robopets.client.screen.PetTransceiverScreen
import net.minecraft.client.Minecraft
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class PetTransceiverItem(properties: Properties) : Item(properties) {
  override fun use(
    level: Level,
    player: Player,
    hand: InteractionHand,
  ): InteractionResultHolder<ItemStack> {
    val heldItem: ItemStack = player.getItemInHand(hand)

    if (level.isClientSide()) {
      level.playLocalSound(player, SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 1f, 1f)
      Minecraft.getInstance().setScreen(PetTransceiverScreen())
    }

    return InteractionResultHolder.sidedSuccess(heldItem, level.isClientSide())
  }
}
