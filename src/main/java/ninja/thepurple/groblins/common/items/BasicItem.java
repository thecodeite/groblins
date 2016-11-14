package ninja.thepurple.groblins.common.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import ninja.thepurple.groblins.GroblinsMod;

public class BasicItem extends Item {

    public BasicItem() {
        super();
    }

    public BasicItem(String unlocalizedName) {
        this();

        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(unlocalizedName);
        setCreativeTab(GroblinsMod.creativeTab);
    }
}