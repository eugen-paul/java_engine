package org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.map;

import org.eugenpaul.javaengine.core.world.entity.AMover;
import org.eugenpaul.javaengine.core.world.entity.IMotionState;

/**
 * Map Mover
 * 
 * @author Eugen Paul
 *
 */
public interface IMapMover {

  public AMover getMover();

  public String getText();

  public IMotionState getStartState();

  public IMotionState getEndState();
}
