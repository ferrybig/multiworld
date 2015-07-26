/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.api.chunkgeneration.populators;

import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.generation.NativeBlockPopulator;
import java.util.Random;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.Native;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.entities.NativeCreatureType;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.inventory.NativeInventory;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.inventory.NativeItemManager;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.inventory.NativeItemStack;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.materials.NativeMaterial;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.materials.NativeMaterials;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.NativeBlock;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.NativeChunk;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.NativeWorld;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.state.NativeBlockState;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.state.NativeMobSpawner;

/**
 *
 * @author Fernando
 */
public class DungeonPopulator implements NativeBlockPopulator {

    private final Native nativeStack;
    private final NativeItemManager items;
    private final NativeMaterials materials;
    private final Random random;
    private final int maxWidth;
    private final int maxLength;
    private final int height;

    public DungeonPopulator(Native nativeStack, NativeItemManager items, NativeMaterials materials,
            Random random) {
        this(nativeStack, items, materials, random, 2, 2, 4);
    }

    public DungeonPopulator(Native nativeStack, NativeItemManager items, NativeMaterials materials,
            Random random, int maxWidth, int maxLength, int height) {
        this.nativeStack = nativeStack;
        this.items = items;
        this.materials = materials;
        this.random = random;
        this.maxWidth = maxWidth;
        this.maxLength = maxLength;
        this.height = height;
    }

    @Override
    public void populateWorld(NativeChunk chunk) {
        for (int i33 = 0; i33 < 8; i33++) {
            final int dungeonX = chunk.getX() * 16 + random.nextInt(16) + 8;
            final int dungeonY = random.nextInt(250);
            final int dungeonZ = chunk.getZ() * 16 + random.nextInt(16) + 8;

            final int dungeonWidthX = random.nextInt(2) + 2;
            final int dungeonWidthY = 3;
            final int dungeonWidthZ = random.nextInt(2) + 2;

            final int dungeonMaxXPoint = dungeonX + dungeonWidthX + 1;
            final int dungeonMaxYPoint = dungeonY + dungeonWidthY + 1;
            final int dungeonMaxZPoint = dungeonZ + dungeonWidthZ + 1;

            final int dungeonMinXPoint = dungeonX - dungeonWidthX - 1;
            final int dungeonMinYPoint = dungeonY - 1;
            final int dungeonMinZPoint = dungeonZ - dungeonWidthZ - 1;

            int counterX;
            int counterY;
            int counterZ;

            int tmpX;
            int tmpY;
            int tmpZ;

            NativeBlock block;
            NativeWorld world = chunk.getWorld();

            for (counterX = dungeonMinXPoint; counterX <= dungeonMaxXPoint; counterX++) {
                for (counterY = dungeonMinYPoint; counterY <= dungeonMaxYPoint; counterY++) {
                    for (counterZ = dungeonMinZPoint; counterZ <= dungeonMaxZPoint; counterZ++) {

                        if ((counterY == dungeonMinYPoint) || (counterY == dungeonMaxYPoint)) {
                            NativeMaterial localMaterial = world.getBlockAt(counterX, counterY, counterZ).getType();
                            if (!isBlock(localMaterial)) {
                                return;
                            }
                        }

                    }

                }

            }

            for (counterX = dungeonMinXPoint; counterX <= dungeonMaxXPoint; counterX++) {
                for (counterY = dungeonY + dungeonWidthY; counterY >= dungeonMinYPoint; counterY--) {
                    for (counterZ = dungeonMinZPoint; counterZ <= dungeonMaxZPoint; counterZ++) {
                        block = world.getBlockAt(counterX, counterY, counterZ);
                        if ((counterX == dungeonMinXPoint) || (counterY == dungeonMinYPoint)
                                || (counterZ == dungeonMinZPoint) || (counterX == dungeonMaxXPoint)
                                || (counterY == dungeonMaxYPoint) || (counterZ == dungeonMaxZPoint)) {
                            // Block is at side
                            if ((counterY >= 0) && (!isBlock(world.getBlockAt(counterX, counterY - 1, counterZ).getType()))) {
                                // Block is at roof
                                block.setType(materials.requireMaterial("minecraft:air"));
                            } else if (isBlock(block.getType())) {
                                // Block is at floor or wall
                                if ((counterY == dungeonMinYPoint) && (random.nextInt(4) != 0)) {
                                    block.setType(materials.requireMaterial("minecraft:mossstone"));
                                } else {
                                    block.setType(materials.requireMaterial("minecraft:cobblestone"));
                                }
                            }
                        } else {
                            // block is inside
                            block.setType(materials.requireMaterial("minecraft:air"));
                        }
                    }
                }
            }

            // Place 2 chests
            for (counterX = 0; counterX < 2; counterX++) {
                // Try 3 times for each chest
                for (counterZ = 0; counterZ < 3; counterZ++) {
                    tmpX = dungeonX + random.nextInt(dungeonWidthX * 2 + 1) - dungeonWidthX;
                    tmpY = dungeonY;
                    tmpZ = dungeonZ + random.nextInt(dungeonWidthZ * 2 + 1) - dungeonWidthZ;

                    if (!isEmpty(world, tmpX, tmpY, tmpZ)) {
                        continue;
                    }

                    //Check if block is side at room
                    int fullSides = 0;
                    if (isBlock(world.getBlockAt(tmpX - 1, tmpY, tmpZ).getType())) {
                        fullSides++;
                    }
                    if (isBlock(world.getBlockAt(tmpX + 1, tmpY, tmpZ).getType())) {
                        fullSides++;
                    }
                    if (isBlock(world.getBlockAt(tmpX, tmpY, tmpZ - 1).getType())) {
                        fullSides++;
                    }
                    if (isBlock(world.getBlockAt(tmpX, tmpY, tmpZ + 1).getType())) {
                        fullSides++;
                    }
                    if (fullSides == 0) {
                        // Skip if not on the side
                        continue;
                    }

                    //Place an chest
                    block = world.getBlockAt(tmpX, tmpY, tmpZ);
                    block.setType(materials.requireMaterial("minecraft:chest"));
                    NativeBlockState localTileEntityChest = block.getBlockState();
                    NativeInventory inv = localTileEntityChest.getInventory();
                    for (int i6 = 0; i6 < 8; i6++) {
                        NativeItemStack randomItem = getRandomItem();
                        if (randomItem == null) {
                            continue;
                        }
                        inv.setItem(random.nextInt(inv.getSize()), randomItem);
                    }
                    // Prevent another chest placed next to this:
                    break;
                }

            }
            // Get spawner location
            block = world.getBlockAt(dungeonX, dungeonY, dungeonZ);

            // Place spawner
            block.setType(materials.requireMaterial("minecraft:chest"));

            // Chance spawned mob
            NativeMobSpawner spawner = (NativeMobSpawner) block.getBlockState();
            spawner.setType(this.getRandomMob(random));
        }
    }

