package me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.machines;

import me.mrCookieSlime.CSCoreLibPlugin.CSCoreLib;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.InvUtils;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.MultiBlock;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunMachine;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.MultiBlockInteractionHandler;
import me.mrCookieSlime.Slimefun.Setup.Messages;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import me.mrCookieSlime.Slimefun.Setup.SlimefunSetup;
import me.mrCookieSlime.Slimefun.SlimefunStartup;
import me.mrCookieSlime.Slimefun.api.Slimefun;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;

public class OreWasherHandler extends MultiBlockInteractionHandler {
    @Override
    public String getMachineID() {
        return "ORE_WASHER";
    }

    private ItemStack getRandomDust() {
        //define in cache, or before hand to reduce creation of an arraylist every time
        ArrayList<ItemStack> dusts = new ArrayList<>();
        dusts.add(SlimefunItems.GOLD_DUST);
        dusts.add(SlimefunItems.ALUMINUM_DUST);
        dusts.add(SlimefunItems.COPPER_DUST);
        dusts.add(SlimefunItems.ZINC_DUST);
        dusts.add(SlimefunItems.TIN_DUST);
        dusts.add(SlimefunItems.MAGNESIUM_DUST);
        dusts.add(SlimefunItems.LEAD_DUST);
        dusts.add(SlimefunItems.SILVER_DUST);

        Random random = new Random();
        int index = random.nextInt(dusts.size());

        if (SlimefunStartup.chance(100, 25))
            return dusts.get(index);
        return SlimefunItems.IRON_DUST;
    }

    private void siftedOre(Inventory inventory,Player player,Block block, ItemStack current){
        ItemStack adding = getRandomDust();
        if (inventory.firstEmpty() != -1 || (SlimefunSetup.legacy_ore_washer && InvUtils.fits(inventory, adding))) {
            ItemStack removing = current.clone();
            removing.setAmount(1);
            inventory.removeItem(removing);
            inventory.addItem(adding);
            player.getWorld().playSound(block.getLocation(), Sound.ENTITY_PLAYER_SPLASH, 1, 1);
            player.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, Material.WATER);
            if (InvUtils.fits(inventory, SlimefunItems.STONE_CHUNK))
                inventory.addItem(SlimefunItems.STONE_CHUNK);
        } else Messages.local.sendTranslation(player, "machines.full-inventory", true);
    }

    private void sand(Inventory inventory,Player player,Block block, ItemStack current){
        ItemStack adding = SlimefunItems.SALT;
        if (InvUtils.fits(inventory, adding)) {
            ItemStack removing = current.clone();
            removing.setAmount(4);
            inventory.removeItem(removing);
            inventory.addItem(adding);
            player.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, Material.WATER);
            player.getWorld().playSound(block.getLocation(), Sound.ENTITY_PLAYER_SPLASH, 1, 1);
        } else Messages.local.sendTranslation(player, "machines.full-inventory", true);
    }

    private void pulverizedOre(Inventory inventory, Player player, Block block, ItemStack current){
        ItemStack adding = SlimefunItems.PURE_ORE_CLUSTER;
        if (InvUtils.fits(inventory, adding)) {
            ItemStack removing = current.clone();
            removing.setAmount(1);
            inventory.removeItem(removing);
            inventory.addItem(adding);
            player.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, Material.WATER);
            player.getWorld().playSound(block.getLocation(), Sound.ENTITY_PLAYER_SPLASH, 1, 1);
        } else Messages.local.sendTranslation(player, "machines.full-inventory", true);
    }

    @Override
    public boolean onInteract(Player p, MultiBlock mb, Block b) {
        SlimefunMachine machine = (SlimefunMachine) SlimefunItem.getByID(getMachineID());

        if (!mb.isMultiBlock(machine)) {
            return false;
        }

        if (CSCoreLib.getLib().getProtectionManager().canAccessChest(p.getUniqueId(), b, true)) {
            if (Slimefun.hasUnlocked(p, machine.getItem(), true)) {
                Dispenser dispenser = (Dispenser) b.getRelative(BlockFace.UP).getState();
                Inventory inv = dispenser.getInventory();
                for (ItemStack current : inv.getContents()) {
                    if (current != null) {
                        if (SlimefunManager.isItemSimiliar(current, SlimefunItems.SIFTED_ORE, true)) {
                            siftedOre(inv,p,b,current);
                            return true;
                        } else if (SlimefunManager.isItemSimiliar(current, new ItemStack(Material.SAND, 4), false)) {
                            sand(inv,p,b,current);
                            return true;
                        } else if (SlimefunManager.isItemSimiliar(current, SlimefunItems.PULVERIZED_ORE, true)) {
                            pulverizedOre(inv,p,b,current);
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
