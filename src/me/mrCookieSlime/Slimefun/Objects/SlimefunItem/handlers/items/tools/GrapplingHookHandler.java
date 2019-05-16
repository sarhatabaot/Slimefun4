package me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.items.tools;

import me.mrCookieSlime.CSCoreLibPlugin.events.ItemUseEvent;
import me.mrCookieSlime.CSCoreLibPlugin.general.Player.PlayerInventory;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.ItemInteractionHandler;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import me.mrCookieSlime.Slimefun.Variables;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class GrapplingHookHandler extends ItemInteractionHandler {
    @Override
    public boolean onRightClick(ItemUseEvent e, Player p, ItemStack item) {
        if (SlimefunManager.isItemSimiliar(item, SlimefunItems.GRAPPLING_HOOK, true)) {
            if (e.getClickedBlock() == null && !Variables.jump.containsKey(p.getUniqueId())) {
                e.setCancelled(true);
                if (p.getInventory().getItemInOffHand().getType().equals(Material.BOW)) {
                    // Cancel, to fix dupe #740
                    return false;
                }
                Variables.jump.put(p.getUniqueId(), p.getInventory().getItemInMainHand().getType() != Material.SHEARS);
                if (p.getInventory().getItemInMainHand().getType() == Material.LEAD) PlayerInventory.consumeItemInHand(p);

                Vector direction = p.getEyeLocation().getDirection().multiply(2.0);
                Projectile projectile = p.getWorld().spawn(p.getEyeLocation().add(direction.getX(), direction.getY(), direction.getZ()), Arrow.class);
                projectile.setShooter(p);
                projectile.setVelocity(direction);
                Arrow arrow = (Arrow) projectile;
                Bat b = (Bat) p.getWorld().spawnEntity(p.getLocation(), EntityType.BAT);
                b.setCanPickupItems(false);
                b.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 100000));
                b.setLeashHolder(arrow);

                Variables.damage.put(p.getUniqueId(), true);
                Variables.remove.put(p.getUniqueId(), new Entity[] {b, arrow});
            }
            return true;
        }
        else return false;
    }
}
