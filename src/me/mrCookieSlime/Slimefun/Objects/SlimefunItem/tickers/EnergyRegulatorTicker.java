package me.mrCookieSlime.Slimefun.Objects.SlimefunItem.tickers;

import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.energy.EnergyNet;
import org.bukkit.block.Block;

public class EnergyRegulatorTicker extends BlockTicker {
    @Override
    public boolean isSynchronized() {
        return false;
    }

    @Override
    public void uniqueTick() {
    }

    @Override
    public void tick(Block b, SlimefunItem item, Config data) {
        EnergyNet.getNetworkFromLocationOrCreate(b.getLocation()).tick(b);
    }
}
