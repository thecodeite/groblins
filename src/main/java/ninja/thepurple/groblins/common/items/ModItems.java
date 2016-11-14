package ninja.thepurple.groblins.common.items;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;

public final class ModItems {
    public static ArrayList<Item> allItems = new ArrayList<Item>();
    public static Item groblinDust;
    public static ItemGroblinHand groblinHand;

    public static void createItems() {
        groblinDust = registerItem(new BasicItem("groblinDust"));
        groblinHand = registerItem(new ItemGroblinHand());
    }

    private static <I extends Item> I registerItem(I item) {
        GameRegistry.register(item);
        allItems.add(item);
        return item;
    }
}
