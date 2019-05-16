package me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.items;

import me.mrCookieSlime.CSCoreLibPlugin.events.ItemUseEvent;
import me.mrCookieSlime.CSCoreLibPlugin.general.Player.PlayerInventory;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.Research;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.ItemInteractionHandler;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class TomeOfKnowledgeSharingHandler extends ItemInteractionHandler {
    @Override
    public boolean onRightClick(ItemUseEvent e, Player p, ItemStack item) {
        if (SlimefunManager.isItemSimiliar(item, SlimefunItems.TOME_OF_KNOWLEDGE_SHARING, true)) {
            List<String> lore = item.getItemMeta().getLore();
            lore.set(0, ChatColor.translateAlternateColorCodes('&', "&7Owner: &b" + p.getName()));
            lore.set(1, ChatColor.BLACK + "" + p.getUniqueId());
            ItemMeta im = item.getItemMeta();
            im.setLore(lore);
            item.setItemMeta(im);
            p.getEquipment().setItemInMainHand(item);
            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 1F);
            return true;
        }
        else if (SlimefunManager.isItemSimiliar(item, SlimefunItems.TOME_OF_KNOWLEDGE_SHARING, false)) {
            List<Research> researches = Research.getResearches(ChatColor.stripColor(item.getItemMeta().getLore().get(1)));
            for (Research research: researches) {
                research.unlock(p, true);
            }
            PlayerInventory.consumeItemInHand(p);
            return true;
        }
        else return false;
    }
}
