package me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.items.weapons;

import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.BowShootHandler;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import me.mrCookieSlime.Slimefun.Variables;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class IcyBowHandler extends BowShootHandler {
    @Override
    public boolean onHit(EntityDamageByEntityEvent e, LivingEntity n) {
        if (SlimefunManager.isItemSimiliar(Variables.arrows.get(e.getDamager().getUniqueId()), SlimefunItems.ICY_BOW, true)) {
            n.getWorld().playEffect(n.getLocation(), Effect.STEP_SOUND, Material.ICE);
            n.getWorld().playEffect(n.getEyeLocation(), Effect.STEP_SOUND, Material.ICE);
            n.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 2, 10));
            n.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 2, -10));
            return true;
        }
        else return false;
    }
}
