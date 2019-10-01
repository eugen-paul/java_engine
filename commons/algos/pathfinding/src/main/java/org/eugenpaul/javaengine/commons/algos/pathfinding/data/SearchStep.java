package org.eugenpaul.javaengine.commons.algos.pathfinding.data;

import org.eugenpaul.javaengine.core.world.entity.AMotionState;
import org.eugenpaul.javaengine.core.world.entity.Step;

import lombok.Getter;
import lombok.Setter;

/**
 * State of the Node
 * 
 * @author Eugen Paul
 *
 */
@Getter
public class SearchStep implements Comparable<SearchStep> {
  /** current cost of all steps */
  private long stepscost = 0;

  @Setter
  /** current cost of pathfinding (steps + heuristic) */
  private long heuristicscost = 0;
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
  /** false - the step must be checked. False - the step was removed, don't check it. */
  private boolean removed = false;

  /**
   * C'tor. heuristicscost will be set to stepscost
   * 
   * @param stepscost - total cost of the way
   * @param state     - last state of the way
   * @param stepFrom  - source step
   * @param x         - x cord
   * @param y         - y cord
   * @param z         - z cord
   */
  public SearchStep(long stepscost, AMotionState state, Step stepFrom, int x, int y, int z) {
    this(stepscost, stepscost, state, stepFrom, x, y, z);
  }

  /**
   * C'tor.
   * 
   * @param stepscost      - total cost of the way
   * @param heuristicscost - total cost of the way + heuristic cost
   * @param state          - last state of the way
   * @param stepFrom       - source step
   * @param x              - x cord
   * @param y              - y cord
   * @param z              - z cord
   */
  public SearchStep(long stepscost, long heuristicscost, AMotionState state, Step stepFrom, int x, int y, int z) {
    this.stepscost = stepscost;
    this.heuristicscost = heuristicscost;
    this.state = state;
    this.stepFrom = stepFrom;
    this.x = x;
    this.y = y;
    this.z = z;
  }

  /**
   * Set the step as removed. Cann't be undone.
   */
  public void setRemoved() {
    removed = true;
  }

  @Override
  public int compareTo(SearchStep o) {
    if (heuristicscost > o.heuristicscost) {
      return 1;
    } else if (heuristicscost < o.heuristicscost) {
      return -1;
    }
    return 0;
  }

}
