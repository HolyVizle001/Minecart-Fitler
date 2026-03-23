package com.minecartfilter.commands;

import com.minecartfilter.MinecartFilterPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Collections;
import java.util.List;

public class MinecartFilterCommand implements CommandExecutor, TabCompleter {

    private final MinecartFilterPlugin plugin;

    public MinecartFilterCommand(MinecartFilterPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("minecartfilter.admin")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            plugin.reloadPluginConfig();
            sender.sendMessage(ChatColor.GREEN + "MinecartFilter config reloaded!");
            return true;
        }

        sender.sendMessage(ChatColor.GOLD + "=== MinecartFilter ===");
        sender.sendMessage(ChatColor.YELLOW + "Rail limit: " + ChatColor.WHITE + plugin.getPluginConfig2().getMaxPerRail());
        sender.sendMessage(ChatColor.YELLOW + "Chunk limit: " + ChatColor.WHITE + plugin.getPluginConfig2().getMaxPerChunk());
        sender.sendMessage(ChatColor.YELLOW + "Usage: " + ChatColor.WHITE + "/" + label + " reload");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Collections.singletonList("reload");
        }
        return Collections.emptyList();
    }
}