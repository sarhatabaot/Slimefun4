package me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.items;

import me.mrCookieSlime.CSCoreLibPlugin.events.ItemUseEvent;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.ItemInteractionHandler;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PortableCrafterHandler extends ItemInteractionHandler {
    @Override
    public boolean onRightClick(ItemUseEvent e, Player p, ItemStack item) {
        if (SlimefunManager.isItemSimiliar(item, SlimefunItems.PORTABLE_CRAFTER, true)) {
            p.openWorkbench(p.getLocation(), true);
            p.getWorld().playSound(p.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1, 1);
            return true;
        }
        else return false;
    }
}
