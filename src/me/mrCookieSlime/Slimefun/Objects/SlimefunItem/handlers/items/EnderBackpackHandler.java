package me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.items;

import me.mrCookieSlime.CSCoreLibPlugin.events.ItemUseEvent;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.ItemInteractionHandler;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EnderBackpackHandler extends ItemInteractionHandler {
    @Override
    public boolean onRightClick(ItemUseEvent e, Player p, ItemStack item) {
        if (SlimefunManager.isItemSimiliar(item, SlimefunItems.ENDER_BACKPACK, true)) {
            e.setCancelled(true);
            p.openInventory(p.getEnderChest());
            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
            return true;
        }
        else return false;
    }
}
