/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.api.chunkgeneration.util;

import java.io.Serializable;
import java.util.Arrays;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.world.NativeWorld;

/**
 * An basic class to make an chunk whit no data
 *
 * @author Fernando
 */
public final class DefaultChunkMaker extends ChunkMaker implements Cloneable, Serializable {

    private static final long serialVersionUID = 111234729L;
    /**
     * The internal saved chunk
     */
    private final short[][] chunk;
    private final int ySize;

    public DefaultChunkMaker(NativeWorld world) {
        this(world.getMaxHeight());
    }

    public DefaultChunkMaker(int maxHeight) {
        this(new short[maxHeight / 16][], maxHeight);
    }

    @Deprecated
    public DefaultChunkMaker(byte[] chunk, int maxHeight) {
        this(new short[maxHeight / 16][], maxHeight);
        if (chunk.length != (maxHeight * 16 * 16)) {
            throw new IllegalArgumentException("Input must be byte[32768]");
        }
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < maxHeight; y++) {
                for (int z = 0; z < 16; z++) {
                    this.setB(x, y, z, chunk[(x * 16 + z) * 128 + y]);
                }
            }
        }
    }

    public DefaultChunkMaker(short[][] chunk, int maxHeight) {
        this.chunk = chunk;
        this.ySize = maxHeight;
    }


    @Override
    protected void setB(int x, int y, int z, short blkid) {
        if (chunk[y >> 4] == null) {
            chunk[y >> 4] = new short[4096];
        }
        chunk[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = blkid;

    }


    @Override
    protected short getB(int x, int y, int z) {
        if (chunk[y >> 4] == null) {
            return (short) 0;
        }

        return chunk[y >> 4][((y & 0xF) << 8) | (z << 4) | x];
    }


    /**
     * Exutes an function on an large number of blocks
     *
     * @param loc1 location 1 pointer
     * @param loc2 location 2 pointer
     * @param c The action to call
     * @throws NullPointerException If <code>loc1 == null</code> or
     * <code>loc2 == null</code> or <code>c == null</code>
     */
    @Override
    protected void action(Pointer loc1, Pointer loc2, ChunkHelper c) throws NullPointerException, IllegalArgumentException {
        this.checkPointers(loc1, loc2);
        this.checkSelection(loc1, loc2);
        for (int x = loc1.getX(); x <= loc2.getX(); x++) {
            for (int z = loc1.getZ(); z <= loc2.getZ(); z++) {
                for (int y = loc1.getY(); y <= loc2.getY(); y++) {
                    c.run(new Pointer(x, y, z), loc2, loc1);
                }
            }
        }

    }


    /**
     * Gets the raw chunk data
     *
     * @return the chunk data
     */
    @Override
    public short[][] getRawChunk() {
        return this.chunk;
    }

    @Override
    public DefaultChunkMaker clone() throws CloneNotSupportedException {
        return (DefaultChunkMaker) super.clone();

    }

    

    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DefaultChunkMaker other = (DefaultChunkMaker) obj;
        if (!Arrays.equals(this.chunk, other.chunk)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = Arrays.hashCode(this.chunk) ^ 1213675258;
        return hash;
    }

    @Override
    public String toString() {
        return "ChunkMaker: " + this.hashCode();
    }
    
    
}
