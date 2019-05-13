package me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.machines;

import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EnhancedCraftingTableHandler extends CrafterHandler {
    @Override
    String getMachineID() {
        return "ENHANCED_CRAFTING_TABLE";
    }

    @Override
    Dispenser getRelativeDispenser(Block block) {
        return (Dispenser) block.getRelative(BlockFace.DOWN).getState();
    }

    @Override
    void removeItemsFromDispenser(Inventory inventory) {
        for (int j = 0; j < 9; j++) {
            if (inventory.getContents()[j] != null
                    && inventory.getContents()[j].getType() != Material.AIR) {
                if (inventory.getContents()[j].getType().toString().endsWith("_BUCKET"))
                    inventory.setItem(j, new ItemStack(Material.BUCKET));
                else if (inventory.getContents()[j].getAmount() > 1)
                    inventory.setItem(j, new CustomItem(inventory.getContents()[j], inventory.getContents()[j].getAmount() - 1));
                else inventory.setItem(j, null);
            }
        }
    }

    @Override
    void addToDispenserInventory(Inventory inventory, ItemStack adding, Player player, Block block) {
        player.getWorld().playSound(block.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1, 1);
        inventory.addItem(adding);
    }
}
