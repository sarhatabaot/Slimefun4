package me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.items;

import me.mrCookieSlime.CSCoreLibPlugin.events.ItemUseEvent;
import me.mrCookieSlime.CSCoreLibPlugin.general.Player.PlayerInventory;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.ItemInteractionHandler;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import org.bukkit.Sound;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MagicEyeOfEnderHandler extends ItemInteractionHandler {
    @Override
    public boolean onRightClick(ItemUseEvent e, Player p, ItemStack item) {
        if (!SlimefunManager.isItemSimiliar(item, SlimefunItems.MAGIC_EYE_OF_ENDER, true)) {
            return false;
        }

        e.getParentEvent().setCancelled(true);
        PlayerInventory.update(p);
        if (p.getInventory().getHelmet() != null && p.getInventory().getChestplate() != null && p.getInventory().getLeggings() != null && p.getInventory().getBoots() != null) {
            if (SlimefunManager.isItemSimiliar(p.getInventory().getHelmet(), SlimefunItems.ENDER_HELMET, true) && SlimefunManager.isItemSimiliar(p.getInventory().getChestplate(), SlimefunItems.ENDER_CHESTPLATE, true) && SlimefunManager.isItemSimiliar(p.getInventory().getLeggings(), SlimefunItems.ENDER_LEGGINGS, true) && SlimefunManager.isItemSimiliar(p.getInventory().getBoots(), SlimefunItems.ENDER_BOOTS, true)) {
                p.launchProjectile(EnderPearl.class);
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
            }
        }

        return true;

    }
}
