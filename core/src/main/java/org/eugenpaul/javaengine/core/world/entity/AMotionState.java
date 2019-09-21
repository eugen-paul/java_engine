package org.eugenpaul.javaengine.core.world.entity;

import lombok.Getter;

/**
 * State of moving. Example: direction of the moving object.
 * 
 * @author Eugen Paul
 *
 */
@Getter
public abstract class AMotionState {
  /**
   * Check if the states are equals.
   * 
   * @param testState
   * @return
   */
  public abstract boolean checkState(AMotionState testState);

}
