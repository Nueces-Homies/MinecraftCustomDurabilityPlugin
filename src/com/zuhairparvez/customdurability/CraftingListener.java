package com.zuhairparvez.customdurability;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;


public class CraftingListener implements Listener {
    private CustomDurabilityManager durabilityManager;

    public CraftingListener(CustomDurabilityManager manager)
    {
        this.durabilityManager = manager;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent breakEvent)
    {
        Player player = breakEvent.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        this.durabilityManager.IncrementDamage(item);
    }

}
