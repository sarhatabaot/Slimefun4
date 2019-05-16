package me.mrCookieSlime.Slimefun.AncientAltar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import me.mrCookieSlime.Slimefun.api.BlockStorage;

public class Pedestals {

	public static List<AltarRecipe> recipes = new ArrayList<>();

	public static List<Block> getPedestals(Block altar) {
		List<Block> list = new ArrayList<>();
		addRelativeBlock(list,altar,3,0,0);
		addRelativeBlock(list,altar,-3,0,0);
		addRelativeBlock(list,altar,0,0,3);
		addRelativeBlock(list,altar,0,0,-3);
		addRelativeBlock(list,altar,2,0,2);
		addRelativeBlock(list,altar,2,0,-2);
		addRelativeBlock(list,altar,-2,0,2);
		addRelativeBlock(list,altar,-2,0,-2);
		return list;
	}

	private static void addRelativeBlock(List<Block> list, Block altar, int i, int i1, int i2){
		if(BlockStorage.check(altar.getRelative(i,i1,i2),"ANCIENT_PEDESTAL")){
			list.add(altar.getRelative(i,i1,i2));
		}
	}

	public static ItemStack getRecipeOutput(ItemStack catalyst, List<ItemStack> input) {
		if (input.size() != 8) return null;
		if (SlimefunManager.isItemSimiliar(catalyst, SlimefunItems.BROKEN_SPAWNER, false)) {
            if (checkRecipe(SlimefunItems.BROKEN_SPAWNER, input) == null) return null;
			final ItemStack spawner = SlimefunItems.REPAIRED_SPAWNER.clone();
			ItemMeta im = spawner.getItemMeta();
			im.setLore(Arrays.asList(catalyst.getItemMeta().getLore().get(0)));
			spawner.setItemMeta(im);
			return spawner;
		}

		return checkRecipe(catalyst, input);
	}

	private static ItemStack checkRecipe(ItemStack catalyst, List<ItemStack> input) {
        AltarRecipe r;
		for (AltarRecipe recipe : recipes) {
			if (SlimefunManager.isItemSimiliar(catalyst, recipe.getCatalyst(), true)) {
				List<ItemStack> copy = new ArrayList<>(input);
				r = getRecipe(copy,recipe);
				if (r != null) return r.getOutput();
			}
		}
        return null;
	}

	private static boolean isMatch(Iterator<ItemStack> iterator,ItemStack item){
		while (iterator.hasNext()) {
			ItemStack altar_item = iterator.next();
			if (SlimefunManager.isItemSimiliar(altar_item, item, true)) {
				iterator.remove();
				return true;
			}
		}
		return false;
	}

	private static AltarRecipe getRecipe(List<ItemStack> copy,AltarRecipe recipe){
		AltarRecipe r  = recipe;
		for (ItemStack item : recipe.getInput()) {
			Iterator<ItemStack> iterator = copy.iterator();

			if (!isMatch(iterator,item)) {
				r = null;
			}
		}
		return r;
	}
}
