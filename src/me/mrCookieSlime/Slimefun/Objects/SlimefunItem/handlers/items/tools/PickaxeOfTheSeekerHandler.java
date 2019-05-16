package me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.items.tools;

import me.mrCookieSlime.CSCoreLibPlugin.events.ItemUseEvent;
import me.mrCookieSlime.CSCoreLibPlugin.general.Player.PlayerInventory;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.ItemInteractionHandler;
import me.mrCookieSlime.Slimefun.Setup.Messages;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import me.mrCookieSlime.Slimefun.SlimefunStartup;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PickaxeOfTheSeekerHandler extends ItemInteractionHandler {
    @Override
    public boolean onRightClick(ItemUseEvent e, Player p, ItemStack item) {
        if (SlimefunManager.isItemSimiliar(item, SlimefunItems.PICKAXE_OF_THE_SEEKER, true)) {
            Block closest = null;

            for (int x = -4; x <= 4; x++) {
                for (int y = -4; y <= 4; y++) {
                    for (int z = -4; z <= 4; z++) {
                        if (p.getLocation().getBlock().getRelative(x, y, z).getType().toString().endsWith("_ORE")) {
                            if (closest == null) closest = p.getLocation().getBlock().getRelative(x, y, z);
                            else if (p.getLocation().distance(closest.getLocation()) < p.getLocation().distance(p.getLocation().getBlock().getRelative(x, y, z).getLocation())) closest = p.getLocation().getBlock().getRelative(x, y, z);
                        }
                    }
                }
            }

            if (closest == null) Messages.local.sendTranslation(p, "miner.no-ores", true);
            else {
                double l = closest.getX() + 0.5 - p.getLocation().getX();
                double w = closest.getZ() + 0.5 - p.getLocation().getZ();
                float yaw, pitch;
                double c = Math.sqrt(l * l + w * w);
                double alpha1 = -Math.asin(l / c) / Math.PI * 180;
                double alpha2 =  Math.acos(w / c) / Math.PI * 180;
                if (alpha2 > 90) yaw = (float) (180 - alpha1);
                else yaw = (float) alpha1;
                pitch = (float) ((-Math.atan((closest.getY() - 0.5 - p.getLocation().getY()) / Math.sqrt(l * l + w * w))) * 180F / Math.PI);

                p.teleport(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), yaw, pitch));
            }

            if (e.getPlayer().getInventory().getItemInMainHand().getEnchantments().containsKey(Enchantment.DURABILITY)) {
                if (SlimefunStartup.randomize(100) <= (60 + 40 / (e.getPlayer().getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.DURABILITY) + 1))) PlayerInventory.damageItemInHand(e.getPlayer());
            }
            else PlayerInventory.damageItemInHand(e.getPlayer());

            PlayerInventory.update(e.getPlayer());
            return true;
        }
        else return false;
    }
}