    private NativeItemStack getRandomItem() {
        int i = random.nextInt(11);
        if (i == 0) {
            return items.createItemStack(materials.requireMaterial("minecraft:SADDLE"));
        }
        if (i == 1) {
            return items.createItemStack(materials.requireMaterial("minecraft:IRON_INGOT"), (byte)(random.nextInt(4) + 1));
        }
        if (i == 2) {
            return items.createItemStack(materials.requireMaterial("minecraft:BREAD"));
        }
        if (i == 3) {
            return items.createItemStack(materials.requireMaterial("minecraft:WHEAT"), (byte)(random.nextInt(4) + 1));
        }
        if (i == 4) {
            return items.createItemStack(materials.requireMaterial("minecraft:SULPHUR"), (byte)(random.nextInt(4) + 1));
        }
        if (i == 5) {
            return items.createItemStack(materials.requireMaterial("minecraft:STRING"), (byte)(random.nextInt(4) + 1));
        }
        if (i == 6) {
            return items.createItemStack(materials.requireMaterial("minecraft:BUCKET"));
        }
        if ((i == 7) && (random.nextInt(100) == 0)) {
            return items.createItemStack(materials.requireMaterial("minecraft:GOLDEN_APPLE"));
        }
        if ((i == 8) && (random.nextInt(2) == 0)) {
            return items.createItemStack(materials.requireMaterial("minecraft:REDSTONE"), (byte)(random.nextInt(4) + 1));
        }
        if ((i == 9) && (random.nextInt(10) == 0)) {
            return items.createItemStack(materials.requireMaterial("minecraft:GOLD_RECORD"));
        }
        if (i == 10) {
            return items.createItemStack(materials.requireMaterial("minecraft:INK_SACK"), (byte)1, (short) 3);
        }

        return null;
    }

    private NativeCreatureType getRandomMob(Random paramRandom) {
        int i = paramRandom.nextInt(4);
        if (i == 0) {
            return NativeCreatureType.SKELETON;
        }
        if (i == 1) {
            return NativeCreatureType.ZOMBIE;
        }
        if (i == 2) {
            return NativeCreatureType.ZOMBIE;
        }
        if (i == 3) {
            return NativeCreatureType.SPIDER;
        }
        return NativeCreatureType.UNKNOWN;
    }

    private boolean isBlock(NativeMaterial mat) {
        return mat.isFullBlock();
    }

    public boolean isEmpty(NativeWorld world, int x, int y, int z) {
        return !world.getBlockAt(x, y, z).getType().isFullBlock();
    }
}
