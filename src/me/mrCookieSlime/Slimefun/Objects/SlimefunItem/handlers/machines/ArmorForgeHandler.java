package me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.machines;

import me.mrCookieSlime.CSCoreLibPlugin.CSCoreLib;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.InvUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.MultiBlock;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunMachine;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.MultiBlockInteractionHandler;
import me.mrCookieSlime.Slimefun.Setup.Messages;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import me.mrCookieSlime.Slimefun.SlimefunStartup;
import me.mrCookieSlime.Slimefun.api.Slimefun;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ArmorForgeHandler extends MultiBlockInteractionHandler {
    private boolean isCraft(Inventory inventory, ItemStack[] inputs){
        for (int j = 0; j < inventory.getContents().length; j++) {
            if (!SlimefunManager.isItemSimiliar(inventory.getContents()[j], inputs[j], true)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getMachineID() {
        return "ARMOR_FORGE";
    }

    @Override
    public boolean onInteract(final Player p, MultiBlock mb, Block b) {

        SlimefunMachine machine = (SlimefunMachine) SlimefunItem.getByID("ARMOR_FORGE");

        if (!mb.isMultiBlock(machine)) {
            return false;
        }

        if (CSCoreLib.getLib().getProtectionManager().canAccessChest(p.getUniqueId(), b, true)) {
            if (Slimefun.hasUnlocked(p, machine.getItem(), true)) {
                Dispenser disp = (Dispenser) b.getRelative(BlockFace.DOWN).getState();
                final Inventory inv = disp.getInventory();
                List<ItemStack[]> inputs = RecipeType.getRecipeInputList(machine);

                for(ItemStack[] stacks:inputs){
                    if (isCraft(inv,stacks)) {
                        final ItemStack adding = RecipeType.getRecipeOutputList(machine, stacks).clone();
                        if (Slimefun.hasUnlocked(p, adding, true)) {
                            if (InvUtils.fits(inv, adding)) {
                                for (ItemStack removing : stacks) {
                                    if (removing != null) inv.removeItem(removing);
                                }
                                for (int j = 0; j < 4; j++) {
                                    int current = j;
                                    Bukkit.getScheduler().runTaskLater(SlimefunStartup.instance, () -> {
                                        if (current < 3) {
                                            p.getWorld().playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 1F, 2F);
                                        } else {
                                            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1F, 1F);
                                            inv.addItem(adding);
                                        }
                                    }, j * 20L);
                                }
                            } else Messages.local.sendTranslation(p, "machines.full-inventory", true);
                        }
                        return true;
                    }
                }
                Messages.local.sendTranslation(p, "machines.pattern-not-found", true);
            }
        }
        return true;
    }

}
