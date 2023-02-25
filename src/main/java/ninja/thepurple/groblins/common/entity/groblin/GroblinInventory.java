package ninja.thepurple.groblins.common.entity.groblin;

import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

import java.util.HashMap;

class GroblinInventory extends InventoryBasic {
    private final int slotsCount;
    private HashMap<String, Integer> bestHarvestLevel;

    GroblinInventory(String title, boolean customName, int slotCount) {
        super(title, customName, slotCount);
        this.slotsCount = slotCount;
        this.bestHarvestLevel = new HashMap<>();

        super.addInventoryChangeListener(invBasic -> {
            int bestAny = 0;

            for (int i = 0; i < this.slotsCount; ++i) {
                ItemStack itemStack= this.getStackInSlot(i);

                if(itemStack != null) {
                    Item item = itemStack.getItem();

                    if (item instanceof ItemTool) {
                        ItemTool itemTool = (ItemTool)item;

                        for(String toolClass : itemTool.getToolClasses(itemStack)) {
                            int best = bestHarvestLevel.containsKey(toolClass) ? bestHarvestLevel.get(toolClass) : 0;
                            int thisTool = itemTool.getHarvestLevel(itemStack, toolClass);

                            if (thisTool > best) {
                                bestHarvestLevel.put(toolClass, thisTool);
                            }

                            if (thisTool > bestAny) {
                                bestAny = thisTool;
                            }
                        }
                    }

                }

            }


            int bestAll = bestAny;
            for(int level : bestHarvestLevel.values()) {
                if (level < bestAll) {
                    bestAll = level;
                }
            }

            bestHarvestLevel.put("any", bestAny);
            bestHarvestLevel.put("all", bestAll);
        });
    }


    public int getBestToolLevel(String toolClass) {
        return bestHarvestLevel.containsKey(toolClass) ? bestHarvestLevel.get(toolClass) : 0;
    }
}
