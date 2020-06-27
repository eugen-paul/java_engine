package org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.map.clearance;

import org.eugenpaul.javaengine.core.world.entity.IMotionState;
import org.eugenpaul.javaengine.core.world.entity.AMover;
import org.eugenpaul.javaengine.core.world.entity.collision.Clearance2dCollisionCondition;
import org.eugenpaul.javaengine.core.world.entity.sample.Simple2dMover;
import org.eugenpaul.javaengine.core.world.entity.sample.SimpleMotionState;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.map.IMapMover;

import lombok.Getter;

/**
 * Mover types.
 * 
 * @author Eugen Paul
 *
 */
public enum ClearanceBasedMoverTyp implements IMapMover{
  SIMPLE_2D_MOVER_SIZE_1(new Simple2dMover(new Clearance2dCollisionCondition(1), true), "Size 1", new SimpleMotionState(), new SimpleMotionState()), //
  SIMPLE_2D_MOVER_SIZE_2(new Simple2dMover(new Clearance2dCollisionCondition(2), true), "Size 2", new SimpleMotionState(), new SimpleMotionState()), //
  SIMPLE_2D_MOVER_SIZE_3(new Simple2dMover(new Clearance2dCollisionCondition(3), true), "Size 3", new SimpleMotionState(), new SimpleMotionState()), //
  SIMPLE_2D_MOVER_SIZE_4(new Simple2dMover(new Clearance2dCollisionCondition(4), true), "Size 4", new SimpleMotionState(), new SimpleMotionState()), //
  SIMPLE_2D_MOVER_SIZE_5(new Simple2dMover(new Clearance2dCollisionCondition(5), true), "Size 5", new SimpleMotionState(), new SimpleMotionState()); //

  @Getter
  private AMover mover;

  @Getter
  private String text;

  @Getter
  private IMotionState startState;
  @Getter
  private IMotionState endState;

  private ClearanceBasedMoverTyp(AMover mover, String text, IMotionState startState, IMotionState endState) {
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
