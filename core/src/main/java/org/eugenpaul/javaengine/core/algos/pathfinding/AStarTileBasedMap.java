package org.eugenpaul.javaengine.core.algos.pathfinding;

import org.eugenpaul.javaengine.core.world.map.Immutable3dTilePoint;

/**
 * Implementation of AStar algorithm in TileBasedMap.
 * 
 * @author Eugen Paul
 *
 */
public class AStarTileBasedMap extends AWave {

  @Override
  protected long getHeuristicsCost(long wayCost, Immutable3dTilePoint a, Immutable3dTilePoint b, int simpleStepCost) {
    // Manhattan distance on a square grid
    return (Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY()) + Math.abs(a.getZ() - b.getZ())) * simpleStepCost + wayCost;
  }

}
