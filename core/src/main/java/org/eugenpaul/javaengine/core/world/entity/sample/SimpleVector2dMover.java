package org.eugenpaul.javaengine.core.world.entity.sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eugenpaul.javaengine.core.world.entity.IMotionState;
import org.eugenpaul.javaengine.core.world.entity.AMover;
import org.eugenpaul.javaengine.core.world.entity.CollisionPoint;
import org.eugenpaul.javaengine.core.world.entity.MoveDirection;
import org.eugenpaul.javaengine.core.world.entity.Step;
import org.eugenpaul.javaengine.core.world.entity.collision.ICollisionCondition;
import org.eugenpaul.javaengine.core.world.map.Immutable3dPoint;

/**
 * Sample implementation of a vector mover. Each state of move has a direction (like a car, train, ...). He can not turn on the spot. To turn, he has to take a step forward and
 * then side.
 * 
 * @author Eugen Paul
 *
 */
public class SimpleVector2dMover implements AMover {

  /** all possible steps */
  private List<Step> possibleStepsXPositiv = new ArrayList<>();
  private List<Step> possibleStepsXNegativ = new ArrayList<>();

  private List<Step> possibleStepsYPositiv = new ArrayList<>();
  private List<Step> possibleStepsYNegativ = new ArrayList<>();

  private ICollisionCondition stdCondition = null;

  /**
   * C'tor
   * 
   * @param stdCondition - Condition for every step that needs to be fulfilled.
   */
  public SimpleVector2dMover(ICollisionCondition stdCondition) {
    this.stdCondition = stdCondition;
    initSteps();
  }

  private void initSteps() {
    possibleStepsXPositiv.add(createForwardStep(MoveDirection.X_POSITIVE));
    possibleStepsXPositiv.add(createLeftStep(MoveDirection.X_POSITIVE));
    possibleStepsXPositiv.add(createRightStep(MoveDirection.X_POSITIVE));

    possibleStepsXNegativ.add(createForwardStep(MoveDirection.X_NEGATIVE));
    possibleStepsXNegativ.add(createLeftStep(MoveDirection.X_NEGATIVE));
    possibleStepsXNegativ.add(createRightStep(MoveDirection.X_NEGATIVE));

    possibleStepsYPositiv.add(createForwardStep(MoveDirection.Y_POSITIVE));
    possibleStepsYPositiv.add(createLeftStep(MoveDirection.Y_POSITIVE));
    possibleStepsYPositiv.add(createRightStep(MoveDirection.Y_POSITIVE));

    possibleStepsYNegativ.add(createForwardStep(MoveDirection.Y_NEGATIVE));
    possibleStepsYNegativ.add(createLeftStep(MoveDirection.Y_NEGATIVE));
    possibleStepsYNegativ.add(createRightStep(MoveDirection.Y_NEGATIVE));
  }

  private Step createForwardStep(MoveDirection startDirection) {
    IMotionState firstPoint;
    IMotionState lastPoint;

    int x = 0;
    int y = 0;

    switch (startDirection) {
    case X_POSITIVE:
      x = 1;
      firstPoint = new SimpleVectorMotionState(MoveDirection.X_POSITIVE);
      lastPoint = new SimpleVectorMotionState(MoveDirection.X_POSITIVE);
      break;
    case X_NEGATIVE:
      x = -1;
      firstPoint = new SimpleVectorMotionState(MoveDirection.X_NEGATIVE);
      lastPoint = new SimpleVectorMotionState(MoveDirection.X_NEGATIVE);
      break;
    case Y_POSITIVE:
      y = 1;
      firstPoint = new SimpleVectorMotionState(MoveDirection.Y_POSITIVE);
      lastPoint = new SimpleVectorMotionState(MoveDirection.Y_POSITIVE);
      break;
    case Y_NEGATIVE:
      y = -1;
      firstPoint = new SimpleVectorMotionState(MoveDirection.Y_NEGATIVE);
      lastPoint = new SimpleVectorMotionState(MoveDirection.Y_NEGATIVE);
      break;
    default:
      throw new IllegalArgumentException();
    }

    List<Immutable3dPoint> movingWay = new ArrayList<>();
    movingWay.add(new Immutable3dPoint(0, 0, 0));
    movingWay.add(new Immutable3dPoint(x, y, 0));

    ArrayList<CollisionPoint> collisionList = new ArrayList<>();

    CollisionPoint cPointStart = new CollisionPoint(new Immutable3dPoint(0, 0, 0), stdCondition);
    collisionList.add(cPointStart);
    CollisionPoint cPointEnd = new CollisionPoint(new Immutable3dPoint(x, y, 0), stdCondition);
    collisionList.add(cPointEnd);

    long cost = 10L;
    return new Step(movingWay, firstPoint, lastPoint, collisionList, cost);
  }

