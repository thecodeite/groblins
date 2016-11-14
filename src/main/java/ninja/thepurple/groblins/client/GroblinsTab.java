package ninja.thepurple.groblins.client;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import ninja.thepurple.groblins.GroblinsMod;
import ninja.thepurple.groblins.common.items.ModItems;

public class GroblinsTab extends CreativeTabs {
    public GroblinsTab() {
        super(GroblinsMod.MODID);
    }

    @Override
    public Item getTabIconItem() {
        return ModItems.groblinDust;
    }

}