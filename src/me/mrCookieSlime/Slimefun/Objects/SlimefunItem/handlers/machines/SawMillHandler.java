package me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.machines;

import me.mrCookieSlime.CSCoreLibPlugin.CSCoreLib;
import me.mrCookieSlime.CSCoreLibPlugin.compatibility.MaterialHelper;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.MultiBlock;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.MultiBlockInteractionHandler;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.Slimefun;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SawMillHandler extends MultiBlockInteractionHandler {
    @Override
    public String getMachineID() {
        return "SAW_MILL";
    }

    @Override
    public boolean onInteract(Player p, MultiBlock mb, Block b) {
        if (!mb.isMultiBlock(SlimefunItem.getByID(getMachineID()))) {
            return false;
        }
        if (CSCoreLib.getLib().getProtectionManager().canBuild(p.getUniqueId(), b.getRelative(BlockFace.UP), true)) {
            if (Slimefun.hasUnlocked(p, SlimefunItems.SAW_MILL, true)) {
                if (MaterialHelper.isLog(b.getRelative(BlockFace.UP).getType())) {
                    Block log = b.getRelative(BlockFace.UP);
                    if (!BlockStorage.hasBlockInfo(log)) {
                        ItemStack item = new CustomItem(MaterialHelper.getWoodFromLog(log.getType()), 0, 8);
                        log.getWorld().dropItemNaturally(log.getLocation(), item);
                        log.getWorld().playEffect(log.getLocation(), Effect.STEP_SOUND, log.getType());
                        log.setType(Material.AIR);
                    }
                }
            }
        }
        return true;
    }
}
