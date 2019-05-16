package me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.items;

import me.mrCookieSlime.CSCoreLibPlugin.events.ItemUseEvent;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.ItemInteractionHandler;
import me.mrCookieSlime.Slimefun.Setup.Messages;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.inventory.ItemStack;

public class StaffElementalWindHandler extends ItemInteractionHandler {
    //TODO: abstract handler for staff
    @Override
    public boolean onRightClick(ItemUseEvent e, Player p, ItemStack item) {
        if (SlimefunManager.isItemSimiliar(item, SlimefunItems.STAFF_WIND, true)) {
            if (p.getFoodLevel() >= 2) {
                if (p.getInventory().getItemInMainHand().getType() != Material.SHEARS && p.getGameMode() != GameMode.CREATIVE) {
                    FoodLevelChangeEvent event = new FoodLevelChangeEvent(p, p.getFoodLevel() - 2);
                    Bukkit.getPluginManager().callEvent(event);
                    p.setFoodLevel(event.getFoodLevel());
                }
                p.setVelocity(p.getEyeLocation().getDirection().multiply(4));
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_TNT_PRIMED, 1, 1);
                p.getWorld().playEffect(p.getLocation(), Effect.SMOKE, 1);
                p.setFallDistance(0.0f);
            }
            else {
                Messages.local.sendTranslation(p, "messages.hungry", true);
            }
            return true;
        }
        else return false;
    }
}
