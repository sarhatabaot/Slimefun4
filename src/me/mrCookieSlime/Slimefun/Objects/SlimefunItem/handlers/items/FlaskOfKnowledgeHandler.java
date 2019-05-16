package me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.items;

import me.mrCookieSlime.CSCoreLibPlugin.events.ItemUseEvent;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.CSCoreLibPlugin.general.Player.PlayerInventory;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.ItemInteractionHandler;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FlaskOfKnowledgeHandler extends ItemInteractionHandler {
    @Override
    public boolean onRightClick(ItemUseEvent e, Player p, ItemStack item) {
        if (SlimefunManager.isItemSimiliar(item, SlimefunItems.FLASK_OF_KNOWLEDGE, true) && p.getLevel() >= 1) {
            p.setLevel(p.getLevel() - 1);
            p.getInventory().addItem(new CustomItem(Material.EXPERIENCE_BOTTLE, "&aFlask of Knowledge", 0));
            PlayerInventory.consumeItemInHand(p);
            PlayerInventory.update(p);
            return true;
        }
        else return false;
    }
}
