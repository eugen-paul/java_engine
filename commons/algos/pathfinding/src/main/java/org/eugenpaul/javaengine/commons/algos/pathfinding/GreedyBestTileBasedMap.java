package org.eugenpaul.javaengine.commons.algos.pathfinding;

/**
 * Implementarion of Lee algorithm in TileBasedMap.
 * 
 * @author Eugen Paul
 *
 */
public class GreedyBestTileBasedMap extends AWave {

  @Override
  protected long getHeuristicsFullCost(long wayCost, int xFrom, int yFrom, int zFrom, int xTo, int yTo, int zTo, int simpleStepCost) {
    // Manhattan distance on a square grid
    return (Math.abs(xTo - xFrom) + Math.abs(yTo - yFrom) + Math.abs(zTo - zFrom)) * simpleStepCost;
  }

}
