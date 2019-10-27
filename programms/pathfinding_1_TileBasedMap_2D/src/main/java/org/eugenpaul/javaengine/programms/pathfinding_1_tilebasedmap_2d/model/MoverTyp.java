package org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model;

import org.eugenpaul.javaengine.core.world.entity.IMotionState;
import org.eugenpaul.javaengine.core.world.entity.AMover;
import org.eugenpaul.javaengine.core.world.entity.MoveDirection;
import org.eugenpaul.javaengine.core.world.entity.sample.Simple2dMover;
import org.eugenpaul.javaengine.core.world.entity.sample.SimpleMotionState;
import org.eugenpaul.javaengine.core.world.entity.sample.SimpleVector2dMover;
import org.eugenpaul.javaengine.core.world.entity.sample.SimpleVectorMotionState;

import lombok.Getter;

/**
 * Mover types.
 * 
 * @author Eugen Paul
 *
 */
public enum MoverTyp {
  SIMPLE_2D_MOVER_SLIM(new Simple2dMover(WorldElements.NOPE, true), "Slim", new SimpleMotionState(), new SimpleMotionState()), //
  SIMPLE_2D_MOVER_SLIM_DYNAMIC(new Simple2dMoverDynamic(WorldElements.NOPE, true), "SlimDynamic", new SimpleMotionState(), new SimpleMotionState()), //
  SIMPLE_2D_MOVER_FAT(new Simple2dMover(WorldElements.NOPE, false), "Fat", new SimpleMotionState(), new SimpleMotionState()), //
  SIMPLE_2D_MOVER_FAT_DYNAMIC(new Simple2dMoverDynamic(WorldElements.NOPE, false), "FatDynamic", new SimpleMotionState(), new SimpleMotionState()), //
  SIMPLE_2D_VECTOR_MOVER_XPXP(new SimpleVector2dMover(WorldElements.NOPE), "Vector XPXP", new SimpleVectorMotionState(MoveDirection.X_POSITIVE), new SimpleVectorMotionState(MoveDirection.X_POSITIVE)), //
  SIMPLE_2D_VECTOR_MOVER_XPXN(new SimpleVector2dMover(WorldElements.NOPE), "Vector XPXN", new SimpleVectorMotionState(MoveDirection.X_POSITIVE), new SimpleVectorMotionState(MoveDirection.X_NEGATIVE)), //
  SIMPLE_2D_VECTOR_MOVER_YPYP(new SimpleVector2dMover(WorldElements.NOPE), "Vector YPYP", new SimpleVectorMotionState(MoveDirection.Y_POSITIVE), new SimpleVectorMotionState(MoveDirection.Y_POSITIVE));

  @Getter
  private AMover mover;

  @Getter
  private String text;

  @Getter
  private IMotionState startState;
  @Getter
  private IMotionState endState;

  private MoverTyp(AMover mover, String text, IMotionState startState, IMotionState endState) {
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
