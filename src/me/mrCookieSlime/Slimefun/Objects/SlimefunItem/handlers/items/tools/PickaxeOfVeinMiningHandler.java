package me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.items.tools;

import me.mrCookieSlime.CSCoreLibPlugin.general.Block.Vein;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.BlockBreakHandler;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PickaxeOfVeinMiningHandler extends BlockBreakHandler {
    @Override
    public boolean onBlockBreak(BlockBreakEvent e, ItemStack item, int fortune, List<ItemStack> drops) {
        if (SlimefunManager.isItemSimiliar(e.getPlayer().getInventory().getItemInMainHand(), SlimefunItems.PICKAXE_OF_VEIN_MINING, true)) {
            if (e.getBlock().getType().toString().endsWith("_ORE")) {
                List<Location> blocks = new ArrayList<>();
                Vein.calculate(e.getBlock().getLocation(), e.getBlock().getLocation(), blocks, 16);
                for (Location block: blocks) {
                    Block b = block.getBlock();
                    b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, b.getType());
                    for (ItemStack drop: b.getDrops()) {
                        b.getWorld().dropItemNaturally(b.getLocation(), drop.getType().isBlock() ? drop: new CustomItem(drop, fortune));
                    }
                    b.setType(Material.AIR);
                }
            }
            return true;
        }
        else return false;
    }
}
