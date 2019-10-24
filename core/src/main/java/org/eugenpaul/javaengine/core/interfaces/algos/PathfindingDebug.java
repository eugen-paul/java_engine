package org.eugenpaul.javaengine.core.interfaces.algos;

import java.util.List;

import org.eugenpaul.javaengine.core.data.statistics.InfoPathfinding;
import org.eugenpaul.javaengine.core.world.entity.IMotionState;
import org.eugenpaul.javaengine.core.world.entity.AMover;
import org.eugenpaul.javaengine.core.world.entity.Step;
import org.eugenpaul.javaengine.core.world.map.Immutable3dPoint;

/**
 * Debug interface for path finding to do path finding step to step.
 * 
 * @author Eugen Paul
 *
 */
public interface PathfindingDebug {

  /**
   * Enable/Disable debugmod. In a debugmode you cann use PathfindingDebug-functions and get a info about a current step.
   * 
   * @param mode
   * @return true - debugMode changed<br>
   *         false - debugmode cann't be changed
   */
  public boolean setDebugMode(boolean mode);

  /**
   * Reset Pathfinding to start new search.<br>
   * The function is not thread safe!
   * 
   * @param mover
   * @param from
   * @param fromPoint
   * @param to
   * @param toPoint
   * @return
   */
  public boolean restartPathfinding(AMover mover, IMotionState from, Immutable3dPoint fromPoint, IMotionState to, Immutable3dPoint toPoint);

  /**
   * Do one Pathfinding step.<br>
   * The function is not thread safe!
   * 
   * @return false - more steps are possible.<br>
   *         true - end of pathfinding. Call {@link #getStepsResult()} to get Pathway
   */
  public boolean doOneStep();

  /**
   * Get pathway
   * 
   * @return not null - a Way<br>
   *         null - no way found
   */
  public List<Step> getStepsResult();

  /**
   * get current pathfinding info.
   * 
   * @return
   */
  public InfoPathfinding getCurrentPathfindingInfo();

}
