/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.natives.location;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 *
 * @author Fernando
 */
public enum NativeDirection {

    SOUTH_WEST_WEST(0.382683, 0, 0.923880),
    SOUTH_WEST(0.707107, 0, 0.707107),
    SOUTH_SOUTH_WEST(0.923880, 0, 0.382683),
    SOUTH(1.000000, 0, 0.000000),
    SOUTH_SOUTH_EAST(0.923880, 0, -0.382683),
    SOUTH_EAST(0.707107, 0, -0.707107),
    SOUTH_EAST_EAST(0.382683, 0, -0.923880),
    EAST(0.000000, 0, -1.000000),
    NORTH_EAST_EAST(-0.382683, 0, -0.923880),
    NORTH_EAST(-0.707107, 0, -0.707107),
    NORTH_NORTH_EAST(-0.923880, 0, -0.382683),
    NORTH(-1.000000, 0, -0.000000),
    NORTH_NORTH_WEST(-0.923880, 0, 0.382683),
    NORTH_WEST(-0.707107, 0, 0.707107),
    NORTH_WEST_WEST(-0.382683, 0, 0.923880),
    WEST(-0.000000, 0, 1.000000),
    UP(0, 1, 0),
    DOWN(0, -1, 0),
    SELF(0, 0, 0),;

    private static final Collection<NativeDirection> ALL
            = Collections.unmodifiableList(Arrays.asList(values()));

    private final double x;
    private final double y;
    private final double z;

    private NativeDirection(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public static NativeDirection getClosestDirection(NativeDirection target, Collection<NativeDirection> allowed) {
        return getClosestDirection(target.getX(), target.getY(), target.getZ(), false, allowed);

    }

    public static NativeDirection getClosestDirection(double tx, double ty, double tz, boolean normalize) {
        return getClosestDirection(tx, ty, tz, normalize, ALL);

    }

    /**
     * get the closest direction for the
     *
     * @param tx
     * @param ty
     * @param tz
     * @param normalize
     * @param allowed
     * @return
     */
    public static NativeDirection getClosestDirection(double tx, double ty, double tz, boolean normalize, Collection<NativeDirection> allowed) {
        if (normalize) {
            double total = tx * tx + ty * ty + tz + tz;
            // Prevent unneeded cpu cycles here
            if (total != 0 && total != 1) {
                total = Math.sqrt(total);
                tx /= total;
                ty /= total;
                tz /= total;
            }
        }
        double dist = 0;
        NativeDirection closest = null;
        for (NativeDirection d : allowed) {
            double distance = Math.abs(tx * tx - d.x * d.x)
                    + Math.abs(ty * ty - d.y * d.y)
                    + Math.abs(tz * tz - d.z * d.z);
            if (closest == null || distance < dist) {
                closest = d;
                dist = distance;
            }
        }
        return closest;
    }

}
