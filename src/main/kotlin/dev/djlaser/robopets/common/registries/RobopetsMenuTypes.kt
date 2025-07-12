package dev.djlaser.robopets.common.registries

import dev.djlaser.robopets.common.RobopetsMod
import dev.djlaser.robopets.common.menu.PetTransceiverMenu
import java.util.function.Supplier
import net.minecraft.core.registries.Registries
import net.minecraft.world.inventory.MenuType
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension
import net.neoforged.neoforge.registries.DeferredRegister

object RobopetsMenuTypes {
  val MENU_TYPES: DeferredRegister<MenuType<*>> =
    DeferredRegister.create(Registries.MENU, RobopetsMod.MODID)

  val PET_TRANSCEIVER: Supplier<MenuType<PetTransceiverMenu>> =
    MENU_TYPES.register(
      "pet_transceiver",
      Supplier {
        IMenuTypeExtension.create { containerId, playerInv, buf ->
          PetTransceiverMenu.fromBuf(containerId, playerInv, buf)
        }
      },
    )
}
