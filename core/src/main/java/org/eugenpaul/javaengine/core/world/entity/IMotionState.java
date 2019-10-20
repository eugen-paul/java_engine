package org.eugenpaul.javaengine.core.world.entity;

/**
 * State of moving. Example: direction of the moving object.
 * 
 * @author Eugen Paul
 *
 */
public interface IMotionState {
  /**
   * Check if the states are equals.
   * 
   * @param testState
   * @return
   */
  public abstract boolean isSame(IMotionState testState);

}
