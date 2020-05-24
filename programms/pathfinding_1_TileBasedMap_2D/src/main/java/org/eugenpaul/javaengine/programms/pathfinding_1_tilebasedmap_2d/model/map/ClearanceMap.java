package org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.map;

import org.eugenpaul.javaengine.core.world.entity.collision.ICollisionCondition;
import org.eugenpaul.javaengine.core.world.map.sample.ClearanceTileBased2dMap;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.WorldElements;

public class ClearanceMap extends ClearanceTileBased2dMap implements IMapRepresentation {

  public ClearanceMap(int sizeX, int sizeY) {
    super(sizeX, sizeY);
  }

  @Override
  public WorldElements getPosition(int x, int y) {
    if (0 == grid[xyzToElem(x, y)]) {
      return WorldElements.WALL;
    }
    return WorldElements.NOPE;
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

    if (!(condition instanceof WorldElements)) {
      return false;
    }

    WorldElements testVar = (WorldElements) condition;
    return getPosition(x, y).isSame(testVar);
  }

  @Override
  public boolean setPosition(int x, int y, WorldElements element) {
    if (x < 0 || sizeX <= x) {
      return false;
    }
    if (y < 0 || sizeY <= y) {
      return false;
    }

    grid[xyzToElem(x, y)] = (element == WorldElements.WALL) ? 0 : 1;
    computeClearance();
    return true;
  }

  @Override
  public int getSiezX() {
    return sizeX;
  }

  @Override
  public int getSiezY() {
    return sizeY;
  }

  @Override
  public int getValue(int x, int y) {
    return grid[xyzToElem(x, y)];
  }

}
