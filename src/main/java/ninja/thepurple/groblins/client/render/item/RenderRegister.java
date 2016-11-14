package ninja.thepurple.groblins.client.render.item;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import ninja.thepurple.groblins.GroblinsMod;
import ninja.thepurple.groblins.common.blocks.ModBlocks;
import ninja.thepurple.groblins.common.items.ModItems;

public final class RenderRegister {
    public static String modId = GroblinsMod.MODID;

    public static void registerItems() {
        for (Item i : ModItems.allItems) {
            reg(i);
        }

        for (Block block: ModBlocks.allBlocks) {
            reg(block);
        }
        
    }

    private static void reg(Item item) {
        String location = modId + ":" + item.getUnlocalizedName().substring(5);
        System.out.println("location = " + location);
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
                .register(item, 0, new ModelResourceLocation(location, "inventory"));
    }

    private static void reg(Block block) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
                .register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(modId + ":" + block.getUnlocalizedName().substring(5), "inventory"));
    }
}
