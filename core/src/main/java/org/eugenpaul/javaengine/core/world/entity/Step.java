package org.eugenpaul.javaengine.core.world.entity;

import java.util.Collections;
import java.util.List;

import org.eugenpaul.javaengine.core.world.entity.collision.ICollisionCondition;
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

  @Getter
  /** The Point can be checked to compute a new (real) step cost if not all points on the map a equal */
  private List<Immutable3dPoint> costWay = null;

  /** Interface to compute dynamic step cost */
  private StepCostModifier costModifier = null;

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

  public Step(List<Immutable3dPoint> movingWay, IMotionState fromState, IMotionState lastState, List<CollisionPoint> collisionBox, long cost, List<Immutable3dPoint> costWay,
      StepCostModifier costModifier) {
    this(movingWay, fromState, lastState, collisionBox, cost);
    this.costWay = costWay;
    this.costModifier = costModifier;
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

  public long getDynamicMultiCost(List<List<ICollisionCondition>> collisions) {
    if (null == costModifier) {
      return cost;
    } else {
      return costModifier.getDynamicMultiCost(cost, collisions);
    }
  }

  public long getDynamicSingleCost(List<ICollisionCondition> collisions) {
    if (null == costModifier) {
      return cost;
    } else {
      return costModifier.getDynamicSingleCost(cost, collisions);
    }
  }
}
