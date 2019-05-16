package me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.items;

import me.mrCookieSlime.CSCoreLibPlugin.events.ItemUseEvent;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.ItemInteractionHandler;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PortableDustbinHandler extends ItemInteractionHandler {

    @Override
    public boolean onRightClick(ItemUseEvent e, Player p, ItemStack item) {
        if (SlimefunManager.isItemSimiliar(item, SlimefunItems.PORTABLE_DUSTBIN, true)) {
            e.setCancelled(true);
            p.openInventory(Bukkit.createInventory(null, 9 * 3, ChatColor.DARK_RED + "Delete Items"));
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 1);
            return true;
        }
        else return false;
    }
}
