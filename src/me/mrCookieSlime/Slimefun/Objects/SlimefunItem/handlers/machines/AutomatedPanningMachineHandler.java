package me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.machines;

import me.mrCookieSlime.CSCoreLibPlugin.CSCoreLib;
import me.mrCookieSlime.CSCoreLibPlugin.general.Player.PlayerInventory;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.MultiBlock;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.MultiBlockInteractionHandler;
import me.mrCookieSlime.Slimefun.Setup.Messages;
import me.mrCookieSlime.Slimefun.SlimefunStartup;
import me.mrCookieSlime.Slimefun.api.Slimefun;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AutomatedPanningMachineHandler extends MultiBlockInteractionHandler {
    @Override
    public String getMachineID() {
        return "AUTOMATED_PANNING_MACHINE";
    }

    @Override
    public boolean onInteract(final Player p, MultiBlock mb, final Block b) {
        if (!mb.isMultiBlock(SlimefunItem.getByID(getMachineID())))
            return false;

        if (CSCoreLib.getLib().getProtectionManager().canAccessChest(p.getUniqueId(), b, true)) {
            final ItemStack input = p.getInventory().getItemInMainHand();
            final ItemStack drop = getDrop();

            if (input.getType() == Material.GRAVEL) {
                PlayerInventory.consumeItemInHand(p);
                for (int i = 1; i < 7; i++) {
                    int j = i;
                    Bukkit.getScheduler().runTaskLater(SlimefunStartup.instance, () -> {
                        b.getWorld().playEffect(b.getRelative(BlockFace.DOWN).getLocation(), Effect.STEP_SOUND, Material.GRAVEL);
                        if (j == 6) {
                            if (drop != null) b.getWorld().dropItemNaturally(b.getLocation(), drop);
                            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1F, 1F);
                        }
                    }, i * 30L);
                }
                return true;
            }

            Messages.local.sendTranslation(p, "machines.wrong-item", true);
            return true;
        }
        return true;
    }

    private ItemStack getDrop() {
        if (SlimefunStartup.chance(100, (Integer) Slimefun.getItemValue("GOLD_PAN", "chance.SIFTED_ORE")))
            return SlimefunItems.SIFTED_ORE;
        if (SlimefunStartup.chance(100, (Integer) Slimefun.getItemValue("GOLD_PAN", "chance.CLAY")))
            return new ItemStack(Material.CLAY_BALL);
        if (SlimefunStartup.chance(100, (Integer) Slimefun.getItemValue("GOLD_PAN", "chance.FLINT")))
            return new ItemStack(Material.FLINT);
        return null;
    }


}
