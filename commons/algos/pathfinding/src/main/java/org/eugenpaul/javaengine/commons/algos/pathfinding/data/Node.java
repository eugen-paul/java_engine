package org.eugenpaul.javaengine.commons.algos.pathfinding.data;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eugenpaul.javaengine.core.world.entity.AMotionState;
import org.eugenpaul.javaengine.core.world.entity.Step;

/**
 * Node
 * 
 * @author Eugen Paul
 *
 */
public class Node {
  List<SearchStep> states = null;

  public Node() {
    states = new LinkedList<>();
  }

  /**
   * add a State to the Node
   * 
   * @param state
   */
  public void addState(SearchStep state) {
    states.add(state);
  }

  /**
   * remove a State from the Node
   * 
   * @param state
   * @return true - OK <br>
   *         false - state not in Node
   */
  public boolean removeState(AMotionState state) {
    Iterator<SearchStep> iterator = states.iterator();
    while (iterator.hasNext()) {
      SearchStep aMotionState = (SearchStep) iterator.next();
      if (aMotionState.getState().checkState(state)) {
        iterator.remove();
        return true;
      }
    }
    return false;
  }

  /**
   * Check if the state is in the node. If the new state is better (new cost < old cost or state is new) then add it to node.
   * 
   * @param state
   * @param cost
   * @param stepFrom
   * @param x
   * @param y
   * @param z
   * @return not null - new state - new state is better or not in the node<br>
   *         null - new state is worse
   */
  public SearchStep chechAndAddNode(AMotionState state, long cost, Step stepFrom, int x, int y, int z) {
    // search the state in the node
    Iterator<SearchStep> iterator = states.iterator();
    while (iterator.hasNext()) {
      SearchStep aMotionState = (SearchStep) iterator.next();
      if (aMotionState.getState().checkState(state)) {
        // found
        if (aMotionState.getStepscost() <= cost) {
          // old state is better
          return null;
        } else {
          // new state is better
          iterator.remove(); // TODO don't remove. The step still in sorted list.
          aMotionState.setRemoved();
          break;
        }
      }
    }

    // state is better or new => add to node
    SearchStep step = new SearchStep(cost, state, stepFrom, x, y, z);
    states.add(step);
    return step;
  }

  /**
   * Get a SearchStep to Point
   * 
   * @param state
   * @return
   */
  public SearchStep getSearchStep(AMotionState state) {
    if (null == states) {
      return null;
    }
    for (SearchStep oneSearchStep : states) {
      if (oneSearchStep.getState().checkState(state)) {
        return oneSearchStep;
      }
    }
    return null;
  }

}
