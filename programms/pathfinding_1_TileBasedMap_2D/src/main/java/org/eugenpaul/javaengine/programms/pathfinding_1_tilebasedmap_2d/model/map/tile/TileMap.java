package org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.map.tile;

import org.eugenpaul.javaengine.core.world.map.sample.SimpleTileBased2dMap;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.WorldElements;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.map.IMapRepresentation;

public class TileMap extends SimpleTileBased2dMap implements IMapRepresentation {

  public TileMap(int sizeX, int sizeY) {
    super(sizeX, sizeY, WorldElements.NOPE);
  }

  @Override
  public WorldElements getPosition(int x, int y) {
    return (WorldElements) grid[xyzToElem(x, y)];
  }

  @Override
  public boolean setPosition(int x, int y, WorldElements element) {
    if (x < 0 || sizeX <= x) {
      return false;
    }
    if (y < 0 || sizeY <= y) {
      return false;
    }

    grid[xyzToElem(x, y)] = element;
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
    return 0;
  }

}
