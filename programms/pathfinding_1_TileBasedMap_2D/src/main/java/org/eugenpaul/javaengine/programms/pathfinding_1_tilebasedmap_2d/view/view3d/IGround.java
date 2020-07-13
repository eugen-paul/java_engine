package org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.view3d;

import org.eugenpaul.javaengine.core.world.map.Immutable3dTilePoint;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.GridElement;

/**
 * Interface to print map.
 * 
 * @author Eugen Paul
 *
 */
public interface IGround {

  /**
   * Set grid to paint.
   * 
   * @param grid
   */
  public abstract void setGrid(GridElement[][] grid);

  /**
   * Set paint parameter.
   * 
   * @param key
   * @param value
   */
  public abstract void setParam(String key, String value);

  /**
   * Set paint parameter.
   * 
   * @param key
   * @param value
   */
  public abstract void setParam(String key, boolean value);

  /**
   * Set paint parameter.
   * 
   * @param key
   * @param value
   */
  public abstract void setParam(String key, int value);

  /**
   * Convert mouse coordinates to world coordinates
   * 
   * @param panelX
   * @param panelY
   * @return
   */
  public abstract Immutable3dTilePoint mouseToWorld(int panelX, int panelY);
}
