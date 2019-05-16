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
import me.mrCookieSlime.Slimefun.api.Slimefun;
import org.bukkit.Effect;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class OreCrusherHandler extends MultiBlockInteractionHandler {
    @Override
    public String getMachineID() {
        return "ORE_CRUSHER";
    }

    @Override
    public boolean onInteract(Player p, MultiBlock mb, Block b) {
        SlimefunMachine machine = (SlimefunMachine) SlimefunItem.getByID("ORE_CRUSHER");

        if (!mb.isMultiBlock(machine)) {
            return false;
        }

        if (CSCoreLib.getLib().getProtectionManager().canAccessChest(p.getUniqueId(), b, true)) {
            if (Slimefun.hasUnlocked(p, machine.getItem(), true)) {
                Dispenser dispenser = (Dispenser) b.getRelative(BlockFace.DOWN).getState();
                Inventory inv = dispenser.getInventory();
                for (ItemStack current : inv.getContents()) {
                    for (ItemStack convert : RecipeType.getRecipeInputs(machine)) {
                        if (convert != null && SlimefunManager.isItemSimiliar(current, convert, true)) {
                            ItemStack adding = RecipeType.getRecipeOutput(machine, convert);
                            if (InvUtils.fits(inv, adding)) {
                                ItemStack removing = current.clone();
                                removing.setAmount(convert.getAmount());
                                inv.removeItem(removing);
                                inv.addItem(adding);
                                p.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, 1);
                            } else Messages.local.sendTranslation(p, "machines.full-inventory", true);
                            return true;
                        }
                    }
                }
                Messages.local.sendTranslation(p, "machines.unknown-material", true);
            }
        }
        return true;

    }
}
