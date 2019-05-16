package me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.items;

import me.mrCookieSlime.CSCoreLibPlugin.events.ItemUseEvent;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.SkullItem;
import me.mrCookieSlime.CSCoreLibPlugin.general.Particles.FireworkShow;
import me.mrCookieSlime.CSCoreLibPlugin.general.Player.PlayerInventory;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.ItemInteractionHandler;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import me.mrCookieSlime.Slimefun.SlimefunStartup;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EasterEggHandler extends ItemInteractionHandler {

    @Override
    public boolean onRightClick(ItemUseEvent e, Player p, ItemStack item) {
        if (SlimefunManager.isItemSimiliar(item, SlimefunItems.EASTER_EGG, true)) {
            e.setCancelled(true);
            PlayerInventory.consumeItemInHand(e.getPlayer());
            FireworkShow.launchRandom(e.getPlayer(), 2);

            List<ItemStack> gifts = new ArrayList<ItemStack>();
            for (int i = 0; i < 2; i++) {
                gifts.add(new CustomItem(SlimefunItems.EASTER_CARROT_PIE, 4));
                gifts.add(new CustomItem(SlimefunItems.CHRISTMAS_APPLE_PIE, 4));
                gifts.add(new CustomItem(SlimefunItems.CARROT_JUICE, 1));
            }

            gifts.add(new SkullItem("mrCookieSlime"));
            gifts.add(new SkullItem("timtower"));
            gifts.add(new SkullItem("bwfcwalshy"));
            gifts.add(new SkullItem("jadedcat"));
            gifts.add(new SkullItem("ZeldoKavira"));
            gifts.add(new SkullItem("eyamaz"));
            gifts.add(new SkullItem("Kaelten"));
            gifts.add(new SkullItem("Myrathi"));

            p.getWorld().dropItemNaturally(p.getLocation(), gifts.get(SlimefunStartup.randomize(gifts.size())));
            return true;
        }
        else return false;
    }
}
