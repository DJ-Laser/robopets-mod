package dev.djlaser.robopets.item

import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class PetTrancieverItem(properties: Item.Properties) : Item(properties) {
  override fun use(
    level: Level,
    player: Player,
    hand: InteractionHand,
  ): InteractionResultHolder<ItemStack> {
    val itemstack: ItemStack = player.getItemInHand(hand)

    if (player.isCrouching()) {
      return InteractionResultHolder(InteractionResult.SUCCESS, itemstack)
    }

    if (level.isClientSide()) {
      level.playLocalSound(player, SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 1f, 1f)
    }

    return InteractionResultHolder(InteractionResult.SUCCESS, itemstack)
  }
}
