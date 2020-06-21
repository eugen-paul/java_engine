package org.eugenpaul.javaengine.core.world.moving.sample;

import java.util.LinkedList;
import java.util.List;

import org.eugenpaul.javaengine.core.world.entity.CollisionPoint;
import org.eugenpaul.javaengine.core.world.entity.Step;
import org.eugenpaul.javaengine.core.world.map.ITileBasedMap;
import org.eugenpaul.javaengine.core.world.map.Immutable3dTilePoint;
import org.eugenpaul.javaengine.core.world.moving.IMoving;

/**
 * 
 * @author Eugen Paul
 *
 */
public class SimpleMoving implements IMoving<Step> {

  @Override
  public List<Step> checkPossibleSteps(List<Step> steps, Immutable3dTilePoint position, ITileBasedMap map) {
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

    int checkX;
    int checkY;
    int checkZ;

    for (CollisionPoint pointToTest : collisionBox) {
      Immutable3dTilePoint coord = pointToTest.getPoint();
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
