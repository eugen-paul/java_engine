package org.eugenpaul.javaengine.core.world.map;

import java.util.List;

import org.eugenpaul.javaengine.core.world.entity.collision.ICollisionCondition;

/**
 * 
 * @author Eugen Paul
 *
 */
public interface ITileBasedMap {
  public Immutable3dPoint getPathfinderSize();

//  public ICollisionCondition getCollisionCondition(Immutable3dPoint point);

  public List<ICollisionCondition> getCollisionCondition(int x, int y, int z);

  public boolean isCollisionCondition(int x, int y, int z, ICollisionCondition condition);
}
