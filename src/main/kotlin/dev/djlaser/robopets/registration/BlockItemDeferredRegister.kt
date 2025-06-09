package dev.djlaser.robopets.registration

import java.util.function.Supplier
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister

class BlockItemDefferedRegister private constructor(modid: String) {
  companion object {
    fun create(modid: String): BlockItemDefferedRegister {
      return BlockItemDefferedRegister(modid)
    }
  }

  val BLOCKS = DeferredRegister.createBlocks(modid)

  val ITEMS = DeferredRegister.createItems(modid)

  fun <B : Block, I : BlockItem> DeferredBlockItem(
    name: String,
    blockSupplier: () -> B,
    itemCreator: (B) -> I,
  ): DeferredBlockItem<B, I> {
    val block = BLOCKS.register(name, blockSupplier)
    val item = ITEMS.register(name, Supplier { itemCreator(block.get()) })

    return DeferredBlockItem(block, item)
  }

  fun <B : Block> registerBlock(
    name: String,
    blockSupplier: () -> B,
  ): DeferredBlockItem<B, BlockItem> {
    return DeferredBlockItem(name, blockSupplier, { BlockItem(it, Item.Properties()) })
  }

  fun registerSimpleBlock(
    name: String,
    properties: BlockBehaviour.Properties,
  ): DeferredBlockItem<Block, BlockItem> {
    return DeferredBlockItem(name, { Block(properties) }, { BlockItem(it, Item.Properties()) })
  }

  fun addAlias(from: ResourceLocation, to: ResourceLocation) {
    BLOCKS.addAlias(from, to)
    ITEMS.addAlias(from, to)
  }

  fun register(bus: IEventBus) {
    BLOCKS.register(bus)
    ITEMS.register(bus)
  }

  fun getBlocks(): Collection<DeferredHolder<Block, out Block>> {
    return BLOCKS.getEntries()
  }

  fun getItems(): Collection<DeferredHolder<Item, out Item>> {
    return ITEMS.getEntries()
  }
}
