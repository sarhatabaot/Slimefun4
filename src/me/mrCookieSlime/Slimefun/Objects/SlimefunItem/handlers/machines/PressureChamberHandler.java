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
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class PressureChamberHandler extends MultiBlockInteractionHandler {
    @Override
    public String getMachineID() {
        return "PRESSURE_CHAMBER";
    }

    @Override
    public boolean onInteract(final Player p, MultiBlock mb, final Block b) {

        SlimefunMachine machine = (SlimefunMachine) SlimefunItem.getByID("PRESSURE_CHAMBER");

        if (!mb.isMultiBlock(machine)) {
            return false;
        }
        if (CSCoreLib.getLib().getProtectionManager().canAccessChest(p.getUniqueId(), b, true)) {
            if (Slimefun.hasUnlocked(p, machine.getItem(), true)) {
                Dispenser disp = (Dispenser) b.getRelative(BlockFace.UP).getRelative(BlockFace.UP).getState();
                final Inventory inv = disp.getInventory();
                for (ItemStack current : inv.getContents()) {
                    for (ItemStack convert : RecipeType.getRecipeInputs(machine)) {
                        if (convert != null && SlimefunManager.isItemSimiliar(current, convert, true)) {
                            final ItemStack adding = RecipeType.getRecipeOutput(machine, convert);
                            if (InvUtils.fits(inv, adding)) {
                                ItemStack removing = current.clone();
                                removing.setAmount(convert.getAmount());
                                inv.removeItem(removing);
                                for (int i = 0; i < 4; i++) {
                                    int j = i;
                                    Bukkit.getScheduler().runTaskLater(SlimefunStartup.instance, () -> {
                                        p.getWorld().playSound(b.getLocation(), Sound.ENTITY_TNT_PRIMED, 1, 1);
                                        p.getWorld().playEffect(b.getRelative(BlockFace.UP).getLocation(), Effect.SMOKE, 4);
                                        p.getWorld().playEffect(b.getRelative(BlockFace.UP).getLocation(), Effect.SMOKE, 4);
                                        p.getWorld().playEffect(b.getRelative(BlockFace.UP).getLocation(), Effect.SMOKE, 4);
                                        if (j < 3) {
                                            p.getWorld().playSound(b.getLocation(), Sound.ENTITY_TNT_PRIMED, 1F, 1F);
                                        } else {
                                            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1F, 1F);
                                            inv.addItem(adding);
                                        }
                                    }, i * 20L);
                                }
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
