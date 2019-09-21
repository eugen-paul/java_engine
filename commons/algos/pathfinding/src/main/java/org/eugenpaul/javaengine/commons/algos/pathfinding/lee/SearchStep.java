package org.eugenpaul.javaengine.commons.algos.pathfinding.lee;

import org.eugenpaul.javaengine.core.world.entity.AMotionState;
import org.eugenpaul.javaengine.core.world.entity.Step;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * State of the Node
 * 
 * @author Eugen Paul
 *
 */
@AllArgsConstructor
@Getter
public class SearchStep implements Comparable<SearchStep> {
  /** current cost */
  private double cost = 0;
  /** current Motion step */
  private AMotionState state = null;
  /** Parent step */
  private Step stepFrom = null;
  /** x-Position of the current Position on the Map */
  int x;
  /** y-Position of the current Position on the Map */
  int y;
  /** z-Position of the current Position on the Map */
  int z;

  @Override
  public int compareTo(SearchStep o) {
    if (cost > o.cost) {
      return 1;
    } else if (cost < o.cost) {
      return -1;
    }
    return 0;
  }

}
