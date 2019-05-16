package me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.items.magic;

import me.mrCookieSlime.CSCoreLibPlugin.events.ItemUseEvent;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.ItemInteractionHandler;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ScrollOfDimensionalTelepositionHandler extends ItemInteractionHandler {
    @Override
    public boolean onRightClick(ItemUseEvent e, Player p, ItemStack item) {
        if (SlimefunManager.isItemSimiliar(item, SlimefunItems.SCROLL_OF_DIMENSIONAL_TELEPOSITION, true)) {
            for (Entity n: p.getNearbyEntities(10.0, 10.0, 10.0)) {
                if (n instanceof LivingEntity && !(n instanceof ArmorStand) &&n.getUniqueId() != p.getUniqueId()) {
                    float yaw = n.getLocation().getYaw() + 180.0F;
                    if (yaw > 360.0F) yaw = yaw - 360.0F;
                    n.teleport(new Location(n.getWorld(), n.getLocation().getX(), n.getLocation().getY(), n.getLocation().getZ(), yaw, n.getLocation().getPitch()));
                }
            }
            return true;
        }
        else return false;
    }
}
