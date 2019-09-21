package org.eugenpaul.javaengine.core.world.entity.sample;

import java.util.ArrayList;
import java.util.List;

import org.eugenpaul.javaengine.core.world.entity.AMotionState;
import org.eugenpaul.javaengine.core.world.entity.AMover;
import org.eugenpaul.javaengine.core.world.entity.CollisionPoint;
import org.eugenpaul.javaengine.core.world.entity.Step;
import org.eugenpaul.javaengine.core.world.entity.collision.ICollisionCondition;
import org.eugenpaul.javaengine.core.world.map.Immutable3dPoint;

/**
 * Mover on 2d Tile Map.<br>
 * He can move on the diagonal<br>
 * 
 * @author Eugen Paul
 *
 */
public class Simple2dMover extends AMover {

  /** all possible steps */
  private List<Step> possibleSteps = new ArrayList<Step>();

  private ICollisionCondition stdCondition = null;

  private boolean slim = false;

  /**
   * Move on diagonal (slim).
   * 
   * @param stdCondition
   */
  public Simple2dMover(ICollisionCondition stdCondition) {
    this(stdCondition, true);
  }

  /**
   * 
   * @param stdCondition
   * @param slim         - false - for diagonal move need two free neighbor
   */
  public Simple2dMover(ICollisionCondition stdCondition, boolean slim) {
    this.slim = slim;
    this.stdCondition = stdCondition;
    createSteps();
  }

  private void createSteps() {
    possibleSteps.add(createStep(1, 0));
    possibleSteps.add(createStep(0, 1));

    possibleSteps.add(createStep(-1, 0));
    possibleSteps.add(createStep(0, -1));

    possibleSteps.add(createStep(1, 1));
    possibleSteps.add(createStep(-1, -1));

    possibleSteps.add(createStep(1, -1));
    possibleSteps.add(createStep(-1, 1));
  }

  private Step createStep(int x, int y) {
    List<Immutable3dPoint> movingWay = new ArrayList<>();
    AMotionState lastPoint = new SimpleMotionState();
    double cost = 1.0;

    if (x != 0 && y != 0) {
      cost = 1.3;
    }

    movingWay.add(new Immutable3dPoint(0, 0, 0));
    movingWay.add(new Immutable3dPoint(x, y, 0));

    ArrayList<CollisionPoint> collisionList = new ArrayList<>();

    CollisionPoint cPointStart = new CollisionPoint(new Immutable3dPoint(0, 0, 0), stdCondition);
    collisionList.add(cPointStart);
    if (!slim && x != 0 && y != 0) {
      CollisionPoint middle1 = new CollisionPoint(new Immutable3dPoint(x, 0, 0), stdCondition);
      collisionList.add(middle1);
      CollisionPoint middle2 = new CollisionPoint(new Immutable3dPoint(0, y, 0), stdCondition);
      collisionList.add(middle2);
    }
    CollisionPoint cPointEnd = new CollisionPoint(new Immutable3dPoint(x, y, 0), stdCondition);
    collisionList.add(cPointEnd);

    Step responseWay = new Step(movingWay, lastPoint, lastPoint, collisionList, cost);
    return responseWay;
  }

  @Override
  public List<Step> getNextSteps(AMotionState state) {
    return possibleSteps;
  }

}
