package org.eugenpaul.javaengine.core.world.map;

import org.eugenpaul.javaengine.core.world.Position;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Immutable point in 3D Tile Based Map.
 * 
 * @author Eugen Paul
 *
 */
@AllArgsConstructor
@EqualsAndHashCode
public class Immutable3dTilePoint {

  public static final Immutable3dTilePoint NULL_POINT = new Immutable3dTilePoint(0, 0, 0);

  @Getter
  private int x;
  @Getter
  private int y;
  @Getter
  private int z;

  public Immutable3dTilePoint(Immutable3dTilePoint point) {
    x = point.getX();
    y = point.getY();
    z = point.getZ();
  }

  public Immutable3dTilePoint(Position point) {
    x = (int) point.getX();
    y = (int) point.getY();
    z = (int) point.getZ();
  }

  @Override
  public String toString() {
    return "X = " + x //
        + ", Y = " + y //
        + ", Z = " + z //
    ;
  }

  public Immutable3dTilePoint minus(Immutable3dTilePoint point) {
    return new Immutable3dTilePoint(x - point.getX(), y - point.getY(), z - point.getZ());
  }

  public Immutable3dTilePoint add(Immutable3dTilePoint point) {
    return new Immutable3dTilePoint(x + point.getX(), y + point.getY(), z + point.getZ());
  }

}
