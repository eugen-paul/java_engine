package org.eugenpaul.javaengine.core.world.entity;

import org.eugenpaul.javaengine.core.world.entity.collision.ICollisionCondition;
import org.eugenpaul.javaengine.core.world.map.Immutable3dTilePoint;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Immutable Point to check collision by the path finding.
 * 
 * @author Eugen Paul
 *
 */
@AllArgsConstructor
@Getter
public class CollisionPoint {
  private Immutable3dTilePoint point;
  private ICollisionCondition collisionCondition;

  @Override
  public String toString() {
    return "Point: " + point.toString() + "\n" + "collisionCondition: " + collisionCondition.toString();
  }
}
