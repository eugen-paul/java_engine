package org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model;

import java.util.ArrayList;
import java.util.List;

import org.eugenpaul.javaengine.core.world.entity.IMotionState;
import org.eugenpaul.javaengine.core.world.entity.AMover;
import org.eugenpaul.javaengine.core.world.entity.CollisionPoint;
import org.eugenpaul.javaengine.core.world.entity.Step;
import org.eugenpaul.javaengine.core.world.entity.StepCostModifier;
import org.eugenpaul.javaengine.core.world.entity.collision.ICollisionCondition;
import org.eugenpaul.javaengine.core.world.entity.sample.SimpleMotionState;
import org.eugenpaul.javaengine.core.world.map.Immutable3dTilePoint;

/**
 * Mover on 2d Tile Map.<br>
 * He can move on the diagonal.<br>
 * The mover move slower on dirt/mud.
 * 
 * @author Eugen Paul
 *
 */
public class Simple2dMoverDynamic implements AMover {

  /** all possible steps */
  private List<Step> possibleSteps = new ArrayList<>();

  private ICollisionCondition stdCondition = null;

  private boolean slim = false;

  private StepCostModifier costModifier = null;

  /**
   * Move on diagonal (slim).
   * 
   * @param stdCondition
   */
  public Simple2dMoverDynamic(ICollisionCondition stdCondition) {
    this(stdCondition, true);
  }

  /**
   * 
   * @param stdCondition
   * @param slim         - false - for diagonal move need two free neighbor
   */
  public Simple2dMoverDynamic(ICollisionCondition stdCondition, boolean slim) {
    this.slim = slim;
    this.stdCondition = stdCondition;

    costModifier = new DynamicStepModifier();

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
    List<Immutable3dTilePoint> movingWay = new ArrayList<>();
    IMotionState lastPoint = new SimpleMotionState();
    long cost = 10L;

    if (x != 0 && y != 0) {
      cost = 13L;
    }

    movingWay.add(new Immutable3dTilePoint(0, 0, 0));
    movingWay.add(new Immutable3dTilePoint(x, y, 0));

    ArrayList<CollisionPoint> collisionList = new ArrayList<>();

    CollisionPoint cPointStart = new CollisionPoint(new Immutable3dTilePoint(0, 0, 0), stdCondition);
    collisionList.add(cPointStart);
    if (!slim && x != 0 && y != 0) {
      CollisionPoint middle1 = new CollisionPoint(new Immutable3dTilePoint(x, 0, 0), stdCondition);
      collisionList.add(middle1);
      CollisionPoint middle2 = new CollisionPoint(new Immutable3dTilePoint(0, y, 0), stdCondition);
      collisionList.add(middle2);
    }
    CollisionPoint cPointEnd = new CollisionPoint(new Immutable3dTilePoint(x, y, 0), stdCondition);
    collisionList.add(cPointEnd);

    List<Immutable3dTilePoint> costWay = new ArrayList<>();
    costWay.add(new Immutable3dTilePoint(x, y, 0));

    return new Step(movingWay, lastPoint, lastPoint, collisionList, cost, costWay, costModifier);
  }

  @Override
  public List<Step> getNextSteps(IMotionState state) {
    return possibleSteps;
  }

  @Override
  public int getSimpleStepHeuristicsCost() {
    return 10;
  }

}
