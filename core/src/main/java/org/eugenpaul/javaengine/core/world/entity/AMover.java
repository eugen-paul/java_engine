package org.eugenpaul.javaengine.core.world.entity;

import java.util.List;

/**
 * The possibility of an entity to moving in the world.
 * 
 * @author Eugen Paul
 *
 */
public interface AMover extends Entity {
  /**
   * Get next possible Steps from state
   * 
   * @param state
   * @return
   */
  public abstract List<Step> getNextSteps(IMotionState state);

  /**
   * Heuristics cost of simple step.
   * 
   * @return
   */
  public abstract int getSimpleStepHeuristicsCost();

}
