package me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.items.weapons;

import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.BowShootHandler;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import me.mrCookieSlime.Slimefun.Variables;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

public class ExplosiveBowHandler extends BowShootHandler {
    @Override
    public boolean onHit(EntityDamageByEntityEvent e, LivingEntity n) {
        if (SlimefunManager.isItemSimiliar(Variables.arrows.get(e.getDamager().getUniqueId()), SlimefunItems.EXPLOSIVE_BOW, true)) {
            Vector vector = n.getVelocity();
            vector.setY(0.6);
            n.setVelocity(vector);
            n.getWorld().createExplosion(n.getLocation(), 0F);
            n.getWorld().playSound(n.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1F, 1F);
            return true;
        }
        else return false;
    }
}
