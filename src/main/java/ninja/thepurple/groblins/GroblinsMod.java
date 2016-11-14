package ninja.thepurple.groblins;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ninja.thepurple.groblins.client.GroblinsTab;

@Mod(modid = GroblinsMod.MODID, version = GroblinsMod.VERSION)
public class GroblinsMod
{
    public static final String MODID = "groblins";
    public static final String VERSION = "0.1";

    public static final GroblinsTab creativeTab = new GroblinsTab();

    @SidedProxy(clientSide="ninja.thepurple.groblins.ClientProxy", serverSide="ninja.thepurple.groblins.ServerProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        this.proxy.preInit(e);
    }

    @EventHandler
    public void init(FMLInitializationEvent e) {
        this.proxy.init(e);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        this.proxy.postInit(e);
    }
}
