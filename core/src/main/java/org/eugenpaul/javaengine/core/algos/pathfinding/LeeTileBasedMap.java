package org.eugenpaul.javaengine.core.algos.pathfinding;

import org.eugenpaul.javaengine.core.world.map.Immutable3dTilePoint;

/**
 * Implementation of Lee (wave propagation) algorithm in TileBasedMap.
 * 
 * @author Eugen Paul
 *
 */
public class LeeTileBasedMap extends AWave {

  @Override
  protected long getHeuristicsCost(long wayCost, Immutable3dTilePoint a, Immutable3dTilePoint b, int simpleStepCost) {
    return wayCost;
  }

}
