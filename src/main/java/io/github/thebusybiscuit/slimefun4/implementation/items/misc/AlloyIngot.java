package io.github.thebusybiscuit.slimefun4.implementation.items.misc;

import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.implementation.items.multiblocks.Smeltery;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

/**
 * An {@link AlloyIngot} is a blend of different metals and resources.
 * These ingots can be crafted using a {@link Smeltery}.
 * 
 * @author TheBusyBiscuit
 * 
 * @see Smeltery
 *
 */
public class AlloyIngot extends SlimefunItem {

    @ParametersAreNonnullByDefault
    public AlloyIngot(Category category, SlimefunItemStack item, ItemStack[] recipe) {
        super(category, item, RecipeType.SMELTERY, recipe);
    }

}
