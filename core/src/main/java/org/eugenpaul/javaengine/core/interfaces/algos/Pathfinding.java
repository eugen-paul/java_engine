package org.eugenpaul.javaengine.core.interfaces.algos;

import java.util.List;

import org.eugenpaul.javaengine.core.world.entity.IMotionState;
import org.eugenpaul.javaengine.core.world.entity.AMover;
import org.eugenpaul.javaengine.core.world.entity.Step;
import org.eugenpaul.javaengine.core.world.map.ITileBasedMap;
import org.eugenpaul.javaengine.core.world.map.Immutable3dPoint;
import org.eugenpaul.javaengine.core.world.moving.IMoving;

/**
 * path finding interface.<br>
 * To initialize the path finding the function {@link #init} must be called once. Then you can call the function {@link #getPath} to find the path. The function {@link #getPath}
 * can be called more then one time.
 * 
 * @author Eugen Paul
 *
 */
public interface Pathfinding<T extends Step> {
  /**
   * Initialize algorithm to search a path in a TileBasedMap
   * 
   * @param map
   * @param movingTester
   * @param doDynamicCost
   * @return
   */
  public boolean init(ITileBasedMap map, IMoving<T> movingTester, boolean doDynamicCost);

  /**
   * Find and get a Path
   * 
   * @param mover
   * @param from
   * @param fromPoint
   * @param to
   * @param toPoint
   * @return
   */
  public List<Step> getPath(AMover mover, IMotionState from, Immutable3dPoint fromPoint, IMotionState to, Immutable3dPoint toPoint);

  /**
   * Get DebugInfo, if possible
   * 
   * @return debutinfo<br>
   *         null -
   */
  public PathfindingDebug getDebugInfo();

}
