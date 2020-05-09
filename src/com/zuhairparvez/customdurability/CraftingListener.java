package com.zuhairparvez.customdurability;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;


public class CraftingListener implements Listener {

    private final NamespacedKey customDurabilityCurrentKey;
    private double multiplier = 1.0;


    public CraftingListener(JavaPlugin plugin)
    {
        this.customDurabilityCurrentKey = new NamespacedKey(plugin, "custom-durability-current");

        this.multiplier = plugin.getConfig().getDouble("durability-multiplier");
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent breakEvent)
    {
        Player player = breakEvent.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();

        var container = meta.getPersistentDataContainer();

        boolean hasDurability = item.getItemMeta() instanceof Damageable;
        if (hasDurability)
        {
            int defaultMax = item.getType().getMaxDurability();

            double maxDurability = this.multiplier * defaultMax;

            int currentDamage = container.getOrDefault(this.customDurabilityCurrentKey, PersistentDataType.INTEGER, 0);
            currentDamage++;

            double fraction = currentDamage / maxDurability;
            double projected = defaultMax * fraction;

            ((Damageable) meta).setDamage((int)projected);
            container.set(this.customDurabilityCurrentKey, PersistentDataType.INTEGER, currentDamage);
            item.setItemMeta(meta);
        }
    }

}
