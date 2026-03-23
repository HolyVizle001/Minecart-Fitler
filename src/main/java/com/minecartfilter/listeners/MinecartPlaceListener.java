package com.minecartfilter.listeners;

import com.minecartfilter.MinecartFilterPlugin;
import com.minecartfilter.PluginConfig;
import com.minecartfilter.util.RailScanner;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.entity.Minecart;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Set;

public class MinecartPlaceListener implements Listener {

    private final MinecartFilterPlugin plugin;

    // All minecart entity types
    private static final Set<EntityType> MINECART_TYPES = Set.of(
            EntityType.MINECART,
            EntityType.MINECART_CHEST,
            EntityType.MINECART_FURNACE,
            EntityType.MINECART_TNT,
            EntityType.MINECART_HOPPER,
            EntityType.MINECART_MOB_SPAWNER,
            EntityType.MINECART_COMMAND
    );

    public MinecartPlaceListener(MinecartFilterPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * VehicleCreateEvent fires when a minecart is placed by a player or otherwise spawned.
     * We cancel it if limits are exceeded.
     */
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onVehicleCreate(VehicleCreateEvent event) {
        if (!(event.getVehicle() instanceof Minecart minecart)) {
            return;
        }

        Location loc = minecart.getLocation();
        Block railBlock = loc.getBlock();

        
        if (!RailScanner.isRail(railBlock)) {
            railBlock = loc.clone().subtract(0, 1, 0).getBlock();
        }

        if (!RailScanner.isRail(railBlock)) {
            return; 
        }

        PluginConfig cfg = plugin.getPluginConfig2();

        
        int chunkCount = RailScanner.countMinecartsInChunk(railBlock);
        if (chunkCount >= cfg.getMaxPerChunk()) {
            event.setCancelled(true);
            if (cfg.isLogViolations()) {
                plugin.getLogger().info("Blocked minecart spawn at " + loc
                        + " — chunk limit reached (" + chunkCount + "/" + cfg.getMaxPerChunk() + ")");
            }
            
            loc.getWorld().getNearbyEntities(loc, 3, 3, 3).stream()
                    .filter(e -> e instanceof Player)
                    .map(e -> (Player) e)
                    .forEach(p -> {
                        if (!p.hasPermission("minecartfilter.bypass")) {
                            p.sendMessage(cfg.getChunkLimitMessage());
                        }
                    });
            return;
        }

        
        int railCount = RailScanner.countMinecartsonConnectedRail(railBlock);
        if (railCount >= cfg.getMaxPerRail()) {
            event.setCancelled(true);
            if (cfg.isLogViolations()) {
                plugin.getLogger().info("Blocked minecart spawn at " + loc
                        + " — rail limit reached (" + railCount + "/" + cfg.getMaxPerRail() + ")");
            }
            loc.getWorld().getNearbyEntities(loc, 3, 3, 3).stream()
                    .filter(e -> e instanceof Player)
                    .map(e -> (Player) e)
                    .forEach(p -> {
                        if (!p.hasPermission("minecartfilter.bypass")) {
                            p.sendMessage(cfg.getRailLimitMessage());
                        }
                    });
        }
    }
}
