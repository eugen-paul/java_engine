package org.eugenpaul.javaengine.core.world.moving.sample;

import java.util.LinkedList;
import java.util.List;

import org.eugenpaul.javaengine.core.world.entity.CollisionPoint;
import org.eugenpaul.javaengine.core.world.entity.Step;
import org.eugenpaul.javaengine.core.world.map.ITileBasedMap;
import org.eugenpaul.javaengine.core.world.map.Immutable3dPoint;
import org.eugenpaul.javaengine.core.world.moving.AMoving;

/**
 * 
 * @author Eugen Paul
 *
 */
public class SimpleMoving extends AMoving {

  @Override
  public List<Step> checkPossibleSteps(List<Step> steps, Immutable3dPoint position, ITileBasedMap map) {
    return checkPossibleSteps(steps, position.getX(), position.getY(), position.getZ(), map);
  }

  @Override
  public List<Step> checkPossibleSteps(List<Step> steps, int x, int y, int z, ITileBasedMap map) {
    List<Step> responseSteps = new LinkedList<>();

    for (Step oneStep : steps) {
      if (checkOneStep(oneStep, x, y, z, map)) {
        responseSteps.add(oneStep);
      }
    }

    return responseSteps;
  }

  private boolean checkOneStep(Step step, int x, int y, int z, ITileBasedMap map) {

    List<CollisionPoint> collisionBox = step.getCollisionBox();

    int checkX = x;
    int checkY = y;
    int checkZ = z;

    for (CollisionPoint pointToTest : collisionBox) {
      Immutable3dPoint coord = pointToTest.getPoint();
      checkX = x + coord.getX();
      checkY = y + coord.getY();
      checkZ = z + coord.getZ();
      if (!map.isCollisionCondition(checkX, checkY, checkZ, pointToTest.getCollisionCondition())) {
        return false;
      }
    }

    return true;
  }

}
