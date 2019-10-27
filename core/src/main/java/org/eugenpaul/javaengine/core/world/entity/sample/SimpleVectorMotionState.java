package org.eugenpaul.javaengine.core.world.entity.sample;

import org.eugenpaul.javaengine.core.world.entity.IMotionState;
import org.eugenpaul.javaengine.core.world.entity.MoveDirection;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Motion State with direction. The two SimpleVectorMotionState are the same if the direction of both states are the same.
 * 
 * @author Eugen Paul
 *
 */
@AllArgsConstructor
public class SimpleVectorMotionState implements IMotionState {

  @Getter
  private MoveDirection moveDirection;

  @Override
  public boolean isSame(IMotionState testRules) {
    if (!(testRules instanceof SimpleVectorMotionState)) {
      throw new IllegalArgumentException();
    }

    return ((SimpleVectorMotionState) testRules).getMoveDirection() == moveDirection;
  }

  @Override
  public String toString() {
    return "Motion: " + moveDirection.toString();
  }
}
