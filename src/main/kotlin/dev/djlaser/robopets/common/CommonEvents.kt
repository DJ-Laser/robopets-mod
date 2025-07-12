package dev.djlaser.robopets.common

import dev.djlaser.robopets.common.item.PetTransceiverItem
import dev.djlaser.robopets.common.network.ServerBoundRenameEntityPayload
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent

@EventBusSubscriber(modid = RobopetsMod.MODID, bus = EventBusSubscriber.Bus.GAME)
object CommonGameEvents {
  @SubscribeEvent
  @JvmStatic
  fun interactWithEntity(event: PlayerInteractEvent.EntityInteractSpecific) {
    val item = event.itemStack.item

    if (item is PetTransceiverItem) {
      item.handleInteractEvent(event)
    }
  }
}

@EventBusSubscriber(modid = RobopetsMod.MODID, bus = EventBusSubscriber.Bus.MOD)
object CommonModEvents {
  @SubscribeEvent
  @JvmStatic
  fun registerPayloads(event: RegisterPayloadHandlersEvent) {
    val registrar = event.registrar("1")
    registrar.playToServer(
      ServerBoundRenameEntityPayload.TYPE,
      ServerBoundRenameEntityPayload.STREAM_CODEC,
      ServerBoundRenameEntityPayload::handle,
    )
  }
}
