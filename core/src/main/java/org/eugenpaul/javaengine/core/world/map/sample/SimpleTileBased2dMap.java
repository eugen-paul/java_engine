package org.eugenpaul.javaengine.core.world.map.sample;

import java.util.Arrays;
import java.util.List;

import org.eugenpaul.javaengine.core.world.entity.collision.ICollisionCondition;
import org.eugenpaul.javaengine.core.world.entity.collision.Sample2dCollisionCondition;
import org.eugenpaul.javaengine.core.world.map.ITileBasedMap;
import org.eugenpaul.javaengine.core.world.map.Immutable3dPoint;

public class SimpleTileBased2dMap implements ITileBasedMap {

  protected ICollisionCondition grid[][];
  protected int sizeX;
  protected int sizeY;

  public SimpleTileBased2dMap(int sizeX, int sizeY) {
    grid = new Sample2dCollisionCondition[sizeX][sizeY];
    for (ICollisionCondition[] is : grid) {
      Arrays.fill(is, Sample2dCollisionCondition.NOT);
    }
    this.sizeX = sizeX;
    this.sizeY = sizeY;
  }

  @Override
  public Immutable3dPoint getPathfinderSize() {
    return new Immutable3dPoint(sizeX, sizeY, 1);
  }

  public void setPoint(int x, int y, int z, Sample2dCollisionCondition value) {
    grid[x][y] = value;
  }

  public void setPoint(int x, int y, int z, int value) {
    grid[x][y] = Sample2dCollisionCondition.fromInt(value);
  }

  @Override
  public List<ICollisionCondition> getCollisionCondition(int x, int y, int z) {
    return List.of(grid[x][y]);
  }

  @Override
  public boolean isCollisionCondition(int x, int y, int z, ICollisionCondition condition) {
    return (condition == grid[x][y]);
  }

}
