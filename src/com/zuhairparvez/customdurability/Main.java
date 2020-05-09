package com.zuhairparvez.customdurability;

import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new CraftingListener(this), this);
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
    }
}
