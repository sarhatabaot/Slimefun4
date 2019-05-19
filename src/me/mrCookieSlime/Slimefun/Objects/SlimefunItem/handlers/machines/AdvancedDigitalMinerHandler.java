package me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.machines;

import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import org.bukkit.*;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class AdvancedDigitalMinerHandler extends ADigitalMinerHandler {
    @Override
    public String getMachineID() {
        return "ADVANCED_DIGITAL_MINER";
    }

    @Override
    public ItemStack getDrop(List<Location> ores, Material ore) {
        switch (ore) {
            case COAL_ORE:
                return new ItemStack(Material.COAL, 4);
            case IRON_ORE:
                return new CustomItem(SlimefunItems.IRON_DUST, 2);
            case GOLD_ORE:
                return new CustomItem(SlimefunItems.GOLD_DUST, 2);
            case REDSTONE_ORE:
                return new ItemStack(Material.REDSTONE, 8);
            case NETHER_QUARTZ_ORE:
                return new ItemStack(Material.QUARTZ, 4);
            case LAPIS_ORE:
                return new ItemStack(Material.LAPIS_LAZULI, 12);
            default:
                return getDropFromLocation(ores, ore);
        }
    }

    private ItemStack getDropFromLocation(List<Location> ores, Material ore) {
        ItemStack drop = new ItemStack(ore);
        for (ItemStack drops : ores.get(0).getBlock().getDrops()) {
            if (!drops.getType().isBlock()) drop = new CustomItem(drops, 2);
        }
        return drop;
    }
}
