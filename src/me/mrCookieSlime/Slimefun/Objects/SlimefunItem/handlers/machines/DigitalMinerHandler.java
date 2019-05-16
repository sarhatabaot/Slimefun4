package me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.machines;

import org.bukkit.*;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class DigitalMinerHandler extends ADigitalMinerHandler {
    @Override
    public String getMachineID() {
        return "DIGITAL_MINER";
    }

    @Override
    public ItemStack getDrop(List<Location> ores, Material ore) {
        return new ItemStack(ore);
    }
}
