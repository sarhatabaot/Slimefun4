package me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.machines;

import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunBackpack;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.MultiBlockInteractionHandler;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Abstract class for custom crafter handlers
 * Currently for EnhancedCraftingTable and MagicWorkbench
 */
public abstract class CrafterHandler extends MultiBlockInteractionHandler {
    protected boolean isCraft(Inventory inventory, ItemStack[] itemStacks) {
        for (int j = 0; j < inventory.getContents().length; j++) {
            if (!SlimefunManager.isItemSimiliar(inventory.getContents()[j], itemStacks[j], true)) {
                return false;
                /*if (SlimefunItem.getByItem(itemStacks[j]) instanceof SlimefunBackpack &&
                        !SlimefunManager.isItemSimiliar(inventory.getContents()[j], itemStacks[j], false)) {
                    return false; // TODO. Wait, return false either way??
                } else {
                    return false;
                }*/
            }
        }
        return true;
    }
}
