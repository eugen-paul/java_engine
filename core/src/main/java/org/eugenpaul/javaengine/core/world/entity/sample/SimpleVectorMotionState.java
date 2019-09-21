package org.eugenpaul.javaengine.core.world.entity.sample;

import org.eugenpaul.javaengine.core.world.entity.AMotionState;
import org.eugenpaul.javaengine.core.world.entity.MoveDirection;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Motion State with direction.
 * 
 * @author Eugen Paul
 *
 */
@AllArgsConstructor
public class SimpleVectorMotionState extends AMotionState {

  @Getter
  private MoveDirection moveDirection;

  @Override
  public boolean checkState(AMotionState testRules) {
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
