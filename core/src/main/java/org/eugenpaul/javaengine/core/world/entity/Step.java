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
  private AMotionState fromState = null;

  @Getter
  private AMotionState lastState = null;

  @Getter
  private List<CollisionPoint> collisionBox = null;

  @Getter
  private double cost = 0.0;

  @Getter
  private Immutable3dPoint lastPosition = null;

  /**
   * C'tor
   * 
   * @param way
   * @param collisionBox
   * @param cost
   */
  public Step(List<Immutable3dPoint> movingWay, AMotionState fromState, AMotionState lastState, List<CollisionPoint> collisionBox, double cost) {
    this.movingWay = Collections.unmodifiableList(List.copyOf(movingWay));
    this.collisionBox = Collections.unmodifiableList(List.copyOf(collisionBox));
    this.fromState = fromState;
    this.lastState = lastState;
    this.cost = cost;

    int x = 0;
    int y = 0;
    int z = 0;

    for (Immutable3dPoint wayPoint : movingWay) {
      x += wayPoint.getX();
      y += wayPoint.getY();
      z += wayPoint.getZ();
    }

    lastPosition = new Immutable3dPoint(x, y, z);
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
