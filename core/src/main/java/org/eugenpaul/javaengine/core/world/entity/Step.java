package org.eugenpaul.javaengine.core.world.entity;

import java.util.Collections;
import java.util.List;

import org.eugenpaul.javaengine.core.world.map.Immutable3dPoint;

import lombok.Getter;

/**
 * Full Step (Immutable).
 * 
 * @author Eugen Paul
 *
 */
public class Step {
  @Getter
  private List<Immutable3dPoint> movingWay = null;

  @Getter
  private IMotionState fromState = null;

  @Getter
  private IMotionState lastState = null;

  @Getter
  private List<CollisionPoint> collisionBox = null;

  @Getter
  private long cost = 0L;

  @Getter
  private Immutable3dPoint lastPosition = null;

  /**
   * C'tor
   * 
   * @param way
   * @param collisionBox
   * @param cost
   */
  public Step(List<Immutable3dPoint> movingWay, IMotionState fromState, IMotionState lastState, List<CollisionPoint> collisionBox, long cost) {
    this.movingWay = Collections.unmodifiableList(List.copyOf(movingWay));
    this.collisionBox = Collections.unmodifiableList(List.copyOf(collisionBox));
    this.fromState = fromState;
    this.lastState = lastState;
    this.cost = cost;

    int size = movingWay.size();
    lastPosition = movingWay.get(size - 1);
  }

  @Override
  public String toString() {
    StringBuilder steps = new StringBuilder();
    for (Immutable3dPoint step : movingWay) {
      steps.append(step.toString());
      steps.append("; \n");
    }
    return steps.toString();
  }
}
