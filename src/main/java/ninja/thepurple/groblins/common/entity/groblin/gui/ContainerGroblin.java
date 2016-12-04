package ninja.thepurple.groblins.common.entity.groblin.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerGroblin extends Container {

    private final IInventory groblinInventory;

    private final int groblinInventorySize;


    public ContainerGroblin(InventoryPlayer playerInventory, IInventory groblinInventory) {

        this.groblinInventory = groblinInventory;

        groblinInventorySize = groblinInventory.getSizeInventory();

        int yPos = 0;
        for (int groblinSlotIndex = 0; groblinSlotIndex < groblinInventorySize; ++groblinSlotIndex)
        {
            int xPos = groblinSlotIndex % 9;
            if (xPos == 0) {
                yPos += 18;
            }

            addSlotToContainer(new Slot(groblinInventory, groblinSlotIndex, 8+xPos*18, yPos));
        }

        yPos += 13 + 18;


        // add player inventory slots
        // note that the slot numbers are within the player inventory so can
        // be same as the tile entity inventory
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                addSlotToContainer(new Slot(playerInventory, j+i*9+9, 8+j*18, yPos));
            }

            yPos += 18;
        }

        yPos += 4;

        // add hotbar slots
        for (int i = 0; i < 9; ++i)
        {
            addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, yPos));
        }
    }


    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return true;
    }


    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int slotIndex)
    {
        ItemStack itemStack1 = null;
        Slot slot = inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemStack2 = slot.getStack();
            itemStack1 = itemStack2.copy();


            if (slotIndex > groblinInventorySize) {
                if (!mergeItemStack(itemStack2, 0, groblinInventorySize, false))
                {
                    return null;
                }
            }
            else if (!mergeItemStack(itemStack2, groblinInventorySize, groblinInventorySize+36, false))
            {
                return null;
            }

            if (itemStack2.stackSize == 0)
            {
                slot.putStack(null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemStack2.stackSize == itemStack1.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(playerIn, itemStack2);
        }

        return itemStack1;
    }
}
