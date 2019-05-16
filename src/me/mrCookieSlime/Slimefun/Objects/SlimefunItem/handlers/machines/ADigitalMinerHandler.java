package me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.machines;

import me.mrCookieSlime.CSCoreLibPlugin.CSCoreLib;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.InvUtils;
import me.mrCookieSlime.Slimefun.Objects.MultiBlock;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunMachine;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.MultiBlockInteractionHandler;
import me.mrCookieSlime.Slimefun.Setup.Messages;
import me.mrCookieSlime.Slimefun.SlimefunStartup;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.Slimefun;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class ADigitalMinerHandler extends MultiBlockInteractionHandler {
    @Override
    public boolean onInteract(Player p, MultiBlock mb, Block b) {
        SlimefunMachine machine = (SlimefunMachine) SlimefunItem.getByID(getMachineID());
        if (!mb.isMultiBlock(machine))
            return false;
        if (CSCoreLib.getLib().getProtectionManager().canAccessChest(p.getUniqueId(), b, true)) {
            if (CSCoreLib.getLib().getProtectionManager().canAccessChest(p.getUniqueId(), b, true)
                    && Slimefun.hasUnlocked(p, machine.getItem(), true)) {
                Block chestBlock = b.getRelative(BlockFace.UP);

                if (!(BlockStorage.check(chestBlock.getRelative(BlockFace.WEST), "SOLAR_PANEL") && BlockStorage.check(chestBlock.getRelative(BlockFace.EAST), "SOLAR_PANEL")) &&
                        !(BlockStorage.check(chestBlock.getRelative(BlockFace.NORTH), "SOLAR_PANEL") && BlockStorage.check(chestBlock.getRelative(BlockFace.SOUTH), "SOLAR_PANEL"))) {
                    return false;
                }
                Chest chest = (Chest) chestBlock.getState();
                final Inventory inv = chest.getInventory();
                List<Location> ores = new ArrayList<>();
                for (int x = b.getX() - 6; x <= b.getX() + 6; x++) {
                    for (int z = b.getZ() - 6; z <= b.getZ() + 6; z++) {
                        for (int y = b.getY(); y > 0; y--) {
                            if (b.getWorld().getBlockAt(x, y, z).getType().toString().endsWith("_ORE")) {
                                ores.add(b.getWorld().getBlockAt(x, y, z).getLocation());
                            }
                        }
                    }
                }

                if (!ores.isEmpty()) {
                    final Material ore = ores.get(0).getBlock().getType();
                    final ItemStack adding = getDrop(ores,ore);
                    ores.get(0).getBlock().setType(Material.AIR);
                    ores.clear();
                    if (InvUtils.fits(inv, adding)) {
                        for (int i = 0; i < 4; i++) {
                            int j = i;
                            Bukkit.getScheduler().runTaskLater(SlimefunStartup.instance, () -> {
                                if (j < 3) {
                                    b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, ore);
                                } else {
                                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1F, 1F);
                                    inv.addItem(adding);
                                }
                            }, i*20L);
                        }
                    }
                    else Messages.local.sendTranslation(p, "machines.full-inventory", true);
                } else Messages.local.sendTranslation(p, "miner.no-ores", true);

            }
        }
        return true;
    }

    public abstract ItemStack getDrop(List<Location> ores, Material ore);
}

