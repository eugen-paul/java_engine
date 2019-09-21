package org.eugenpaul.javaengine.programms.learn_pathfinding.model;

import org.eugenpaul.javaengine.core.world.entity.AMotionState;
import org.eugenpaul.javaengine.core.world.entity.AMover;
import org.eugenpaul.javaengine.core.world.entity.MoveDirection;
import org.eugenpaul.javaengine.core.world.entity.sample.Simple2dMover;
import org.eugenpaul.javaengine.core.world.entity.sample.SimpleMotionState;
import org.eugenpaul.javaengine.core.world.entity.sample.SimpleVector2dMover;
import org.eugenpaul.javaengine.core.world.entity.sample.SimpleVectorMotionState;

import lombok.Getter;

public enum MoverTyp {
  Simple2dMoverSlim(new Simple2dMover(WorldElements.NOPE, true), "Slim", new SimpleMotionState(), new SimpleMotionState()), //
  Simple2dMoverFat(new Simple2dMover(WorldElements.NOPE, false), "Fat", new SimpleMotionState(), new SimpleMotionState()), //
  Simple2dVectorMoverXPXP(new SimpleVector2dMover(WorldElements.NOPE), "Vector XPXP", new SimpleVectorMotionState(MoveDirection.X_POSITIVE), new SimpleVectorMotionState(MoveDirection.X_POSITIVE)), //
  Simple2dVectorMoverXPXN(new SimpleVector2dMover(WorldElements.NOPE), "Vector XPXN", new SimpleVectorMotionState(MoveDirection.X_POSITIVE), new SimpleVectorMotionState(MoveDirection.X_NEGATIVE)), //
  Simple2dVectorMoverYPYP(new SimpleVector2dMover(WorldElements.NOPE), "Vector YPYP", new SimpleVectorMotionState(MoveDirection.Y_POSITIVE), new SimpleVectorMotionState(MoveDirection.Y_POSITIVE));

  @Getter
  private AMover mover;

  @Getter
  private String text;

  @Getter
  private AMotionState startState;
  @Getter
  private AMotionState endState;

  private MoverTyp(AMover mover, String text, AMotionState startState, AMotionState endState) {
    this.mover = mover;
    this.text = text;
    this.startState = startState;
    this.endState = endState;
  }

  @Override
  public String toString() {
    return text;
  }
}
