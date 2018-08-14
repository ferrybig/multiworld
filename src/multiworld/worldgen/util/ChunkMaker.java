/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.worldgen.util;

import java.io.Serializable;
import java.util.Arrays;
import org.bukkit.World;
import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;

/**
 * An basic class to make an chunk whit no data
 * @author Fernando
 */
public final class ChunkMaker extends Object implements Cloneable, Serializable
{
	private static final long serialVersionUID = 111234729L;
	/**
	 * The internal saved chunk
	 */
	private final Material[][] chunk;
	private final int ySize;

	public ChunkMaker(World world) throws NullPointerException
	{
		this(world.getMaxHeight());
	}
	public ChunkMaker(int maxHeight) throws IllegalArgumentException, NullPointerException
	{
		this(new Material[maxHeight / 16][], maxHeight);
	}

	public ChunkMaker(Material[][] chunk, int maxHeight)
	{
		this.chunk = chunk;
		this.ySize = maxHeight;
	}

	/**
	 * Check if the given x, y and z is inside the chunk
	 * @param x The X to check
	 * @param y The Y to check
	 * @param z The Z to check
	 * @throws IllegalArgumentException If the given cordinates ar not inside this chunk
	 * 
	 */
	private void checkAccess(int x, int y, int z) throws IllegalArgumentException
	{
		if ((x < 0) || (x >= 16))
		{
			throw new IllegalArgumentException("X must be 0 <= x < 16");
		}
		if ((y < 0) || (y >= this.ySize))
		{
			throw new IllegalArgumentException("Y must be 0 <= x < 128");
		}
		if ((z < 0) || (z >= 16))
		{
			throw new IllegalArgumentException("Z must be 0 <= x < 16");
		}
	}

	private void checkSelection(int x1, int y1, int z1, int x2, int y2, int z2) throws IllegalArgumentException
	{
		if ((x1 > x2) || (y1 > y2) || (z1 > z2))
		{
			throw new IllegalArgumentException("the first point must be smaller than the second");
		}
	}

	private void checkSelection(Pointer loc1, Pointer loc2) throws IllegalArgumentException
	{
		this.checkSelection(loc1.x, loc1.y, loc1.z, loc2.x, loc2.y, loc2.z);
	}

	private void checkPointers(Pointer... list) throws IllegalArgumentException
	{
		for (Pointer p : list)
		{
			this.checkPointer(p);
		}
	}

	/**
	 * Checks if the given pointer points to this <code>ChunkMaker</code>
	 * @param p The Pointer to check
	 * @throws IllegalArgumentException if it don't point to this chunk
	 */
	private void checkPointer(Pointer p) throws IllegalArgumentException
	{
		if (p.getMainChunk() != this)
		{
			throw new IllegalArgumentException("The given pointer does not point to this chunk");
		}
	}

