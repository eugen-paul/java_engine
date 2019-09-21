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
  public boolean init(ITileBasedMap map, AMoving movingTester);

  public List<Step> getPath(AMover mover, AMotionState from, Immutable3dPoint fromPoint, AMotionState to, Immutable3dPoint toPoint);
}
