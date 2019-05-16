package me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.machines;

import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.AutonomousMachineHandler;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.MachineID;
import me.mrCookieSlime.Slimefun.SlimefunStartup;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.event.block.BlockDispenseEvent;

public class BlockPlacerHandler extends AutonomousMachineHandler implements MachineID {
    private String[] blacklist;

    public BlockPlacerHandler(String[] blacklist) {
        this.blacklist = blacklist;
    }

    @Override
    public String getMachineID() {
        return "BLOCK_PLACER";
    }

    @Override
    public boolean onBlockDispense(final BlockDispenseEvent e, Block dispenser, final Dispenser d, Block block, Block chest, SlimefunItem machine) {
        if (machine.getID().equalsIgnoreCase(getMachineID())) {
            e.setCancelled(true);
            if ((block.getType() == null || block.getType() == Material.AIR) && e.getItem().getType().isBlock()) {
                for(String blockType : blacklist) {
                    if (e.getItem().getType().toString().equals(blockType)) {
                        return false;
                    }
                }

                SlimefunItem sfItem = SlimefunItem.getByItem(e.getItem());
                if (sfItem != null) {
                    if (!SlimefunItem.blockhandler.containsKey(sfItem.getID())) {
                        block.setType(e.getItem().getType());
                        BlockStorage.store(block, sfItem.getID());
                        block.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, e.getItem().getType());
                        if (d.getInventory().containsAtLeast(e.getItem(), 2)) d.getInventory().removeItem(new CustomItem(e.getItem(), 1));
                        else {
                            Bukkit.getScheduler().runTaskLater(SlimefunStartup.instance, () -> {
                                d.getInventory().removeItem(e.getItem());
                            }, 2L);
                        }
                    }
                }
                else {
                    block.setType(e.getItem().getType());
                    block.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, e.getItem().getType());
                    if (d.getInventory().containsAtLeast(e.getItem(), 2)) d.getInventory().removeItem(new CustomItem(e.getItem(), 1));
                    else {
                        Bukkit.getScheduler().runTaskLater(SlimefunStartup.instance, () -> {
                            d.getInventory().removeItem(e.getItem());
                        }, 2L);
                    }
                }
            }
            return true;
        }
        else return false;
    }
}
