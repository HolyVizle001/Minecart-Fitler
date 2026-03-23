package com.minecartfilter;

import org.bukkit.ChatColor;

public class PluginConfig {

    private final int maxPerRail;
    private final int maxPerChunk;
    private final String messageRailLimit;
    private final String messageChunkLimit;
    private final boolean logViolations;

    public PluginConfig(MinecartFilterPlugin plugin) {
        this.maxPerRail = plugin.getConfig().getInt("max-minecarts-per-rail", 10);
        this.maxPerChunk = plugin.getConfig().getInt("max-minecarts-per-chunk", 20);
        this.messageRailLimit = ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("message-rail-limit",
                        "&cToo many minecarts on this rail! Maximum is {limit}."));
        this.messageChunkLimit = ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("message-chunk-limit",
                        "&cToo many minecarts in this chunk! Maximum is {limit}."));
        this.logViolations = plugin.getConfig().getBoolean("log-violations", false);
    }

    public int getMaxPerRail() {
        return maxPerRail;
    }

    public int getMaxPerChunk() {
        return maxPerChunk;
    }

    public String getRailLimitMessage() {
        return messageRailLimit.replace("{limit}", String.valueOf(maxPerRail));
    }

    public String getChunkLimitMessage() {
        return messageChunkLimit.replace("{limit}", String.valueOf(maxPerChunk));
    }

    public boolean isLogViolations() {
        return logViolations;
    }
}