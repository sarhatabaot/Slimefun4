package me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.items;

import me.mrCookieSlime.CSCoreLibPlugin.CSCoreLib;
import me.mrCookieSlime.CSCoreLibPlugin.events.ItemUseEvent;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.ItemInteractionHandler;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import me.mrCookieSlime.Slimefun.SlimefunStartup;
import me.mrCookieSlime.Slimefun.api.Slimefun;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GoldPanHandler extends ItemInteractionHandler {
    @Override
    public boolean onRightClick(ItemUseEvent e, Player p, ItemStack item) {
        if (SlimefunManager.isItemSimiliar(item, SlimefunItems.GOLD_PAN, true)) {
            if (e.getClickedBlock() != null && e.getClickedBlock().getType() == Material.GRAVEL) {
                if (CSCoreLib.getLib().getProtectionManager().canBuild(p.getUniqueId(), e.getClickedBlock(), true)) {
                    List<ItemStack> drops = new ArrayList<>();
                    if (SlimefunStartup.chance(100, (Integer) Slimefun.getItemValue("GOLD_PAN", "chance.SIFTED_ORE"))) drops.add(SlimefunItems.SIFTED_ORE);
                    else if (SlimefunStartup.chance(100, (Integer) Slimefun.getItemValue("GOLD_PAN", "chance.CLAY"))) drops.add(new ItemStack(Material.CLAY_BALL));
                    else if (SlimefunStartup.chance(100, (Integer) Slimefun.getItemValue("GOLD_PAN", "chance.FLINT"))) drops.add(new ItemStack(Material.FLINT));



                    e.getClickedBlock().getWorld().playEffect(e.getClickedBlock().getLocation(), Effect.STEP_SOUND, e.getClickedBlock().getType());
                    e.getClickedBlock().setType(Material.AIR);
                    for (ItemStack drop: drops) {
                        e.getClickedBlock().getWorld().dropItemNaturally(e.getClickedBlock().getLocation(), drop);
                    }
                }
            }
            e.setCancelled(true);
            return true;
        }
        else return false;
    }
}
