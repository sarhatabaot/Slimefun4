package me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.items.tools;

import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.BlockBreakHandler;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class HerculesPickaxeHandler extends BlockBreakHandler {
    @Override
    public boolean onBlockBreak(BlockBreakEvent e, ItemStack item, int fortune, List<ItemStack> drops) {
        if (SlimefunManager.isItemSimiliar(e.getPlayer().getInventory().getItemInMainHand(), SlimefunItems.HERCULES_PICKAXE, true) && e.getBlock().getType().toString().endsWith("_ORE")) {
            if (e.getBlock().getType() == Material.IRON_ORE) drops.add(new CustomItem(SlimefunItems.IRON_DUST, 2));
            else if (e.getBlock().getType() == Material.GOLD_ORE) drops.add(new CustomItem(SlimefunItems.GOLD_DUST, 2));
            else {
                for (ItemStack drop: e.getBlock().getDrops()) {
                    drops.add(new CustomItem(drop, 2));
                }
            }
            return true;
        }
        else return false;
    }
}
