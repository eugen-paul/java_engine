package org.eugenpaul.javaengine.commons.algos.pathfinding;

import org.eugenpaul.javaengine.core.world.map.Immutable3dPoint;

/**
 * Implementation of Lee (wave propagation) algorithm in TileBasedMap.
 * 
 * @author Eugen Paul
 *
 */
public class LeeTileBasedMap extends AWave {

  @Override
  protected long getHeuristicsCost(long wayCost, Immutable3dPoint a, Immutable3dPoint b, int simpleStepCost) {
    return wayCost;
  }

}
