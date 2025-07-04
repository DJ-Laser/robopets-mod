package dev.djlaser.robopets.registries

import dev.djlaser.blockentity.GeneticReplicatorBlockEntity
import dev.djlaser.robopets.RobopetsMod
import java.util.function.Supplier
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.block.entity.BlockEntityType
import net.neoforged.neoforge.registries.DeferredRegister

object RobopetsBlockEntityTypes {
  val BLOCK_ENTITY_TYPES: DeferredRegister<BlockEntityType<*>> =
    DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, RobopetsMod.MODID)

  val GENETIC_REPLICATOR: Supplier<BlockEntityType<GeneticReplicatorBlockEntity>> =
    BLOCK_ENTITY_TYPES.register(
      "genetic_replicator",
      Supplier {
        BlockEntityType.Builder.of(
            ::GeneticReplicatorBlockEntity,
            RobopetsBlocks.GENETIC_REPLICATOR.getBlock(),
          )
          .build(@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS") null)
      },
    )
}
