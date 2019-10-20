package org.eugenpaul.javaengine.commons.algos.pathfinding.data;

import org.eugenpaul.javaengine.core.world.entity.IMotionState;
import org.eugenpaul.javaengine.core.world.entity.Step;

import lombok.Getter;
import lombok.Setter;

/**
 * Search-Step of path finding. Each Search-Step contain a current/last position on the map, the full cost of path finding (from start to current position), the last step of the
 * way and the last state.
 * 
 * @author Eugen Paul
 *
 */
@Getter
public class SearchStep implements Comparable<SearchStep> {
  /** current cost of all steps */
  private long stepscost = 0;

  @Setter
  /** current cost of path finding (steps + heuristic) */
  private long heuristicscost = 0;
  /** current Motion step */
  private IMotionState state = null;
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
  /** false - the step must be checked. True - The step are checked. Don't check it again. */
  private boolean checked = false;

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
  public SearchStep(long stepscost, IMotionState state, Step stepFrom, int x, int y, int z) {
    this(stepscost, stepscost, state, stepFrom, x, y, z);
  }

  /**
   * C'tor.
   * 
   * @param stepscost      - total cost of the way
   * @param heuristicscost - total cost of the way + heuristic cost
   * @param state          - last state of the way
   * @param stepFrom       - last step
   * @param x              - x cord
   * @param y              - y cord
   * @param z              - z cord
   */
  public SearchStep(long stepscost, long heuristicscost, IMotionState state, Step stepFrom, int x, int y, int z) {
    this.stepscost = stepscost;
    this.heuristicscost = heuristicscost;
    this.state = state;
    this.stepFrom = stepFrom;
    this.x = x;
    this.y = y;
    this.z = z;
  }

  /**
   * Set the step as removed. The step should not be checked. Cann't be undone.
   */
  public void setRemoved() {
    removed = true;
  }

  /**
   * Set the step as checked. Don't check it again. Cann't be undone.
   */
  public void setChecked() {
    checked = true;
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

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

}
