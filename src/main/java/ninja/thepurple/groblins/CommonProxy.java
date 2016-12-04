package ninja.thepurple.groblins;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import ninja.thepurple.groblins.common.blocks.ModBlocks;
import ninja.thepurple.groblins.common.entity.ModEntities;
import ninja.thepurple.groblins.common.entity.groblin.gui.GuiHandler;
import ninja.thepurple.groblins.common.items.ModItems;
import ninja.thepurple.groblins.common.crafting.ModCrafting;
import ninja.thepurple.groblins.common.rituals.ModRituals;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e) {
        ModItems.createItems();
        ModBlocks.createBlocks();
        ModEntities.init();
        ModRituals.createRituals();
    }

    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(GroblinsMod.instance,
                new GuiHandler());
        ModCrafting.initCrafting();
    }

    public void postInit(FMLPostInitializationEvent e) {

    }
}