package com.minecartfilter;

import com.minecartfilter.commands.MinecartFilterCommand;
import com.minecartfilter.listeners.MinecartPlaceListener;
import org.bukkit.plugin.java.JavaPlugin;

public class MinecartFilterPlugin extends JavaPlugin {

    private static MinecartFilterPlugin instance;
    private PluginConfig pluginConfig;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        pluginConfig = new PluginConfig(this);
        getServer().getPluginManager().registerEvents(new MinecartPlaceListener(this), this);
        getCommand("minecartfilter").setExecutor(new MinecartFilterCommand(this));
    }

    @Override
    public void onDisable() {
    }

    public static MinecartFilterPlugin getInstance() {
        return instance;
    }

    public PluginConfig getPluginConfig2() {
        return pluginConfig;
    }

    public void reloadPluginConfig() {
        reloadConfig();
        pluginConfig = new PluginConfig(this);
    }
}