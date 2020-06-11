package org.eugenpaul.javaengine.core.algos.pathfinding;

import org.eugenpaul.javaengine.core.world.map.Immutable3dPoint;

/**
 * Implementation of GreedyBest algorithm in TileBasedMap.
 * 
 * @author Eugen Paul
 *
 */
public class GreedyBestTileBasedMap extends AWave {

  @Override
  protected long getHeuristicsCost(long wayCost, Immutable3dPoint a, Immutable3dPoint b, int simpleStepCost) {
    // Manhattan distance on a square grid
    return (Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY()) + Math.abs(a.getZ() - b.getZ())) * (long) simpleStepCost;
  }

}
