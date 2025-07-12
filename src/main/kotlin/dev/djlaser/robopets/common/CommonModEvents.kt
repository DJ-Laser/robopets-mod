package dev.djlaser.robopets.common

import dev.djlaser.robopets.common.network.ServerBoundRenameEntityPayload
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent

@EventBusSubscriber(modid = RobopetsMod.MODID, bus = EventBusSubscriber.Bus.MOD)
object CommonModEvents {
  @SubscribeEvent
  @JvmStatic
  fun registerPayloads(event: RegisterPayloadHandlersEvent) {
    val registrar = event.registrar("1")
    registrar.playToServer(
      ServerBoundRenameEntityPayload.TYPE,
      ServerBoundRenameEntityPayload.STREAM_CODEC,
      ServerBoundRenameEntityPayload::handle
    )
  }
}