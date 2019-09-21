package org.eugenpaul.javaengine.core.world.entity.sample;

import org.eugenpaul.javaengine.core.world.entity.AMotionState;

import lombok.NoArgsConstructor;

/**
 * Very simple state. Without direction, speed, ... All SimpleMotionStates are the same.
 * 
 * @author Eugen Paul
 *
 */
@NoArgsConstructor
public class SimpleMotionState extends AMotionState {
  @Override
  public boolean checkState(AMotionState testRules) {
    return true;
  }
}
