package me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.items.tools;

import me.mrCookieSlime.CSCoreLibPlugin.CSCoreLib;
import me.mrCookieSlime.CSCoreLibPlugin.compatibility.MaterialHelper;
import me.mrCookieSlime.CSCoreLibPlugin.general.Block.TreeCalculator;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.BlockBreakHandler;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class LumberAxeHandler extends BlockBreakHandler {
    @Override
    public boolean onBlockBreak(BlockBreakEvent e, ItemStack item, int fortune, List<ItemStack> drops) {
        if (SlimefunManager.isItemSimiliar(e.getPlayer().getInventory().getItemInMainHand(), SlimefunItems.LUMBER_AXE, true)) {
            if (MaterialHelper.isLog( e.getBlock().getType())) {
                List<Location> logs = new ArrayList<>();
                TreeCalculator.getTree(e.getBlock().getLocation(), e.getBlock().getLocation(), logs);

                if (logs.contains(e.getBlock().getLocation())) logs.remove(e.getBlock().getLocation());
                for (Location b: logs) {
                    if (CSCoreLib.getLib().getProtectionManager().canBuild(e.getPlayer().getUniqueId(), b.getBlock())) {
                        b.getWorld().playEffect(b, Effect.STEP_SOUND, b.getBlock().getType());
                        for (ItemStack drop: b.getBlock().getDrops()) {
                            b.getWorld().dropItemNaturally(b, drop);
                        }
                        b.getBlock().setType(Material.AIR);
                    }
                }
            }
            return true;
        }
        else return false;
    }
}
