package me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.machines;

import me.mrCookieSlime.CSCoreLibPlugin.CSCoreLib;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.InvUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.MultiBlock;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunBackpack;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunMachine;
import me.mrCookieSlime.Slimefun.Setup.Messages;
import me.mrCookieSlime.Slimefun.SlimefunStartup;
import me.mrCookieSlime.Slimefun.api.Backpacks;
import me.mrCookieSlime.Slimefun.api.Slimefun;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.List;


public class MagicWorkbenchHandler extends CrafterHandler {
    @Override
    public boolean onInteract(final Player p, MultiBlock mb, final Block b) {

        SlimefunMachine machine = (SlimefunMachine) SlimefunItem.getByID("MAGIC_WORKBENCH");

        if (!mb.isMultiBlock(machine)) {
            return false;
        }
        if (CSCoreLib.getLib().getProtectionManager().canAccessChest(p.getUniqueId(), b, true)) {
            if (Slimefun.hasUnlocked(p, machine.getItem(), true)) {
                Dispenser disp = null;

                if (b.getRelative(1, 0, 0).getType() == Material.DISPENSER)
                    disp = (Dispenser) b.getRelative(1, 0, 0).getState();
                else if (b.getRelative(0, 0, 1).getType() == Material.DISPENSER)
                    disp = (Dispenser) b.getRelative(0, 0, 1).getState();
                else if (b.getRelative(-1, 0, 0).getType() == Material.DISPENSER)
                    disp = (Dispenser) b.getRelative(-1, 0, 0).getState();
                else if (b.getRelative(0, 0, -1).getType() == Material.DISPENSER)
                    disp = (Dispenser) b.getRelative(0, 0, -1).getState();

                final Inventory inv = disp.getInventory();
                List<ItemStack[]> inputs = RecipeType.getRecipeInputList(machine);
                //TODO: very similar to EnhancedWorkBenchHandler
                for (ItemStack[] stacks:inputs) {
                    if (isCraft(inv, stacks)) {
                        final ItemStack adding = RecipeType.getRecipeOutputList(machine, stacks.clone());
                        if (Slimefun.hasUnlocked(p, adding, true)) {
                            Inventory inv2 = Bukkit.createInventory(null, 9, "test");
                            for (int j = 0; j < inv.getContents().length; j++) {
                                inv2.setItem(j, inv.getContents()[j] != null ? (inv.getContents()[j].getAmount() > 1 ? new CustomItem(inv.getContents()[j], inv.getContents()[j].getAmount() - 1) : null) : null);
                            }
                            if (InvUtils.fits(inv2, adding)) {
                                SlimefunItem sfItem = SlimefunItem.getByItem(adding);

                                if (sfItem instanceof SlimefunBackpack) {
                                    ItemStack backpack = null;

                                    for (int j = 0; j < 9; j++) {
                                        if (inv.getContents()[j] != null) {
                                            if (inv.getContents()[j].getType() != Material.AIR) {
                                                if (SlimefunItem.getByItem(inv.getContents()[j]) instanceof SlimefunBackpack) {
                                                    backpack = inv.getContents()[j];
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    String id = "";
                                    int size = ((SlimefunBackpack) sfItem).size;

                                    if (backpack != null) {
                                        for (String line : backpack.getItemMeta().getLore()) {
                                            if (line.startsWith(ChatColor.translateAlternateColorCodes('&', "&7ID: ")) && line.contains("#")) {
                                                id = line.replace(ChatColor.translateAlternateColorCodes('&', "&7ID: "), "");
                                                Config cfg = new Config(new File("data-storage/Slimefun/Players/" + id.split("#")[0] + ".yml"));
                                                cfg.setValue("backpacks." + id.split("#")[1] + ".size", size);
                                                cfg.save();
                                                break;
                                            }
                                        }
                                    }

                                    if (id.equals("")) {
                                        for (int line = 0; line < adding.getItemMeta().getLore().size(); line++) {
                                            if (adding.getItemMeta().getLore().get(line).equals(ChatColor.translateAlternateColorCodes('&', "&7ID: <ID>"))) {
                                                ItemMeta im = adding.getItemMeta();
                                                List<String> lore = im.getLore();
                                                lore.set(line, lore.get(line).replace("<ID>", Backpacks.createBackpack(p, size)));
                                                im.setLore(lore);
                                                adding.setItemMeta(im);
                                                break;
                                            }
                                        }
                                    } else {
                                        for (int line = 0; line < adding.getItemMeta().getLore().size(); line++) {
                                            if (adding.getItemMeta().getLore().get(line).equals(ChatColor.translateAlternateColorCodes('&', "&7ID: <ID>"))) {
                                                ItemMeta im = adding.getItemMeta();
                                                List<String> lore = im.getLore();
                                                lore.set(line, lore.get(line).replace("<ID>", id));
                                                im.setLore(lore);
                                                adding.setItemMeta(im);
                                                break;
                                            }
                                        }
                                    }
                                }

                                for (int j = 0; j < 9; j++) {
                                    if (inv.getContents()[j] != null) {
                                        if (inv.getContents()[j].getType() != Material.AIR) {
                                            if (inv.getContents()[j].getAmount() > 1)
                                                inv.setItem(j, new CustomItem(inv.getContents()[j], inv.getContents()[j].getAmount() - 1));
                                            else inv.setItem(j, null);
                                        }
                                    }
                                }
                                for (int j = 0; j < 4; j++) {
                                    int current = j;
                                    Bukkit.getScheduler().runTaskLater(SlimefunStartup.instance, () -> {
                                        p.getWorld().playEffect(b.getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
                                        p.getWorld().playEffect(b.getLocation(), Effect.ENDER_SIGNAL, 1);
                                        if (current < 3) {
                                            p.getWorld().playSound(b.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1F, 1F);
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
