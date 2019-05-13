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
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.MultiBlockInteractionHandler;
import me.mrCookieSlime.Slimefun.Setup.Messages;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import me.mrCookieSlime.Slimefun.api.Backpacks;
import me.mrCookieSlime.Slimefun.api.Slimefun;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.List;

/**
 * Abstract class for custom crafter handlers
 * Currently for EnhancedCraftingTable and MagicWorkbench
 */
public abstract class CrafterHandler extends MultiBlockInteractionHandler {
    /**
     *
     * @param backpack
     * @param size
     * @return new id
     */
    private String saveUpgradedBackpack(ItemStack backpack, int size){
        String id;
        if (backpack != null) { //save upgraded backpack to player information
            for (String line : backpack.getItemMeta().getLore()) {
                if (line.startsWith(ChatColor.translateAlternateColorCodes('&', "&7ID: ")) && line.contains("#")) {
                    id = line.replace(ChatColor.translateAlternateColorCodes('&', "&7ID: "), "");
                    Config cfg = new Config(new File("data-storage/Slimefun/Players/" + id.split("#")[0] + ".yml"));
                    cfg.setValue("backpacks." + id.split("#")[1] + ".size", size);
                    cfg.save();
                    return id;
                }
            }
        }
        return "";
    }

    private void createNewBackpack(Player player, int size, ItemStack adding){
        for (int line = 0; line < adding.getItemMeta().getLore().size(); line++) {
            if (adding.getItemMeta().getLore().get(line).equals(ChatColor.translateAlternateColorCodes('&', "&7ID: <ID>"))) {
                ItemMeta im = adding.getItemMeta();
                List<String> lore = im.getLore();
                lore.set(line, lore.get(line).replace("<ID>", Backpacks.createBackpack(player, size)));
                im.setLore(lore);
                adding.setItemMeta(im);
                break;
            }
        }
    }

    private void updateUpgradedBackpack(ItemStack adding, String id){
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

    private void addToInventory(Player player, Inventory inventory, Block block, final ItemStack adding){
        SlimefunItem sfItem = SlimefunItem.getByItem(adding);

        if (sfItem instanceof SlimefunBackpack) {
            ItemStack backpack = getBackpack(inventory);

            int size = ((SlimefunBackpack) sfItem).size;

            String backpackID = saveUpgradedBackpack(backpack,size);

            if (backpackID.isEmpty()) { //create a new backpack
                createNewBackpack(player,size,adding);
            } else { //update upgraded backpack meta
                updateUpgradedBackpack(adding,backpackID);
            }
        }

        removeItemsFromDispenser(inventory);
        addToDispenserInventory(inventory,adding,player, block);
    }

    abstract void addToDispenserInventory(Inventory inventory, final ItemStack adding, Player player, Block block);

    @Override
    public boolean onInteract(Player p, MultiBlock mb, Block b) {
        SlimefunMachine machine = (SlimefunMachine) SlimefunItem.getByID(getMachineID());
        if (!mb.isMultiBlock(machine)) {
            return false;
        }
        if (CSCoreLib.getLib().getProtectionManager().canAccessChest(p.getUniqueId(), b, true)
                && Slimefun.hasUnlocked(p, machine.getItem(), true)) {

            Dispenser dispenser = getRelativeDispenser(b);

            final Inventory inv = dispenser.getInventory();
            List<ItemStack[]> inputs = RecipeType.getRecipeInputList(machine);

            for (ItemStack[] itemStacks : inputs) {
                if (isCraft(inv, itemStacks)) {
                    final ItemStack adding = RecipeType.getRecipeOutputList(machine, itemStacks.clone());
                    if (Slimefun.hasUnlocked(p, adding, true)) {
                        Inventory inv2 = Bukkit.createInventory(null, 9, "test");
                        for (int j = 0; j < inv.getContents().length; j++) {
                            inv2.setItem(j, inv.getContents()[j] != null ? (inv.getContents()[j].getAmount() > 1 ? new CustomItem(inv.getContents()[j], inv.getContents()[j].getAmount() - 1) : null) : null);
                        }
                        if (InvUtils.fits(inv2, adding)) {
                            addToInventory(p,inv,b,adding);
                        } else Messages.local.sendTranslation(p, "machines.full-inventory", true);
                    }
                    return true;
                }
            }
            Messages.local.sendTranslation(p, "machines.pattern-not-found", true);
        }
        return true;
    }

    /**
     *
     * @param inventory
     * @param itemStacks
     * @return
     */
    private boolean isCraft(Inventory inventory, ItemStack[] itemStacks) {
        for (int j = 0; j < inventory.getContents().length; j++) {
            if (!SlimefunManager.isItemSimiliar(inventory.getContents()[j], itemStacks[j], true)) {
                return false;
            }
        }
        return true;
    }


    /**
     *
     * @param inventory
     * @return
     */
    private ItemStack getBackpack(Inventory inventory) {
        for (int j = 0; j < 9; j++) {
            if (inventory.getContents()[j] != null) {
                if (inventory.getContents()[j].getType() != Material.AIR) {
                    if (SlimefunItem.getByItem(inventory.getContents()[j]) instanceof SlimefunBackpack) {
                        return inventory.getContents()[j];
                    }
                }
            }
        }
        return null;
    }

    /**
     *
     * @param block block to check
     * @return Dispenser from relative block
     */
    abstract Dispenser getRelativeDispenser(Block block);

    /**
     *
     * @return String machineID
     */
    abstract String getMachineID();

    abstract void removeItemsFromDispenser(Inventory inventory);
}
