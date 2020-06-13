package com.zuhairparvez.customdurability;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.HashSet;

public class CustomDurabilityManager {
    private final Plugin plugin;
    private final NamespacedKey customDurabilityCurrentKey;
    private final double multiplier;

    // Suppressing warning since these types may show up
    @SuppressWarnings("deprecation")
    HashSet<Material> toolMaterials = new HashSet<>(Arrays.asList(
            Material.WOODEN_PICKAXE, Material.LEGACY_WOOD_PICKAXE,
            Material.STONE_PICKAXE, Material.LEGACY_STONE_PICKAXE,
            Material.IRON_PICKAXE, Material.LEGACY_IRON_PICKAXE,
            Material.GOLDEN_PICKAXE, Material.LEGACY_GOLD_PICKAXE,
            Material.DIAMOND_PICKAXE, Material.LEGACY_DIAMOND_PICKAXE,

            Material.WOODEN_AXE, Material.LEGACY_WOOD_AXE,
            Material.STONE_AXE, Material.LEGACY_STONE_AXE,
            Material.IRON_AXE, Material.LEGACY_IRON_AXE,
            Material.GOLDEN_AXE, Material.LEGACY_GOLD_AXE,
            Material.DIAMOND_AXE, Material.LEGACY_DIAMOND_AXE,

            Material.WOODEN_HOE, Material.LEGACY_WOOD_HOE,
            Material.STONE_HOE, Material.LEGACY_STONE_HOE,
            Material.IRON_HOE, Material.LEGACY_IRON_HOE,
            Material.GOLDEN_HOE, Material.LEGACY_GOLD_HOE,
            Material.DIAMOND_HOE, Material.LEGACY_DIAMOND_HOE,

            Material.WOODEN_SHOVEL, Material.LEGACY_WOOD_SPADE,
            Material.STONE_SHOVEL, Material.LEGACY_STONE_SPADE,
            Material.IRON_SHOVEL, Material.LEGACY_IRON_SPADE,
            Material.GOLDEN_SHOVEL, Material.LEGACY_GOLD_SPADE,
            Material.DIAMOND_SHOVEL, Material.LEGACY_DIAMOND_SPADE
    ));

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

        if (toolMaterials.contains(tool.getType()))
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

        if (toolMaterials.contains(tool.getType()))
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
