package com.minecartfilter.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class RailScanner {

    private static final Set<Material> RAIL_TYPES = new HashSet<>();

    static {
        RAIL_TYPES.add(Material.RAIL);
        RAIL_TYPES.add(Material.POWERED_RAIL);
        RAIL_TYPES.add(Material.DETECTOR_RAIL);
        RAIL_TYPES.add(Material.ACTIVATOR_RAIL);
    }

    public static int countMinecartsonConnectedRail(Block startBlock) {
        if (!isRail(startBlock)) {
            return 0;
        }

        World world = startBlock.getWorld();
        Set<Location> visitedRails = new HashSet<>();
        Queue<Block> queue = new ArrayDeque<>();

        queue.add(startBlock);
        visitedRails.add(startBlock.getLocation());

        int minecartCount = 0;
        int maxRailsToScan = 200;

        while (!queue.isEmpty() && visitedRails.size() <= maxRailsToScan) {
            Block current = queue.poll();

            for (Entity entity : world.getNearbyEntities(
                    current.getLocation().add(0.5, 0.5, 0.5), 0.6, 0.6, 0.6)) {
                if (entity instanceof Minecart) {
                    minecartCount++;
                }
            }

            for (Block neighbor : getAdjacentRailBlocks(current)) {
                if (isRail(neighbor) && !visitedRails.contains(neighbor.getLocation())) {
                    visitedRails.add(neighbor.getLocation());
                    queue.add(neighbor);
                }
            }
        }

        return minecartCount;
    }

    public static int countMinecartsInChunk(Block block) {
        int count = 0;
        for (Entity entity : block.getChunk().getEntities()) {
            if (entity instanceof Minecart) {
                count++;
            }
        }
        return count;
    }

    private static Block[] getAdjacentRailBlocks(Block block) {
        return new Block[]{
                block.getRelative(1, 0, 0),
                block.getRelative(-1, 0, 0),
                block.getRelative(0, 0, 1),
                block.getRelative(0, 0, -1),
                block.getRelative(1, 1, 0),
                block.getRelative(-1, 1, 0),
                block.getRelative(0, 1, 1),
                block.getRelative(0, 1, -1),
                block.getRelative(1, -1, 0),
                block.getRelative(-1, -1, 0),
                block.getRelative(0, -1, 1),
                block.getRelative(0, -1, -1),
        };
    }

    public static boolean isRail(Block block) {
        return RAIL_TYPES.contains(block.getType());
    }
}