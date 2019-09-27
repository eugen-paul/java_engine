package org.eugenpaul.javaengine.core.interfaces.algos;

import java.util.List;

import org.eugenpaul.javaengine.core.world.entity.AMotionState;
import org.eugenpaul.javaengine.core.world.entity.AMover;
import org.eugenpaul.javaengine.core.world.entity.Step;
import org.eugenpaul.javaengine.core.world.map.ITileBasedMap;
import org.eugenpaul.javaengine.core.world.map.Immutable3dPoint;
import org.eugenpaul.javaengine.core.world.moving.AMoving;

/**
 * 
 * @author Eugen Paul
 *
 */
public interface Pathfinding {
  /**
   * Init Algo to search a path in a TileBasedMap
   * 
   * @param map
   * @param movingTester
   * @return
   */
  public boolean init(ITileBasedMap map, AMoving movingTester);

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
  public List<Step> getPath(AMover mover, AMotionState from, Immutable3dPoint fromPoint, AMotionState to, Immutable3dPoint toPoint);

  /**
   * Get DebugInfo, if possible
   * 
   * @return debutinfo<br>
   *         null - 
   */
  public PathfindingDebug getDebugInfo();

}
