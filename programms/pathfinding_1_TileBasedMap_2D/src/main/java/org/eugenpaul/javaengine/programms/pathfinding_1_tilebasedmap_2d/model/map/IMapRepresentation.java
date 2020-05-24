package org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.map;

import org.eugenpaul.javaengine.core.world.map.ITileBasedMap;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.WorldElements;

/**
 * Map Representation for this example. New functions for ITileBasedMap to control map value.
 * 
 * @author Eugen Paul
 */
public interface IMapRepresentation extends ITileBasedMap {

  /**
   * get one element from the map
   * 
   * @param x - X Postion
   * @param y - Y Postion
   * @return element on X Y
   */
  public WorldElements getPosition(int x, int y);

  /**
   * set element to the map on Position X Y
   * 
   * @param x
   * @param y
   * @param element
   * @return
   */
  public boolean setPosition(int x, int y, WorldElements element);
  
  public int getSiezX();
  public int getSiezY();
  public int getValue(int x, int y);
}
