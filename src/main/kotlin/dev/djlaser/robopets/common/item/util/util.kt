package dev.djlaser.robopets.common.item.util

import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.neoforged.neoforge.common.Tags

fun canMicrochipEntity(target: LivingEntity): Boolean {
  return when {
    target is Player || !target.isAlive || target.type.`is`(Tags.EntityTypes.BOSSES) -> false

    else -> true
  }
}
