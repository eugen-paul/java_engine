package org.eugenpaul.javaengine.core.world.map;

import org.eugenpaul.javaengine.core.world.Position;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @author Eugen Paul
 *
 */
@AllArgsConstructor
@EqualsAndHashCode
public class Immutable3dPoint {

  public static final Immutable3dPoint NULL_POINT = new Immutable3dPoint(0, 0, 0);

  @Getter
  private int x;
  @Getter
  private int y;
  @Getter
  private int z;

  public Immutable3dPoint(Immutable3dPoint point) {
    x = point.getX();
    y = point.getY();
    z = point.getZ();
  }

  public Immutable3dPoint(Position point) {
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

  public Immutable3dPoint minus(Immutable3dPoint point) {
    return new Immutable3dPoint(x - point.getX(), y - point.getY(), z - point.getZ());
  }
  
  public Immutable3dPoint add(Immutable3dPoint point) {
    return new Immutable3dPoint(x + point.getX(), y + point.getY(), z + point.getZ());
  }

}
