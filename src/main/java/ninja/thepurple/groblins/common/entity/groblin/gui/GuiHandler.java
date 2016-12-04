package ninja.thepurple.groblins.common.entity.groblin.gui;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ninja.thepurple.groblins.GroblinsMod;
import ninja.thepurple.groblins.common.entity.groblin.EntityGroblin;

@SideOnly(Side.CLIENT)
public class GuiHandler implements IGuiHandler
{

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player,
                                      World world, int x, int y, int z)
    {
        if (ID == GroblinsMod.GUI_ENUM.GROBLIN.ordinal())
        {
            Entity entity = world.getEntityByID(x);
            if (entity instanceof EntityGroblin) {
                return new ContainerGroblin(player.inventory, ((EntityGroblin)entity).getGroblinInventory());
            }
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player,
                                      World world, int x, int y, int z)
    {

        if (ID == GroblinsMod.GUI_ENUM.GROBLIN.ordinal())
        {
            Entity entity = world.getEntityByID(x);
            if (entity instanceof EntityGroblin) {
                return new GuiGroblin(player.inventory, ((EntityGroblin)entity).getGroblinInventory());
            }
        }

        return null;
    }
}
