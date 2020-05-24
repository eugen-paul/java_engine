package org.eugenpaul.javaengine.core.world.map.sample;

import java.util.Arrays;
import java.util.List;

import org.eugenpaul.javaengine.core.world.entity.collision.Clearance2dCollisionCondition;
import org.eugenpaul.javaengine.core.world.entity.collision.ICollisionCondition;
import org.eugenpaul.javaengine.core.world.entity.collision.Sample2dCollisionCondition;
import org.eugenpaul.javaengine.core.world.map.ITileBasedMap;
import org.eugenpaul.javaengine.core.world.map.Immutable3dPoint;

/**
 * Implementation of 2D-ClearanceTileBasedMap. Each Point on the Map cann be traversable or not.
 * 
 * @author Eugen Paul
 *
 */
public class ClearanceTileBased2dMap implements ITileBasedMap {

  protected int[][] grid;
  protected int sizeX;
  protected int sizeY;

  public ClearanceTileBased2dMap(int sizeX, int sizeY) {
    grid = new int[sizeX][sizeY];
    for (int[] is : grid) {
      Arrays.fill(is, 0);
    }
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
  public void setPoint(int x, int y, Sample2dCollisionCondition value) {
    if (Sample2dCollisionCondition.BARRIER == value) {
      grid[x][y] = 0;
    } else {
      grid[x][y] = 1;
    }
  }

  @Override
  public List<ICollisionCondition> getCollisionCondition(int x, int y, int z) {
    if (grid[x][y] > 0) {
      return List.of(Sample2dCollisionCondition.NOT);
    } else {
      return List.of(Sample2dCollisionCondition.BARRIER);
    }
  }

  @Override
  public List<ICollisionCondition> getCollisionCondition(Immutable3dPoint position) {
    return getCollisionCondition(position.getX(), position.getY(), position.getZ());
  }

  @Override
  public boolean isCollisionCondition(int x, int y, int z, ICollisionCondition condition) {
    if (!(condition instanceof Clearance2dCollisionCondition)) {
      return false;
    }

    Clearance2dCollisionCondition testVar = (Clearance2dCollisionCondition) condition;
    return testVar.isSame(grid[x][y]);
  }

  private void computeClearance(int startX, int startY) {

  }

}
