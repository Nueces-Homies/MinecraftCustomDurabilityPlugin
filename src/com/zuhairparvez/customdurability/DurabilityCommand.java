package com.zuhairparvez.customdurability;

import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class DurabilityCommand implements CommandExecutor {
    CustomDurabilityManager durabilityManager;
    public DurabilityCommand(CustomDurabilityManager manager)
    {
        this.durabilityManager = manager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player)
        {
            Player player = (Player) commandSender;
            var tool = player.getInventory().getItemInMainHand();

            try {
                CustomDurabilityManager.DamageStats stats = this.durabilityManager.GetCurrentDamage(tool);
                var message = String.format(
                        "Basis: %d/%d, Projected: %d/%d",
                        stats.ComputedDamage, stats.ComputedMax,
                        stats.ActualDamage, stats.ActualMax);

                player.sendMessage(message);
            } catch (Exception e)
            {
                player.sendMessage("Invalid command for tool '"+tool.getI18NDisplayName()+"'");
            }
        }
        else {
            commandSender.sendMessage("Not supported from console");
        }

        return true;
    }
}
