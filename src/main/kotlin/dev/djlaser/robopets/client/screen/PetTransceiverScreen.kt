package dev.djlaser.robopets.client.screen

import dev.djlaser.robopets.common.RobopetsMod
import dev.djlaser.robopets.common.menu.PetTransceiverLayout as Layout
import dev.djlaser.robopets.common.menu.PetTransceiverMenu
import dev.djlaser.robopets.common.network.ServerBoundRenameEntityPayload
import kotlin.math.PI
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.EditBox
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.gui.screens.inventory.InventoryScreen
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.OwnableEntity
import net.minecraft.world.entity.player.Inventory
import net.neoforged.neoforge.network.PacketDistributor
import org.joml.Quaternionf
import org.joml.Vector3f

class PetTransceiverScreen(menu: PetTransceiverMenu, playerInv: Inventory, title: Component) :
  AbstractContainerScreen<PetTransceiverMenu>(menu, playerInv, title) {
  companion object {
    enum class Icon {
      Health,
      Armor,
    }

    private fun GuiGraphics.drawIcon(x: Int, y: Int, icon: Icon) {
      this.blit(
        BASE_BG,
        x,
        y,
        icon.ordinal * Layout.ICON_WIDTH,
        Layout.ICON_V_OFFSET,
        Layout.ICON_WIDTH,
        Layout.ICON_HEIGHT,
      )
    }

    val PET_NAME_LABEL: Component = Component.translatable("gui.robopets.pet_transceiver.pet_name")

    private val PET_TRANSLATION: Vector3f = Vector3f()

    private val BASE_BG: ResourceLocation = RobopetsMod.loc("textures/gui/pet_transceiver.png")
    private val PLAYER_BG: ResourceLocation = RobopetsMod.loc("textures/gui/player_gui.png")
  }

  init {
    imageWidth = Layout.COMBINED_BG_WIDTH
    imageHeight = Layout.COMBINED_BG_HEIGHT
  }

  private val canEditPet
    get() = menu.canEditPet

  private val petEntity
    get() = menu.petEntity

  private val petViewAngle = Quaternionf().rotationXYZ(0F, 0F, PI.toFloat())
  private lateinit var petName: EditBox
  private val player = playerInv.player

  override fun init() {
    super.init()

    petName = EditBox(font, leftPos + 6, topPos + 6, imageWidth - 12, 10, PET_NAME_LABEL)
    petName.isBordered = false
    petName.textShadow = false
    petName.setTextColor(-1)
    petName.setMaxLength(PetTransceiverMenu.NAME_MAX_LEN)
    petName.setResponder(this::onNameChanged)
    petName.value = menu.entityCustomName?.string ?: ""
    petName.setEditable(canEditPet)
    petName.setHint(menu.entityName)

    this.addRenderableWidget(petName)
  }

  override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
    if (keyCode == 256) {
      minecraft!!.player!!.closeContainer()
    }

    if (petName.keyPressed(keyCode, scanCode, modifiers) || petName.canConsumeInput()) {
      return true
    }

    return super.keyPressed(keyCode, scanCode, modifiers)
  }

  private fun onNameChanged(newName: String) {
    if (menu.renameEntity(newName)) {
      PacketDistributor.sendToServer(ServerBoundRenameEntityPayload(newName))
    }
  }

  override fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
    super.render(guiGraphics, mouseX, mouseY, partialTick)
    renderPetPanel(guiGraphics, partialTick)
    renderPetStats(guiGraphics)
  }

  override fun renderBg(guiGraphics: GuiGraphics, partialTick: Float, mouseX: Int, mouseY: Int) {
    guiGraphics.blit(
      BASE_BG,
      leftPos + Layout.BASE_BG_X_OFFSET,
      topPos,
      0,
      0,
      Layout.BASE_BG_WIDTH,
      Layout.BASE_BG_HEIGHT,
    )
    guiGraphics.blit(
      PLAYER_BG,
      leftPos + Layout.PLAYER_BG_X_OFFSET,
      topPos + Layout.PLAYER_BG_Y_OFFSET,
      0,
      0,
      Layout.PLAYER_BG_WIDTH,
      Layout.PLAYER_BG_HEIGHT,
    )
  }

  override fun renderLabels(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int) {
    // Don't render default labels
  }

  private fun renderPetPanel(guiGraphics: GuiGraphics, partialTick: Float) {
    val panelX = leftPos + Layout.PANEL_BG_X_OFFSET

    guiGraphics.blit(
      BASE_BG,
      panelX,
      topPos,
      0,
      Layout.BASE_BG_HEIGHT,
      Layout.PANEL_BG_WIDTH,
      Layout.PANEL_BG_HEIGHT,
    )

    val entityX = panelX + (Layout.PANEL_BG_WIDTH.toFloat() / 2)
    val entityY = (topPos + 90).toFloat()
    val targetRotation = -player.getViewYRot(partialTick) * PI.toFloat() / 180
    val prevCustomName = petEntity.customName
    petEntity.customName = null

    InventoryScreen.renderEntityInInventory(
      guiGraphics,
      entityX,
      entityY,
      40F,
      PET_TRANSLATION,
      petViewAngle.rotationXYZ(0F, targetRotation, PI.toFloat()),
      null,
      petEntity,
    )

    petEntity.customName = prevCustomName
  }

  private fun renderPetStats(guiGraphics: GuiGraphics) {
    var left = leftPos + Layout.STATS_LEFT
    val top = topPos + Layout.STATS_BOTTOM - font.lineHeight

    guiGraphics.drawIcon(left, top, Icon.Health)
    left += 2 + Layout.ICON_WIDTH

    left =
      4 +
        guiGraphics.drawString(
          font,
          "${petEntity.health.toInt()}/${petEntity.maxHealth.toInt()}",
          left,
          top + 1,
          -1,
          false,
        )

    guiGraphics.drawIcon(left, top, Icon.Armor)
    left += 2 + Layout.ICON_WIDTH

    left = 4 + guiGraphics.drawString(font, "${petEntity.armorValue}", left, top + 1, -1, false)

    when (val petEntity = petEntity) {
      is OwnableEntity -> {
        if (!canEditPet) {
          guiGraphics.drawString(font, "Owner: ${petEntity.owner?.name}", left, top + 1, -1, false)
        }
      }
    }
  }
}
