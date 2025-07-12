package dev.djlaser.robopets.common.network

import dev.djlaser.robopets.common.RobopetsMod
import dev.djlaser.robopets.common.menu.PetTransceiverMenu
import io.netty.buffer.ByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.neoforged.neoforge.network.handling.IPayloadContext


data class ServerBoundRenameEntityPayload(val newName: String) : CustomPacketPayload {
  companion object {
    val TYPE: CustomPacketPayload.Type<ServerBoundRenameEntityPayload> =
      CustomPacketPayload.Type<ServerBoundRenameEntityPayload>(RobopetsMod.loc("pet_transceiver/rename"))

    val STREAM_CODEC: StreamCodec<ByteBuf, ServerBoundRenameEntityPayload> = StreamCodec.composite(
      ByteBufCodecs.STRING_UTF8,
      ServerBoundRenameEntityPayload::newName,
      ::ServerBoundRenameEntityPayload
    )

    fun handle(data: ServerBoundRenameEntityPayload, context: IPayloadContext) {
      val player = context.player()
      val menu = player.containerMenu

      if (menu is PetTransceiverMenu) {
        if (!menu.stillValid(player)) {
          RobopetsMod.LOGGER.debug(
            "Player {} interacted with invalid menu {}",
            player, menu
          )
          return
        }

        menu.renameEntity(data.newName)
      }
    }
  }

  override fun type(): CustomPacketPayload.Type<out CustomPacketPayload> {
    return TYPE
  }
}