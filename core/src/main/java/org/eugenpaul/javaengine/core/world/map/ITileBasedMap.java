package org.eugenpaul.javaengine.core.world.map;

import java.util.List;

import org.eugenpaul.javaengine.core.world.entity.collision.ICollisionCondition;

/**
 * TileBasedMap.
 * 
 * @author Eugen Paul
 *
 */
public interface ITileBasedMap {
  /**
   * Get map size for path finding.
   * 
   * @return
   */
  public Immutable3dTilePoint getPathfinderSize();

  /**
   * Get list of collisions in map.
   * 
   * @param x - x position
   * @param y - y position
   * @param z - z position
   * @return
   */
  public List<ICollisionCondition> getCollisionCondition(int x, int y, int z);

  /**
   * Get list of collisions in map.
   * 
   * @param position
   * @return
   */
  public List<ICollisionCondition> getCollisionCondition(Immutable3dTilePoint position);

  /**
   * Check, if the collision condition is at map point. <br>
   * Example: is on the location a wall? is on the location nothing? is on the location water?
   * 
   * @param x         - x position
   * @param y         - y position
   * @param z         - z position
   * @param condition - condition to check
   * @return true - condition in on the map point<br>
   *         false - else
   */
  public boolean isCollisionCondition(int x, int y, int z, ICollisionCondition condition);
}
