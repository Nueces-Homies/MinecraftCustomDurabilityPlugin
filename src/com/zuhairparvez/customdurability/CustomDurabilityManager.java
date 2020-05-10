package com.zuhairparvez.customdurability;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class CustomDurabilityManager {
    private final Plugin plugin;
    private final NamespacedKey customDurabilityCurrentKey;
    private final double multiplier;

    public CustomDurabilityManager(Plugin plugin)
    {
        this.plugin = plugin;
        this.customDurabilityCurrentKey = new NamespacedKey(plugin, "custom-durability-current");
        this.multiplier = plugin.getConfig().getDouble("durability-multiplier");
    }

    public void IncrementDamage(ItemStack tool)
    {
        ItemMeta meta = tool.getItemMeta();

        var container = meta.getPersistentDataContainer();

        boolean hasDurability = tool.getItemMeta() instanceof Damageable;
        if (hasDurability)
        {
            int defaultMax = tool.getType().getMaxDurability();

            double maxDurability = this.multiplier * defaultMax;

            int currentDamage = container.getOrDefault(this.customDurabilityCurrentKey, PersistentDataType.INTEGER, 0);
            currentDamage++;

            double fraction = currentDamage / maxDurability;
            double projected = defaultMax * fraction;

            ((Damageable) meta).setDamage((int)projected);
            container.set(this.customDurabilityCurrentKey, PersistentDataType.INTEGER, currentDamage);
            tool.setItemMeta(meta);
        }
    }

    public class DamageStats
    {
        public int ActualDamage;
        public int ActualMax;

        public int ComputedDamage;
        public int ComputedMax;

        public DamageStats(int actualDamage, int actualMax, int computedDamage, int computedMax)
        {
            this.ActualDamage = actualDamage;
            this.ActualMax = actualMax;
            this.ComputedDamage = computedDamage;
            this.ComputedMax = computedMax;
        }
    }

    public DamageStats GetCurrentDamage(ItemStack tool) throws Exception {
        ItemMeta meta = tool.getItemMeta();

        var container = meta.getPersistentDataContainer();

        boolean hasDurability = tool.getItemMeta() instanceof Damageable;
        if (hasDurability)
        {
            int defaultMax = tool.getType().getMaxDurability();
            int actualDamage = ((Damageable) tool.getItemMeta()).getDamage();

            double maxDurability = this.multiplier * defaultMax;
            int currentDamage = container.getOrDefault(this.customDurabilityCurrentKey, PersistentDataType.INTEGER, 0);
            return new DamageStats(actualDamage, defaultMax, currentDamage, (int)maxDurability);
        }
        else
        {
            throw new Exception("Not a holding a tool with damage");
        }
    }
}
