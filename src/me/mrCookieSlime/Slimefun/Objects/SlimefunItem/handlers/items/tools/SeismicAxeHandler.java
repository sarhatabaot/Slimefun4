package me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.items.tools;

import me.mrCookieSlime.CSCoreLibPlugin.events.ItemUseEvent;
import me.mrCookieSlime.CSCoreLibPlugin.general.Player.PlayerInventory;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.ItemInteractionHandler;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import me.mrCookieSlime.Slimefun.SlimefunStartup;
import me.mrCookieSlime.Slimefun.Variables;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.List;

public class SeismicAxeHandler extends ItemInteractionHandler {
    @Override
    public boolean onRightClick(ItemUseEvent e, Player p, ItemStack item) {
        if (SlimefunManager.isItemSimiliar(item, SlimefunItems.SEISMIC_AXE, true)) {
            List<Block> blocks = p.getLineOfSight((HashSet<Material>) null, 10);
            for (int i = 0; i < blocks.size(); i++) {
                Block b = blocks.get(i);
                Location ground = b.getLocation();
                if (b.getType() == null || b.getType() == Material.AIR) {
                    for (int y = ground.getBlockY(); y > 0; y--) {
                        if (b.getWorld().getBlockAt(b.getX(), y, b.getZ()) != null) {
                            if (b.getWorld().getBlockAt(b.getX(), y, b.getZ()).getType() != null) {
                                if (b.getWorld().getBlockAt(b.getX(), y, b.getZ()).getType() != Material.AIR) {
                                    ground = new Location(b.getWorld(), b.getX(), y, b.getZ());
                                    break;
                                }
                            }
                        }
                    }
                }
                b.getWorld().playEffect(ground, Effect.STEP_SOUND, ground.getBlock().getType());
                if (ground.getBlock().getRelative(BlockFace.UP).getType() == null || ground.getBlock().getRelative(BlockFace.UP).getType() == Material.AIR) {
                    FallingBlock block = ground.getWorld().spawnFallingBlock(ground.getBlock().getRelative(BlockFace.UP).getLocation(), ground.getBlock().getBlockData());
                    block.setDropItem(false);
                    block.setVelocity(new Vector(0, 0.4 + i * 0.01, 0));
                    Variables.blocks.add(block.getUniqueId());
                }
                for (Entity n: ground.getChunk().getEntities()) {
                    if (n instanceof LivingEntity) {
                        if (n.getLocation().distance(ground) <= 2.0D && n.getUniqueId() != p.getUniqueId()) {
                            Vector vector = n.getLocation().toVector().subtract(p.getLocation().toVector()).normalize().multiply(1.4);
                            vector.setY(0.9);
                            n.setVelocity(vector);
                            if (p.getWorld().getPVP()) {
                                EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(p, n, EntityDamageEvent.DamageCause.ENTITY_ATTACK, 6D);
                                Bukkit.getPluginManager().callEvent(event);
                                if (!event.isCancelled()) ((LivingEntity) n).damage(6D);
                            }
                        }
                    }
                }
            }

            for (int i = 0; i < 4; i++) {
                if (e.getPlayer().getInventory().getItemInMainHand() != null) {
                    if (e.getPlayer().getInventory().getItemInMainHand().getEnchantments().containsKey(Enchantment.DURABILITY)) {
                        if (SlimefunStartup.randomize(100) <= (60 + 40 / (e.getPlayer().getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.DURABILITY) + 1))) PlayerInventory.damageItemInHand(e.getPlayer());
                    }
                    else PlayerInventory.damageItemInHand(e.getPlayer());
                }
            }
            return true;
        }
        else return false;
    }
}
