package org.eugenpaul.javaengine.core.world.entity.sample;

import org.eugenpaul.javaengine.core.world.entity.IMotionState;

import lombok.NoArgsConstructor;

/**
 * Very simple state. Without direction, speed, ... All SimpleMotionStates are the same.
 * 
 * @author Eugen Paul
 *
 */
@NoArgsConstructor
public class SimpleMotionState implements IMotionState {
  @Override
  public boolean isSame(IMotionState testRules) {
    return true;
  }
}
