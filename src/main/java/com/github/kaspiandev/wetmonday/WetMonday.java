package com.github.kaspiandev.wetmonday;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.PufferFish;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class WetMonday extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onBucketUse(PlayerBucketEmptyEvent event) {
        Material bucket = event.getBucket();
        if (bucket == Material.WATER_BUCKET) {
            resetBucket(event, Material.LAVA);
        } else if (bucket == Material.LAVA_BUCKET) {
            resetBucket(event, Material.WATER);
        } else if (bucket == Material.PUFFERFISH_BUCKET) {
            resetBucket(event, Material.WATER);
            Block block = event.getBlock();
            block.getWorld().spawn(block.getLocation(), PufferFish.class, (pufferFish) -> {
                pufferFish.setGravity(false);
                pufferFish.setCustomName("Hack3d By GH05T");
                pufferFish.setVisualFire(true);
                pufferFish.setPuffState(25);
                pufferFish.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, -1, 0, false, false, false));
            });
        }
    }

    private void resetBucket(PlayerBucketEmptyEvent event, Material replacement) {
        ItemStack handItem = event.getPlayer().getInventory().getItem(event.getHand());
        if (handItem != null) {
            event.setCancelled(true);
            handItem.setType(Material.BUCKET);
            event.getBlock().setType(replacement);
        }
    }

}