	private void setB(int x, int y, int z, Material blkid)
	{
		if (chunk[y >> 4] == null)
		{
			chunk[y >> 4] = new Material[4096];
			Arrays.fill(chunk[y >> 4], Material.AIR);
		}
		chunk[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = blkid;

	}

	/**
	 * Sets the block at the given cordiantes
	 * @param x The x to set
	 * @param y The y to set
	 * @param z The z to set
	 * @param block The block id of the new block
	 * @throws IllegalArgumentException when the given x,y,z are not valid locations
	 */
	public void setBlock(int x, int y, int z, Material block) throws IllegalArgumentException
	{
		this.checkAccess(x, y, z);
		this.setB(x, y, z, block);
	}

	/**
	 * Sets an block on the given location
	 * @param loc the location to set on
	 * @param block The blok to set
	 * @throws IllegalArgumentException If the location are not inside this chunk
	 * @throws NullPointerException If loc == null
	 */
	public void setBlock(Pointer loc, Material block) throws IllegalArgumentException, NullPointerException
	{
		this.setB(loc.x, loc.y, loc.z, block);
	}


	private Material getB(int x, int y, int z)
	{
		if (chunk[y >> 4] == null)
		{
			return Material.AIR;
		}

		return chunk[y >> 4][((y & 0xF) << 8) | (z << 4) | x];
	}

	/**
	 * get an block on the given space
	 * @param x The X cordinate
	 * @param y The Y cordinate
	 * @param z The z cordinate
	 * @return The block at the given locations
	 * @throws IllegalArgumentException
	 */
	public Material getBlock(int x, int y, int z) throws IllegalArgumentException
	{
		this.checkAccess(x, y, z);
		return this.getB(x, y, z);
	}

	/**
	 * Gets an block from this chunk
	 * @param loc The location to get
	 * @return The block on the given location
	 * @throws IllegalArgumentException If loc.getMainChunk != this
	 * @throws NullPointerException If loc == null
	 */
	public Material getBlock(Pointer loc) throws IllegalArgumentException, NullPointerException
	{
		this.checkPointer(loc);
		return this.getB(loc.x, loc.y, loc.z);
	}

	/**
	 * Exutes an function on an large number of blocks
	 * @param loc1 location 1 pointer
	 * @param loc2 location 2 pointer
	 * @param c The action to call
	 * @throws NullPointerException If <code>loc1 == null</code> or <code>loc2 == null</code> or <code>c == null</code>
	 */
	private void action(Pointer loc1, Pointer loc2, ChunkHelper c) throws NullPointerException, IllegalArgumentException
	{
		this.checkPointers(loc1, loc2);
		this.checkSelection(loc1, loc2);
		for (int x = loc1.getX(); x <= loc2.getX(); x++)
		{
			for (int z = loc1.getZ(); z <= loc2.getZ(); z++)
			{
				for (int y = loc1.getY(); y <= loc2.getY(); y++)
				{
					c.run(new Pointer(x, y, z), loc2, loc1);
				}
			}
		}
	}

	/**
	 * Fills an area whit blocks
	 * @param x1 location 1 X
	 * @param y1 location 1 Y
	 * @param z1 location 1 Z
	 * @param x2 location 2 X
	 * @param y2 location 2 Y
	 * @param z2 location 2 Z
	 * @param block The block to fill it whit
	 * @throws IllegalArgumentException if the cordinatews given dont give an good area
	 */
	public void cuboid(int x1, int y1, int z1, int x2, int y2, int z2, Material block) throws IllegalArgumentException
	{
		this.cuboid(this.getPointer(x1, y1, z1), this.getPointer(x2, y2, z2), block);
	}

	/**
	 * Fills an area with blocks
	 * @param loc1 Location 1
	 * @param loc2 Location 2
	 * @param block The block to fill it whit
	 * @throws IllegalArgumentException If the location arguments dont cover an proer location
	 * @throws NullPointerException if loc1 == null, or loc2 == null
	 */
	public void cuboid(Pointer loc1, Pointer loc2, final Material block) throws IllegalArgumentException, NullPointerException
	{
		this.action(loc1, loc2, new ChunkHelper()
		{
			@Override
			public void run(Pointer target, Pointer selection1, Pointer selection2)
			{
				target.setBlock(block);
			}
		});
	}

	/**
	 * replaces blocks at the selected area
	 * @param x1
	 * @param y1
	 * @param z1
	 * @param x2
	 * @param y2
	 * @param z2
	 * @param blockFrom the block to replace from
	 * @param blockTo the block to replace to
	 * @throws IllegalArgumentException
	 */
	public void replace(int x1, int y1, int z1, int x2, int y2, int z2, Material blockFrom, Material blockTo) throws IllegalArgumentException
	{
		this.replace(this.getPointer(x1, y1, z1), this.getPointer(x2, y2, z2), blockFrom, blockTo);
	}

	/**
	 * replaces blocks at the selected area
	 * @param loc1
	 * @param loc2
	 * @param from the block to replace from
	 * @param to the block to replace to
	 * @throws IllegalArgumentException
	 * @throws NullPointerException
	 */
	public void replace(Pointer loc1, Pointer loc2, final Material from, final Material to) throws IllegalArgumentException, NullPointerException
	{
		this.action(loc1, loc2, new ChunkHelper()
		{
			@Override
			public void run(Pointer target, Pointer selection1, Pointer selection2)
			{
				if (target.getBlock() == from)
				{
					target.setBlock(to);
				}
			}
		});
	}

	/**
	 * Add walls around the selected area
	 * @param loc1
	 * @param loc2
	 * @param block
	 * @throws IllegalArgumentException
	 * @throws NullPointerException
	 */
	public void walls(Pointer loc1, Pointer loc2, final Material block) throws IllegalArgumentException, NullPointerException
	{
		this.action(loc1, loc2, new ChunkHelper()
		{
			@Override
			public void run(Pointer t, Pointer a, Pointer b)
			{
				if ((t.x == a.x) || (t.x == b.x) || (t.z == a.z) || (t.z == b.z))
				{
					t.setBlock(block);
				}

			}
		});
	}

	/**
	 * add walls around the specified area
	 * @param x1
	 * @param y1
	 * @param z1
	 * @param x2
	 * @param y2
	 * @param z2
	 * @param block
	 * @throws IllegalArgumentException
	 */
	public void walls(int x1, int y1, int z1, int x2, int y2, int z2, Material block) throws IllegalArgumentException
	{
		this.walls(this.getPointer(x1, y1, z1), this.getPointer(x2, y2, z2), block);
	}

	/**
	 * Gets an pointer for an location
	 * @param x the x from the location
	 * @param y the y from the location
	 * @param z the z from the location
	 * @return the pointer that points to this location
	 * @throws IllegalArgumentException if the given location is not inside this chunk
	 */
	public Pointer getPointer(int x, int y, int z) throws IllegalArgumentException
	{
		return new Pointer(x, y, z);
	}

	/**
	 * Gets the raw chunk data
	 * @return the chunk data
	 */
	public Material[][] getRawChunk()
	{
		return this.chunk;
	}

	@Override
	public ChunkMaker clone() throws CloneNotSupportedException
	{
		return (ChunkMaker) super.clone();


	}

	/**
	 * The class used for locations
	 */
	public class Pointer implements Cloneable, Serializable, Comparable<Pointer>
	{
		private static final long serialVersionUID = 56874873276L;
		/**
		 * The x cordinate
		 */
		public final int x;
		/**
		 * The Y cordinate
		 */
		public final int y;
		/**
		 * The z cordinate
		 */
		public final int z;

		/**
		 * Creates an pointer whit locations 0,0,0
		 */
		public Pointer()
		{
			this(0, 0, 0);
		}

		/**
		 * Makes an pointer
		 * @param x
		 * @param y
		 * @param z
		 * @throws IllegalArgumentException if the cordinates are not inside the main chunk
		 */
		public Pointer(int x, int y, int z) throws IllegalArgumentException
		{
			ChunkMaker.this.checkAccess(x, y, z);
			this.x = x;
			this.y = y;
			this.z = z;
		}

		/**
		 * Gets the chunk that definited this pointer
		 * @return the chunk that created this pointer
		 */
		public ChunkMaker getMainChunk()
		{
			return ChunkMaker.this;
		}

		/**
		 * get the X from selection
		 * @return the x
		 */
		public int getX()
		{
			return this.x;
		}

		/**
		 * gets the Y from the pointer
		 * @return the y
		 */
		public int getY()
		{
			return this.y;
		}

		/**
		 * get the Z from the pointer
		 * @return the Z
		 */
		public int getZ()
		{
			return this.z;
		}

		/**
		 * Sets the block on this location
		 * @param block the block id to place on the location
		 */
		public void setBlock(Material block)
		{
			ChunkMaker.this.setB(this.x, this.y, this.z, block);
		}

		public Material getBlock()
		{
			return ChunkMaker.this.getB(this.x, this.y, this.z);
		}

		private int getIndex()
		{
			return (this.x * 16 + this.z) * 128 + this.y;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (obj == null)
			{
				return false;
			}
			if (getClass() != obj.getClass())
			{
				return false;
			}
			final Pointer other = (Pointer) obj;
			if (this.x != other.x)
			{
				return false;
			}
			if (this.y != other.y)
			{
				return false;
			}
			if (this.z != other.z)
			{
				return false;
			}
			return true;
		}

		@Override
		public int hashCode()
		{
			int hash = 7;
			hash = 4 * hash + this.x;
			hash = 7 * hash + this.y;
			hash = 4 * hash + this.z;
			return hash;
		}

		@Override
		public String toString()
		{
			return "Pointer{x=" + x + ",y=" + y + ",z=" + z + "}";
		}

		@Override
		public Pointer clone() throws CloneNotSupportedException
		{
			return (Pointer) super.clone();
		}

		@Override
		public int compareTo(Pointer o)
		{
			return this.getIndex() - o.getIndex();
		}
	}

	private interface ChunkHelper
	{
		public void run(Pointer target, Pointer selection1, Pointer selection2);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final ChunkMaker other = (ChunkMaker) obj;
		if (!Arrays.equals(this.chunk, other.chunk))
		{
			return false;
		}
		return true;
	}

	@Override
	public int hashCode()
	{
		int hash = Arrays.hashCode(this.chunk) ^ 1213675258;
		return hash;
	}

	@Override
	public String toString()
	{
		return "ChunkMaker:" + this.hashCode();
	}

	public ChunkGenerator.ChunkData toChunkData(ChunkGenerator.ChunkData chunkData) {
		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				for (int y = 0; y < 256; y++)
				{
					if (chunk[y >> 4] != null) {
						chunkData.setBlock(x, y, z, getB(x, y, z));
					}
				}
			}
		}
		return chunkData;
	}
}
