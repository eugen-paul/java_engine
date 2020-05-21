package org.eugenpaul.javaengine.core.world.moving;

import java.util.List;

import org.eugenpaul.javaengine.core.world.entity.Step;
import org.eugenpaul.javaengine.core.world.map.ITileBasedMap;
import org.eugenpaul.javaengine.core.world.map.Immutable3dPoint;

/**
 * Interface to test if the step is on the map possible.
 * 
 * @author Eugen Paul
 *
 */
public interface IMoving<T extends Step> {

  /**
   * Check possibility of steps.
   * 
   * @param state
   * @param position
   * @param map
   * @return allowed steps
   */
  public abstract List<T> checkPossibleSteps(List<T> steps, Immutable3dPoint position, ITileBasedMap map);

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
  public abstract List<T> checkPossibleSteps(List<T> steps, int x, int y, int z, ITileBasedMap map);

}
