package me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.machines;

import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.Slimefun.SlimefunStartup;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class MagicWorkbenchHandler extends CrafterHandler {
    @Override
    public String getMachineID() {
        return "MAGIC_WORKBENCH";
    }

    @Override
    Dispenser getRelativeDispenser(Block block) {
        if (block.getRelative(1, 0, 0).getType() == Material.DISPENSER)
            return (Dispenser) block.getRelative(1, 0, 0).getState();
        else if (block.getRelative(0, 0, 1).getType() == Material.DISPENSER)
            return (Dispenser) block.getRelative(0, 0, 1).getState();
        else if (block.getRelative(-1, 0, 0).getType() == Material.DISPENSER)
            return (Dispenser) block.getRelative(-1, 0, 0).getState();
        else if (block.getRelative(0, 0, -1).getType() == Material.DISPENSER)
            return (Dispenser) block.getRelative(0, 0, -1).getState();
        return null;
    }

    @Override
    void removeItemsFromDispenser(Inventory inventory) {
        for (int j = 0; j < 9; j++) {
            if (inventory.getContents()[j] != null && inventory.getContents()[j].getType() != Material.AIR) {
                if (inventory.getContents()[j].getAmount() > 1)
                    inventory.setItem(j, new CustomItem(inventory.getContents()[j], inventory.getContents()[j].getAmount() - 1));
                else inventory.setItem(j, null);
            }
        }
    }

    @Override
    void addToDispenserInventory(Inventory inventory, final ItemStack adding, Player player, Block block) {
        for (int j = 0; j < 4; j++) {
            int current = j;
            Bukkit.getScheduler().runTaskLater(SlimefunStartup.instance, () -> {
                player.getWorld().playEffect(block.getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
                player.getWorld().playEffect(block.getLocation(), Effect.ENDER_SIGNAL, 1);
                if (current < 3) {
                    player.getWorld().playSound(block.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1F, 1F);
                } else {
                    player.getWorld().playSound(block.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1F, 1F);
                    inventory.addItem(adding);
                }
            }, j * 20L);
        }
    }


}
