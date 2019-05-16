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
    public ItemStack getDrop(List<Location> ores, Material ore){
        if (ore == Material.COAL_ORE)  return new ItemStack(Material.COAL, 4);
        else if (ore == Material.IRON_ORE) return new CustomItem(SlimefunItems.IRON_DUST, 2);
        else if (ore == Material.GOLD_ORE)  return new CustomItem(SlimefunItems.GOLD_DUST, 2);
        else if (ore == Material.REDSTONE_ORE)  return new ItemStack(Material.REDSTONE, 8);
        else if (ore == Material.NETHER_QUARTZ_ORE)  return new ItemStack(Material.QUARTZ, 4);
        else if (ore == Material.LAPIS_ORE)  return new ItemStack(Material.LAPIS_LAZULI, 12);
        else {
            return getDropFromLocation(ores, ore);
        }
    }

    private ItemStack getDropFromLocation(List<Location> ores, Material ore){
        ItemStack drop = new ItemStack(ore);
        for (ItemStack drops: ores.get(0).getBlock().getDrops()) {
            if (!drops.getType().isBlock()) drop = new CustomItem(drops, 2);
        }
        return drop;
    }
}
