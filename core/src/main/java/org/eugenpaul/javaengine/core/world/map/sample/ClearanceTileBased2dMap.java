package org.eugenpaul.javaengine.core.world.map.sample;

import java.util.Arrays;
import java.util.List;

import org.eugenpaul.javaengine.core.world.entity.collision.Clearance2dCollisionCondition;
import org.eugenpaul.javaengine.core.world.entity.collision.ICollisionCondition;
import org.eugenpaul.javaengine.core.world.map.ITileBasedMap;
import org.eugenpaul.javaengine.core.world.map.Immutable3dTilePoint;

/**
 * Implementation of 2D-ClearanceTileBasedMap. Each Point on the Map cann be traversable or not.
 * 
 * @author Eugen Paul
 *
 */
public class ClearanceTileBased2dMap implements ITileBasedMap {

  protected int[] grid;
  protected int sizeX;
  protected int sizeY;

  public ClearanceTileBased2dMap(int sizeX, int sizeY) {
    grid = new int[sizeX * sizeY];
    Arrays.fill(grid, 1);
    this.sizeX = sizeX;
    this.sizeY = sizeY;

    computeClearance();
  }

  @Override
  public Immutable3dTilePoint getPathfinderSize() {
    return new Immutable3dTilePoint(sizeX, sizeY, 1);
  }

  /**
   * Set point on map
   * 
   * @param x
   * @param y
   * @param traversable
   */
  public void setPoint(int x, int y, boolean traversable) {
    if (traversable) {
      grid[xyzToElem(x, y)] = 1;
    } else {
      grid[xyzToElem(x, y)] = 0;
    }
    computeClearance();
  }

  @Override
  public List<ICollisionCondition> getCollisionCondition(int x, int y, int z) {
    return List.of(new Clearance2dCollisionCondition(grid[xyzToElem(x, y)]));
  }

  @Override
  public List<ICollisionCondition> getCollisionCondition(Immutable3dTilePoint position) {
    return getCollisionCondition(position.getX(), position.getY(), position.getZ());
  }

  @Override
  public boolean isCollisionCondition(int x, int y, int z, ICollisionCondition condition) {
    if (x < 0 || sizeX <= x) {
      return false;
    }
    if (y < 0 || sizeY <= y) {
      return false;
    }
    if (z != 0) {
      return false;
    }

    if (!(condition instanceof Clearance2dCollisionCondition)) {
      return false;
    }

    Clearance2dCollisionCondition testVar = (Clearance2dCollisionCondition) condition;
    return testVar.isSame(grid[xyzToElem(x, y)]);
  }

  protected int xyzToElem(int x, int y) {
    return x * sizeY + y;
  }

  protected void computeClearance() {
    for (int x = sizeX - 1; x >= 0; x--) {
      for (int y = sizeY - 1; y >= 0; y--) {
        if (0 == grid[xyzToElem(x, y)]) {
          // nothing to do
        } else if (x >= sizeX - 1) {
          grid[xyzToElem(x, y)] = 1;
        } else if (y >= sizeY - 1) {
          grid[xyzToElem(x, y)] = 1;
        } else {
          int valueX = grid[xyzToElem(x + 1, y)];
          int valueY = grid[xyzToElem(x, y + 1)];
          int valueXY = grid[xyzToElem(x + 1, y + 1)];
          int min = Math.min(valueX, valueY);
          min = Math.min(min, valueXY);
          grid[xyzToElem(x, y)] = min + 1;
        }
      }
    }
  }

}
