package me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.items.tools;

import me.mrCookieSlime.CSCoreLibPlugin.general.String.StringUtils;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.BlockBreakHandler;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PickaxeOfContainmentHandler extends BlockBreakHandler {

    @Override
    public boolean onBlockBreak(BlockBreakEvent e, ItemStack item, int fortune, List<ItemStack> drops) {
        if (SlimefunManager.isItemSimiliar(item, SlimefunItems.PICKAXE_OF_CONTAINMENT, true)) {
            if (e.getBlock().getType() != Material.SPAWNER) return true;
            BlockStorage.clearBlockInfo(e.getBlock());
            ItemStack spawner = SlimefunItems.BROKEN_SPAWNER.clone();
            ItemMeta im = spawner.getItemMeta();
            List<String> lore = im.getLore();
            for (int i = 0; i < lore.size(); i++) {
                if (lore.get(i).contains("<Type>")) lore.set(i, lore.get(i).replace("<Type>", StringUtils.format(((CreatureSpawner) e.getBlock().getState()).getSpawnedType().toString())));
            }
            im.setLore(lore);
            spawner.setItemMeta(im);
            e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(), spawner);
            e.setExpToDrop(0);
            return true;
        }
        else return false;
    }
}
