package ninja.thepurple.groblins.common.crafting;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ninja.thepurple.groblins.common.blocks.ModBlocks;
import ninja.thepurple.groblins.common.items.ModItems;

public class ModCrafting {
    public static void initCrafting() {
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.groblinDustBlock), "###", "###", "###", '#', ModItems.groblinDust);
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.groblinDust, 9), ModBlocks.groblinDustBlock);
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.groblinHouse), " # ", "#*#", "#d#", '#', Blocks.PLANKS, '*', Items.DIAMOND, 'd', ModBlocks.groblinDustBlock);

        System.out.println("Crafting initiated");
    }
}