  private Step createLeftStep(MoveDirection startDirection) {
    int x = 0;
    int y = 0;

    IMotionState firstPoint;
    IMotionState lastPoint;

    Immutable3dPoint stepMiddle;
    CollisionPoint cPointMiddle;

    switch (startDirection) {
    case X_POSITIVE:
      x = 1;
      y = 1;
      stepMiddle = new Immutable3dPoint(1, 0, 0);
      cPointMiddle = new CollisionPoint(new Immutable3dPoint(1, 0, 0), stdCondition);
      firstPoint = new SimpleVectorMotionState(MoveDirection.X_POSITIVE);
      lastPoint = new SimpleVectorMotionState(MoveDirection.Y_POSITIVE);
      break;
    case X_NEGATIVE:
      x = -1;
      y = -1;
      stepMiddle = new Immutable3dPoint(-1, 0, 0);
      cPointMiddle = new CollisionPoint(new Immutable3dPoint(-1, 0, 0), stdCondition);
      firstPoint = new SimpleVectorMotionState(MoveDirection.X_NEGATIVE);
      lastPoint = new SimpleVectorMotionState(MoveDirection.Y_NEGATIVE);
      break;
    case Y_POSITIVE:
      x = -1;
      y = 1;
      stepMiddle = new Immutable3dPoint(0, 1, 0);
      cPointMiddle = new CollisionPoint(new Immutable3dPoint(0, 1, 0), stdCondition);
      firstPoint = new SimpleVectorMotionState(MoveDirection.Y_POSITIVE);
      lastPoint = new SimpleVectorMotionState(MoveDirection.X_NEGATIVE);
      break;
    case Y_NEGATIVE:
      x = 1;
      y = -1;
      stepMiddle = new Immutable3dPoint(0, -1, 0);
      cPointMiddle = new CollisionPoint(new Immutable3dPoint(0, -1, 0), stdCondition);
      firstPoint = new SimpleVectorMotionState(MoveDirection.Y_NEGATIVE);
      lastPoint = new SimpleVectorMotionState(MoveDirection.X_POSITIVE);
      break;
    default:
      throw new IllegalArgumentException();
    }

    List<Immutable3dPoint> movingWay = new ArrayList<>();
    movingWay.add(new Immutable3dPoint(0, 0, 0));
    movingWay.add(stepMiddle);
    movingWay.add(new Immutable3dPoint(x, y, 0));

    ArrayList<CollisionPoint> collisionList = new ArrayList<>();

    CollisionPoint cPointStart = new CollisionPoint(new Immutable3dPoint(0, 0, 0), stdCondition);
    collisionList.add(cPointStart);

    collisionList.add(cPointMiddle);

    CollisionPoint cPointEnd = new CollisionPoint(new Immutable3dPoint(x, y, 0), stdCondition);
    collisionList.add(cPointEnd);

    long cost = 15L;
    return new Step(movingWay, firstPoint, lastPoint, collisionList, cost);
  }

  private Step createRightStep(MoveDirection startDirection) {
    int x = 0;
    int y = 0;

    IMotionState firstPoint;
    IMotionState lastPoint;

    Immutable3dPoint stepMiddle;
    CollisionPoint cPointMiddle;

    switch (startDirection) {
    case X_POSITIVE:
      x = 1;
      y = -1;
      stepMiddle = new Immutable3dPoint(1, 0, 0);
      cPointMiddle = new CollisionPoint(new Immutable3dPoint(1, 0, 0), stdCondition);
      firstPoint = new SimpleVectorMotionState(MoveDirection.X_POSITIVE);
      lastPoint = new SimpleVectorMotionState(MoveDirection.Y_NEGATIVE);
      break;
    case X_NEGATIVE:
      x = -1;
      y = 1;
      stepMiddle = new Immutable3dPoint(-1, 0, 0);
      cPointMiddle = new CollisionPoint(new Immutable3dPoint(-1, 0, 0), stdCondition);
      firstPoint = new SimpleVectorMotionState(MoveDirection.X_NEGATIVE);
      lastPoint = new SimpleVectorMotionState(MoveDirection.Y_POSITIVE);
      break;
    case Y_POSITIVE:
      x = 1;
      y = 1;
      stepMiddle = new Immutable3dPoint(0, 1, 0);
      cPointMiddle = new CollisionPoint(new Immutable3dPoint(0, 1, 0), stdCondition);
      firstPoint = new SimpleVectorMotionState(MoveDirection.Y_POSITIVE);
      lastPoint = new SimpleVectorMotionState(MoveDirection.X_POSITIVE);
      break;
    case Y_NEGATIVE:
      x = -1;
      y = -1;
      stepMiddle = new Immutable3dPoint(0, -1, 0);
      cPointMiddle = new CollisionPoint(new Immutable3dPoint(0, -1, 0), stdCondition);
      firstPoint = new SimpleVectorMotionState(MoveDirection.Y_NEGATIVE);
      lastPoint = new SimpleVectorMotionState(MoveDirection.X_NEGATIVE);
      break;
    default:
      throw new IllegalArgumentException();
    }

    List<Immutable3dPoint> movingWay = new ArrayList<>();
    movingWay.add(new Immutable3dPoint(0, 0, 0));
    movingWay.add(stepMiddle);
    movingWay.add(new Immutable3dPoint(x, y, 0));

    ArrayList<CollisionPoint> collisionList = new ArrayList<>();

    CollisionPoint cPointStart = new CollisionPoint(new Immutable3dPoint(0, 0, 0), stdCondition);
    collisionList.add(cPointStart);

    collisionList.add(cPointMiddle);

    CollisionPoint cPointEnd = new CollisionPoint(new Immutable3dPoint(x, y, 0), stdCondition);
    collisionList.add(cPointEnd);

    long cost = 15L;
    return new Step(movingWay, firstPoint, lastPoint, collisionList, cost);
  }

  @Override
  public List<Step> getNextSteps(IMotionState state) {
    if (!(state instanceof SimpleVectorMotionState)) {
      throw new IllegalArgumentException();
    }

    SimpleVectorMotionState innerState = (SimpleVectorMotionState) state;
    switch (innerState.getMoveDirection()) {
    case X_POSITIVE:
      return possibleStepsXPositiv;
    case X_NEGATIVE:
      return possibleStepsXNegativ;
    case Y_POSITIVE:
      return possibleStepsYPositiv;
    case Y_NEGATIVE:
      return possibleStepsYNegativ;
    case Z_POSITIVE:
    case Z_NEGATIVE:
      return Collections.emptyList();
    }
    return Collections.emptyList();
  }

  @Override
  public int getSimpleStepHeuristicsCost() {
    return 10;
  }

}
