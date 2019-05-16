package me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.items.tools;

import me.mrCookieSlime.CSCoreLibPlugin.CSCoreLib;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.CSCoreLibPlugin.general.String.StringUtils;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.HandledBlock;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.UnregisterReason;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.BlockBreakHandler;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import me.mrCookieSlime.Slimefun.SlimefunStartup;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ExplosivePickaxeHandler extends BlockBreakHandler {
    private String[] blacklist;

    public ExplosivePickaxeHandler(String[] blacklist) {
        this.blacklist = blacklist;
    }

    @Override
    public boolean onBlockBreak(BlockBreakEvent e, ItemStack item, int fortune, List<ItemStack> drops) {
        if (SlimefunManager.isItemSimiliar(item, SlimefunItems.EXPLOSIVE_PICKAXE, true)) {
            e.getBlock().getWorld().createExplosion(e.getBlock().getLocation(), 0.0F);
            e.getBlock().getWorld().playSound(e.getBlock().getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1F, 1F);
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        Block b = e.getBlock().getRelative(x, y, z);
                        if (b.getType() != Material.AIR && !StringUtils.equals(b.getType().toString(), blacklist)) {
                            if (CSCoreLib.getLib().getProtectionManager().canBuild(e.getPlayer().getUniqueId(), b)) {
                                if (SlimefunStartup.instance.isCoreProtectInstalled()) SlimefunStartup.instance.getCoreProtectAPI().logRemoval(e.getPlayer().getName(), b.getLocation(), b.getType(), b.getBlockData());
                                b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, b.getType());
                                SlimefunItem sfItem = BlockStorage.check(b);
                                boolean allow = true;
                                if (sfItem != null && !(sfItem instanceof HandledBlock)) {
                                    if (SlimefunItem.blockhandler.containsKey(sfItem.getID())) {
                                        allow = SlimefunItem.blockhandler.get(sfItem.getID()).onBreak(e.getPlayer(), e.getBlock(), sfItem, UnregisterReason.PLAYER_BREAK);
                                    }
                                    if (allow) {
                                        drops.add(BlockStorage.retrieve(e.getBlock()));
                                    }
                                }
                                else if (b.getType().equals(Material.PLAYER_HEAD)) {
                                    b.breakNaturally();
                                }
                                else if (b.getType().name().endsWith("_SHULKER_BOX")) {
                                    b.breakNaturally();
                                }
                                else {
                                    for (ItemStack drop: b.getDrops()) {
                                        b.getWorld().dropItemNaturally(b.getLocation(), (b.getType().toString().endsWith("_ORE") && !b.getType().equals(Material.IRON_ORE) && !b.getType().equals(Material.GOLD_ORE)) ? new CustomItem(drop, fortune): drop);
                                    }
                                    b.setType(Material.AIR);
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }
        else return false;
    }
}
