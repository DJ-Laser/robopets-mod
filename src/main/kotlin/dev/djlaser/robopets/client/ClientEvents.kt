package dev.djlaser.robopets.client

import dev.djlaser.robopets.client.screen.PetTransceiverScreen
import dev.djlaser.robopets.common.RobopetsMod
import dev.djlaser.robopets.common.registries.RobopetsMenuTypes
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent

@EventBusSubscriber(modid = RobopetsMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = [Dist.CLIENT])
object ClientModEvents {
  @SubscribeEvent
  @JvmStatic
  fun registerScreens(event: RegisterMenuScreensEvent) {
    event.register(
      RobopetsMenuTypes.PET_TRANSCEIVER.get()
    ) { menu, inv, title -> PetTransceiverScreen(menu, inv, title) }
  }
}