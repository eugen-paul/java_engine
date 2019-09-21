package org.eugenpaul.javaengine.core.world.moving;

import java.util.List;

import org.eugenpaul.javaengine.core.world.entity.Step;
import org.eugenpaul.javaengine.core.world.map.ITileBasedMap;
import org.eugenpaul.javaengine.core.world.map.Immutable3dPoint;

/**
 * 
 * @author Eugen Paul
 *
 */
public abstract class AMoving {

  /**
   * Check possibility of steps.
   * 
   * @param state
   * @param position
   * @param map
   * @return allowed steps
   */
  public abstract List<Step> checkPossibleSteps(List<Step> steps, Immutable3dPoint position, ITileBasedMap map);

  /**
   * Check possibility of steps.
   * 
   * @param state
   * @param x
   * @param y
   * @param z
   * @param map
   * @return allowed steps
   */
  public abstract List<Step> checkPossibleSteps(List<Step> steps, int x, int y, int z, ITileBasedMap map);
}
