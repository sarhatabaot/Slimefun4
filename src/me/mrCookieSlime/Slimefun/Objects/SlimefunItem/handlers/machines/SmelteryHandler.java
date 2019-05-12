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
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.Slimefun;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Hopper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * @author sarhatabaot
 */
public class SmelteryHandler extends MultiBlockInteractionHandler {
    @Override
    public boolean onInteract(Player p, MultiBlock mb, Block b) {
        SlimefunMachine machine = (SlimefunMachine) SlimefunItem.getByID("SMELTERY");

        if (!mb.isMultiBlock(machine)) {
            return false;
        }

        if (CSCoreLib.getLib().getProtectionManager().canAccessChest(p.getUniqueId(), b, true)) {
            if (Slimefun.hasUnlocked(p, machine.getItem(), true)) {
                Dispenser disp = (Dispenser) b.getRelative(BlockFace.DOWN).getState();
                Inventory inv = disp.getInventory();
                List<ItemStack[]> inputs = RecipeType.getRecipeInputList(machine);

                for (int i = 0; i < inputs.size(); i++) {
                    boolean craft = true;
                    for (ItemStack converting : inputs.get(i)) {
                        if (converting != null) {
                            for (int j = 0; j < inv.getContents().length; j++) {
                                if (j == (inv.getContents().length - 1) && !SlimefunManager.isItemSimiliar(converting, inv.getContents()[j], true, SlimefunManager.DataType.ALWAYS)) {
                                    craft = false;
                                    break;
                                } else if (SlimefunManager.isItemSimiliar(inv.getContents()[j], converting, true, SlimefunManager.DataType.ALWAYS))
                                    break;
                            }
                        }
                    }

                    if (craft) {
                        ItemStack adding = RecipeType.getRecipeOutputList(machine, inputs.get(i)).clone();
                        if (Slimefun.hasUnlocked(p, adding, true)) {
                            if (InvUtils.fits(inv, adding)) {
                                for (ItemStack removing : inputs.get(i)) {
                                    if (removing != null) inv.removeItem(removing);
                                }
                                inv.addItem(adding);
                                p.getWorld().playSound(p.getLocation(), Sound.BLOCK_LAVA_POP, 1, 1);
                                p.getWorld().playEffect(b.getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
                                Block raw_disp = b.getRelative(BlockFace.DOWN);
                                Hopper chamber = null;
                                if (BlockStorage.check(raw_disp.getRelative(BlockFace.EAST).getState().getBlock(), "IGNITION_CHAMBER")) {
                                    chamber = (Hopper) raw_disp.getRelative(BlockFace.EAST).getState();
                                } else if (BlockStorage.check(raw_disp.getRelative(BlockFace.WEST).getState().getBlock(), "IGNITION_CHAMBER")) {
                                    chamber = (Hopper) raw_disp.getRelative(BlockFace.WEST).getState();
                                } else if (BlockStorage.check(raw_disp.getRelative(BlockFace.NORTH).getState().getBlock(), "IGNITION_CHAMBER")) {
                                    chamber = (Hopper) raw_disp.getRelative(BlockFace.NORTH).getState();
                                } else if (BlockStorage.check(raw_disp.getRelative(BlockFace.SOUTH).getState().getBlock(), "IGNITION_CHAMBER")) {
                                    chamber = (Hopper) raw_disp.getRelative(BlockFace.SOUTH).getState();
                                }

                                if (SlimefunStartup.chance(100, (Integer) Slimefun.getItemValue("SMELTERY", "chance.fireBreak"))) {
                                    if (chamber != null) {
                                        if (chamber.getInventory().contains(Material.FLINT_AND_STEEL)) {
                                            ItemStack item = chamber.getInventory().getItem(chamber.getInventory().first(Material.FLINT_AND_STEEL));
                                            ItemMeta meta = item.getItemMeta();
                                            ((Damageable) meta).setDamage(((Damageable) meta).getDamage() + 1);
                                            item.setItemMeta(meta);
                                            if (((Damageable) item.getItemMeta()).getDamage() >= item.getType().getMaxDurability()) {
                                                item.setAmount(0);
                                                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
                                            }
                                            p.getWorld().playSound(p.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 1, 1);
                                        } else {
                                            Messages.local.sendTranslation(p, "machines.ignition-chamber-no-flint", true);

                                            Block fire = b.getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN);
                                            fire.getWorld().playEffect(fire.getLocation(), Effect.STEP_SOUND, fire.getType());
                                            fire.setType(Material.AIR);
                                        }
                                    } else {
                                        Block fire = b.getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN);
                                        fire.getWorld().playEffect(fire.getLocation(), Effect.STEP_SOUND, fire.getType());
                                        fire.setType(Material.AIR);
                                    }
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
