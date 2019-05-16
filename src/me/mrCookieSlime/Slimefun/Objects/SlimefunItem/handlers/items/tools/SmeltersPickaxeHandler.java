package me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.items.tools;

import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.CSCoreLibPlugin.general.Recipe.RecipeCalculator;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.BlockBreakHandler;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class SmeltersPickaxeHandler extends BlockBreakHandler {

    @Override
    public boolean onBlockBreak(BlockBreakEvent e, ItemStack item, int fortune, List<ItemStack> drops) {
        if (SlimefunManager.isItemSimiliar(item, SlimefunItems.AUTO_SMELT_PICKAXE, true)) {
            if (e.getBlock().getType().equals(Material.PLAYER_HEAD)) return true;

            int j = -1;
            List<ItemStack> dropsList = (List<ItemStack>) e.getBlock().getDrops();
            for (int i = 0; i < dropsList.size(); i++) {
                if (dropsList.get(i) != null) {
                    j++;
                    drops.add(e.getBlock().getType().toString().endsWith("_ORE") ? new CustomItem(dropsList.get(i), fortune): dropsList.get(i));
                    if (RecipeCalculator.getSmeltedOutput(drops.get(i).getType()) != null) {
                        e.getBlock().getWorld().playEffect(e.getBlock().getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
                        drops.set(j, new CustomItem(RecipeCalculator.getSmeltedOutput(drops.get(i).getType()), drops.get(i).getAmount()));
                    }
                }
            }

            return true;
        }
        else return false;
    }
}
