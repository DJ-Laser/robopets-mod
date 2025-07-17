package dev.djlaser.robopets.datagen.common.recipe

import dev.djlaser.robopets.common.registries.RobopetsItems
import java.util.concurrent.CompletableFuture
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.RecipeProvider
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.world.item.Items
import net.neoforged.neoforge.common.Tags

class RobopetsRecipeProvider(
  output: PackOutput,
  lookupProvider: CompletableFuture<HolderLookup.Provider>,
) : RecipeProvider(output, lookupProvider) {
  override fun buildRecipes(output: RecipeOutput) {
    ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RobopetsItems.PET_TRANSCEIVER)
      .pattern("  L")
      .pattern("IPI")
      .pattern("IDI")
      .define('L', Items.LIGHTNING_ROD)
      .define('I', Items.IRON_INGOT)
      .define('P', Tags.Items.GLASS_PANES)
      .define('D', Items.DIAMOND)
      .unlockedBy("has_copper", has(Items.COPPER_INGOT))
      .unlockedBy("has_diamond", has(Items.DIAMOND))
      .save(output)

    ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RobopetsItems.MICROCHIP)
      .pattern("ODO")
      .pattern("CLR")
      .pattern("III")
      .define('O', Items.OBSERVER)
      .define('D', Items.DIAMOND)
      .define('C', Items.COMPARATOR)
      .define('L', Items.CLOCK)
      .define('R', Items.REPEATER)
      .define('I', Items.COPPER_INGOT)
      .unlockedBy("has_copper", has(Items.COPPER_INGOT))
      .unlockedBy("has_redstone", has(Items.REDSTONE))
      .unlockedBy("has_diamond", has(Items.DIAMOND))
      .save(output)

    ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RobopetsItems.HYPODERMIC_NEEDLE)
      .pattern(" GS")
      .pattern("GMG")
      .pattern("IG ")
      .define('G', Tags.Items.GLASS_BLOCKS_CHEAP)
      .define('S', Items.STICK)
      .define('M', RobopetsItems.MICROCHIP)
      .define('I', Items.IRON_INGOT)
      .unlockedBy("has_microchip", has(RobopetsItems.MICROCHIP))
      .save(output)
  }
}
