package dev.djlaser.robopets.registration

import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.registries.DeferredBlock
import net.neoforged.neoforge.registries.DeferredItem

public class DeferredBlockItem<B : Block, I : Item>(
  val block: DeferredBlock<B>,
  val item: DeferredItem<I>,
) {
  fun getBlock(): B {
    return block.get()
  }

  fun getItem(): I {
    return item.get()
  }
}
