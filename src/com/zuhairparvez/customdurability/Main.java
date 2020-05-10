package com.zuhairparvez.customdurability;

import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        CustomDurabilityManager durabilityManager = new CustomDurabilityManager(this);

        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new CraftingListener(durabilityManager), this);

        this.getServer().getPluginManager().registerEvents(this, this);

        this.getCommand("durability").setExecutor(new DurabilityCommand(durabilityManager));
    }

    @Override
    public void onDisable() {
    }
}
