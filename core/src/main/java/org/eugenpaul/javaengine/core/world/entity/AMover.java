package org.eugenpaul.javaengine.core.world.entity;

import java.util.List;

/**
 * 
 * @author Eugen Paul
 *
 */
public abstract class AMover extends Entity {
  /**
   * Get next possible Steps from state
   * 
   * @param state
   * @return
   */
  public abstract List<Step> getNextSteps(AMotionState state);

  /**
   * Heuristics cost of simple step.
   * 
   * @return
   */
  public abstract int getSimpleStepHeuristicsCost();

//  /**
//   * Get next possible Steps from state and position on the map
//   * 
//   * @param state
//   * @param position
//   * @param map
//   * @return
//   */
//  public abstract List<Step> getNextSteps(AMotionState state, Immutable3dPoint position, TileBasedMap map);
//
//  /**
//   * Get next possible Steps from state and position on the map
//   * 
//   * @param state
//   * @param x
//   * @param y
//   * @param z
//   * @param map
//   * @return
//   */
//  public abstract List<Step> getNextSteps(AMotionState state, int x, int y, int z, TileBasedMap map);
}
