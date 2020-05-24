package org.eugenpaul.javaengine.core.world.map.sample;

import java.util.Arrays;
import java.util.List;

import org.eugenpaul.javaengine.core.world.entity.collision.ICollisionCondition;
import org.eugenpaul.javaengine.core.world.map.ITileBasedMap;
import org.eugenpaul.javaengine.core.world.map.Immutable3dPoint;

/**
 * Implementation of 2D-TileBasedMap.
 * 
 * @author Eugen Paul
 *
 */
public class SimpleTileBased2dMap implements ITileBasedMap {

  protected ICollisionCondition[] grid;
  protected int sizeX;
  protected int sizeY;

  public SimpleTileBased2dMap(int sizeX, int sizeY, ICollisionCondition stdValue) {
    grid = new ICollisionCondition[sizeX * sizeY];
    Arrays.fill(grid, stdValue);
    this.sizeX = sizeX;
    this.sizeY = sizeY;
  }

  @Override
  public Immutable3dPoint getPathfinderSize() {
    return new Immutable3dPoint(sizeX, sizeY, 1);
  }

  /**
   * Set point on map
   * 
   * @param x
   * @param y
   * @param value
   */
  public void setPoint(int x, int y, ICollisionCondition value) {
    grid[xyzToElem(x, y)] = value;
  }

  @Override
  public List<ICollisionCondition> getCollisionCondition(int x, int y, int z) {
    return List.of(grid[xyzToElem(x, y)]);
  }

  @Override
  public List<ICollisionCondition> getCollisionCondition(Immutable3dPoint position) {
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
    return grid[xyzToElem(x, y)].isSame(condition);
  }

  protected int xyzToElem(int x, int y) {
    return x * sizeY + y;
  }

}
