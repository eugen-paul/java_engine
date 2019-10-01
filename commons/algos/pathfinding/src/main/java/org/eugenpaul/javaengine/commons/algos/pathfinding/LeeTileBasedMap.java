package org.eugenpaul.javaengine.commons.algos.pathfinding;

/**
 * Implementarion of Lee algorithm in TileBasedMap.
 * 
 * @author Eugen Paul
 *
 */
public class LeeTileBasedMap extends AWave {

  @Override
  protected long getHeuristicsFullCost(long wayCost, int xFrom, int yFrom, int zFrom, int xTo, int yTo, int zTo, int simpleStepCost) {
    return wayCost;
  }

}
