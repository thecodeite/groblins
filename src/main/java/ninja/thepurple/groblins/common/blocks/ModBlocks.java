package ninja.thepurple.groblins.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import java.util.ArrayList;

public class ModBlocks {
    public static ArrayList<Block> allBlocks = new ArrayList<Block>();

    public static Block groblinDustBlock;
    public static BlockGroblinHouse groblinHouse;
    public static BlockGroblinPlasma groblinPlasma;


    public static void createBlocks() {
        groblinDustBlock = registerBlock(new BasicBlock("groblinDustBlock").setLightLevel(1.0f));
        registerBlock(new BasicModelBlock("floatingBall").setLightLevel(1.0f));
        groblinHouse = registerBlock(new BlockGroblinHouse());
        groblinPlasma = registerBlock(new BlockGroblinPlasma());
    }

    private static <B extends Block> B registerBlock(B block) {
        GameRegistry.register(block);
        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(block.getRegistryName());
        GameRegistry.register(itemBlock);
        allBlocks.add(block);
        return block;
    }
}
