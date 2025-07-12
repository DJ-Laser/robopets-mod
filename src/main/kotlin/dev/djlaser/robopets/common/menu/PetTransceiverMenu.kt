package dev.djlaser.robopets.common.menu

import dev.djlaser.robopets.common.registries.RobopetsMenuTypes
import kotlin.math.max
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.util.StringUtil
import net.minecraft.world.Container
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack

object PetTransceiverLayout {
  const val BG_HORIZONTAL_GAP = 5
  const val BG_VERTICAL_GAP = 5

  const val BASE_BG_WIDTH = 256
  const val BASE_BG_HEIGHT = 140

  const val PLAYER_BG_WIDTH = 176
  const val PLAYER_BG_HEIGHT = 90

  const val PANEL_BG_WIDTH = 75
  const val PANEL_BG_HEIGHT = 101

  val COMBINED_BG_WIDTH = max(BASE_BG_WIDTH, PLAYER_BG_WIDTH)
  const val COMBINED_BG_HEIGHT = BASE_BG_HEIGHT + BG_VERTICAL_GAP + PLAYER_BG_HEIGHT

  val BASE_BG_X_OFFSET = (COMBINED_BG_WIDTH - BASE_BG_WIDTH) / 2
  val PLAYER_BG_X_OFFSET = (COMBINED_BG_WIDTH - PLAYER_BG_WIDTH) / 2
  const val PLAYER_BG_Y_OFFSET = BASE_BG_HEIGHT + BG_VERTICAL_GAP
  const val PANEL_BG_X_OFFSET = -1 * (PANEL_BG_WIDTH + BG_HORIZONTAL_GAP)
}

class PetTransceiverMenu(
  containerId: Int,
  private val playerInv: Inventory,
  val petEntity: LivingEntity,
) : AbstractContainerMenu(RobopetsMenuTypes.PET_TRANSCEIVER.get(), containerId) {
  companion object {
    class LockedSlot(inv: Container, index: Int, x: Int, y: Int) : Slot(inv, index, x, y) {
      constructor(slot: Slot) : this(slot.container, slot.index, slot.x, slot.y)

      override fun mayPickup(player: Player): Boolean {
        return false
      }

      override fun mayPlace(stack: ItemStack): Boolean {
        return false
      }
    }

    fun fromBuf(containerId: Int, playerInv: Inventory, buf: FriendlyByteBuf): PetTransceiverMenu {
      val entityId = buf.readInt()
      val petEntity = playerInv.player.level().getEntity(entityId)

      if (petEntity !is LivingEntity) {
        throw ClassCastException(
          "Invalid petEntity id, petEntity should be an instance of LivingEntity"
        )
      }

      return PetTransceiverMenu(containerId, playerInv, petEntity)
    }

    const val NAME_MAX_LEN = 50

    private val INV_X_OFFSET = PetTransceiverLayout.PLAYER_BG_X_OFFSET + 8
    private const val INV_Y_OFFSET = PetTransceiverLayout.PLAYER_BG_Y_OFFSET + 8
    private const val HOTBAR_Y_OFFSET = INV_Y_OFFSET + 58
  }

  init {
    for (row in 0..2) {
      for (column in 0..8) {
        val x = INV_X_OFFSET + column * 18
        val y = INV_Y_OFFSET + row * 18
        val index = column + row * 9 + 9
        val slot = Slot(this.playerInv, index, x, y)
        this.addSlot(slot)
      }
    }

    for (row in 0..8) {
      var slot = Slot(playerInv, row, INV_X_OFFSET + row * 18, HOTBAR_Y_OFFSET)
      if (row == this.playerInv.selected) {
        slot = LockedSlot(slot)
      }
      this.addSlot(slot)
    }
  }

  override fun quickMoveStack(player: Player, index: Int): ItemStack {
    var resultItem = ItemStack.EMPTY
    val slot = slots[index]
    if (slot.hasItem()) {
      resultItem = slot.item.copy()
    }

    return resultItem
  }

  override fun stillValid(player: Player): Boolean {
    return this.petEntity.isAlive && player.canInteractWithEntity(this.petEntity, 4.0)
  }

  val entityName: Component
    get() = petEntity.name

  val entityCustomName
    get() = petEntity.customName

  fun renameEntity(newName: String): Boolean {
    val name: Component? =
      if (StringUtil.isBlank(newName)) null
      else {
        val name = validateName(newName) ?: return false
        Component.literal(name)
      }

    petEntity.customName = name
    return true
  }

  private fun validateName(itemName: String): String? {
    val s = StringUtil.filterText(itemName)
    return if (s.length <= NAME_MAX_LEN) s else null
  }
}
