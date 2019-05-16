package me.mrCookieSlime.Slimefun.Objects.SlimefunItem.tickers;

import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.energy.EnergyTicker;
import org.bukkit.Location;

public class SolarGeneratorTicker extends EnergyTicker {
    @Override
    public double generateEnergy(Location l, SlimefunItem item, Config data) {
        if (!l.getWorld().isChunkLoaded(l.getBlockX() >> 4, l.getBlockZ() >> 4) || l.getBlock().getLightFromSky() != 15) return 0D;
        if (l.getWorld().getTime() < 12300 || l.getWorld().getTime() > 23850) return 2D;
        return 0D;
    }

    @Override
    public boolean explode(Location l) {
        return false;
    }
}
