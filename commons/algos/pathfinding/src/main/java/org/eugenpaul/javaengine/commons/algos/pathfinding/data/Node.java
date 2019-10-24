package org.eugenpaul.javaengine.commons.algos.pathfinding.data;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eugenpaul.javaengine.core.world.entity.IMotionState;
import org.eugenpaul.javaengine.core.world.entity.Step;

/**
 * One node on the map, to save visited steps. One node can contain one or more steps.
 * 
 * @author Eugen Paul
 *
 */
public class Node {
  List<SearchStep> states = null;

  /**
   * C'tor
   */
  public Node() {
    states = new LinkedList<>();
  }

  /**
   * add a State to the Node
   * 
   * @param state - state to add
   */
  public void addState(SearchStep state) {
    states.add(state);
  }

  /**
   * remove a State from the Node
   * 
   * @param state - state to remove
   * @return true - OK <br>
   *         false - state not in Node
   */
  public boolean removeState(IMotionState state) {
    Iterator<SearchStep> iterator = states.iterator();
    while (iterator.hasNext()) {
      SearchStep aMotionState = iterator.next();
      if (aMotionState.getState().isSame(state)) {
        iterator.remove();
        return true;
      }
    }
    return false;
  }

  /**
   * Check if the state is in the node. If the new state is better (new cost < old cost or state is new) then add it to node.
   * 
   * @param state    end state of the step
   * @param cost     full cost of the way
   * @param stepFrom step to reach this position
   * @param x        end x-position of the last step / way
   * @param y        end y-position of the last step / way
   * @param z        end z-position of the last step / way
   * @return not null - new state - new state is better or not in the node<br>
   *         null - new state is worse
   */
  public SearchStep chechAndAddNode(IMotionState state, long cost, Step stepFrom, int x, int y, int z) {
    boolean returnStep = true;
    // search the state in the node
    Iterator<SearchStep> iterator = states.iterator();
    while (iterator.hasNext()) {
      SearchStep aMotionState = iterator.next();
      if (aMotionState.getState().isSame(state)) {
        // found
        if (aMotionState.getStepscost() <= cost) {
          // old state is better
          return null;
        } else {
          if (aMotionState.isChecked()) {
            returnStep = false;
          }
          // new state is better
          iterator.remove();
          aMotionState.setRemoved();
          break;
        }
      }
    }

    // state is better or new => add to node
    SearchStep step = new SearchStep(cost, state, stepFrom, x, y, z);
    states.add(step);

    if (returnStep) {
      // new State is better AND old step is not checked
      return step;
    } else {
      step.setChecked();
      return null;
    }

  }

  /**
   * Get a SearchStep to Point
   * 
   * @param state
   * @return
   */
  public SearchStep getSearchStep(IMotionState state) {
    if (null == states) {
      return null;
    }
    for (SearchStep oneSearchStep : states) {
      if (oneSearchStep.getState().isSame(state)) {
        return oneSearchStep;
      }
    }
    return null;
  }

}
