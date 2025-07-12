package dev.djlaser.robopets.client.screen

import dev.djlaser.robopets.common.RobopetsMod
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.EditBox
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation


class PetTransceiverScreen : Screen(TITLE) {
  companion object {
    private val TITLE: Component = Component.translatable("gui.robopets.pet_transceiver.title")
    private val PET_NAME_LABEL: Component = Component.translatable("gui.robopets.pet_transceiver.pet_name")

    private val BACKGROUND: ResourceLocation = RobopetsMod.loc("textures/gui/pet_transceiver.png")

    private val imageWidth = 256
    private val imageHeight = 140
  }

  private var leftPos = 0
  private var topPos = 0

  private lateinit var petName: EditBox

  override fun init() {
    super.init()

    leftPos = (width - imageWidth) / 2
    topPos = (height - imageHeight) / 2

    petName = EditBox(font, leftPos + 6, topPos + 6, imageWidth - 12, 10, PET_NAME_LABEL)
    petName.isBordered = false
    petName.textShadow = false
    petName.setTextColor(-1)
    petName.setMaxLength(50)

    this.addRenderableWidget(petName)
  }

  override fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
    super.render(guiGraphics, mouseX, mouseY, partialTick)
  }

  override fun renderBackground(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
    this.renderTransparentBackground(guiGraphics)
    guiGraphics.blit(BACKGROUND, leftPos, topPos, 0, 0, imageWidth, imageHeight)
  }
}