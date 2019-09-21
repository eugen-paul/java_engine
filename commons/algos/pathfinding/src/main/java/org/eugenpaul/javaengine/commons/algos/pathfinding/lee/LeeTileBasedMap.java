package org.eugenpaul.javaengine.commons.algos.pathfinding.lee;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.eugenpaul.javaengine.core.data.SimpleSortedList;
import org.eugenpaul.javaengine.core.interfaces.algos.Pathfinding;
import org.eugenpaul.javaengine.core.world.entity.AMotionState;
import org.eugenpaul.javaengine.core.world.entity.AMover;
import org.eugenpaul.javaengine.core.world.entity.Step;
import org.eugenpaul.javaengine.core.world.map.ITileBasedMap;
import org.eugenpaul.javaengine.core.world.map.Immutable3dPoint;
import org.eugenpaul.javaengine.core.world.moving.AMoving;

/**
 * Implementarion of Lee algorithm in TileBasedMap
 * 
 * @author Eugen Paul
 *
 */
public class LeeTileBasedMap implements Pathfinding {

  /// The complete set of nodes across the map
  private MapBuffer nodes = null;

  private ITileBasedMap mapCopy = null;

  private SimpleSortedList<SearchStep> nodesToCheck = null;

  private AMoving movingTester = null;

  @Override
  public boolean init(ITileBasedMap map, AMoving movingTester) {
    Immutable3dPoint mapSize = map.getPathfinderSize();
    nodes = new MapBuffer(mapSize.getX(), mapSize.getY(), mapSize.getZ());

    this.mapCopy = map;
    this.movingTester = movingTester;

    nodesToCheck = new SimpleSortedList<>();
    return true;
  }

  @Override
  public List<Step> getPath(AMover mover, AMotionState from, Immutable3dPoint fromPoint, AMotionState to, Immutable3dPoint toPoint) {

    // add Startpoint to nodesToCheck
    push(from, 0, null, fromPoint.getX(), fromPoint.getY(), fromPoint.getZ());

    SearchStep checkNode = pop();
    while (checkNode != null) {
      // check if the end node is received
      if (checkNode.getX() == toPoint.getX()//
          && checkNode.getY() == toPoint.getY()//
          && checkNode.getZ() == toPoint.getZ()//
          && checkNode.getState().checkState(to)//
      ) {
        break;
      }

      // check next steps
      List<Step> stepsList = movingTester.checkPossibleSteps(mover.getNextSteps(checkNode.getState()), checkNode.getX(), checkNode.getY(), checkNode.getZ(), mapCopy);
      for (Step step : stepsList) {
        push(//
            step.getLastState(), //
            checkNode.getCost() + step.getCost(), //
            step, //
            checkNode.getX() + step.getLastPosition().getX(), //
            checkNode.getY() + step.getLastPosition().getY(), //
            checkNode.getZ() + step.getLastPosition().getZ() //
        );
      }
      checkNode = pop();
    }

    return getPath(to, toPoint);
  }

  /**
   * get Path from nodes
   * 
   * @return
   */
  private List<Step> getPath(AMotionState to, Immutable3dPoint toPoint) {
    LinkedList<Step> response = new LinkedList<>();

    Immutable3dPoint pointToTest = toPoint;
    Node lastNode = nodes.getNode(toPoint);
    if (null == lastNode) {
      return null;
    }

    SearchStep lastSearchStep = nodes.getNode(toPoint).getSearchStep(to);

    while (lastSearchStep != null) {
      Step lastStep = lastSearchStep.getStepFrom();
      if (null == lastStep) {
        break;
      }
      response.addFirst(lastStep);
      pointToTest = pointToTest.minus(lastStep.getLastPosition());
      lastSearchStep = nodes.getNode(pointToTest).getSearchStep(lastStep.getFromState());
    }

    return response;
  }

  /**
   * push step to check List, if the step a good.
   * 
   * @param step
   * @param fromPoint
   */
  private void push(AMotionState state, double cost, Step stepFrom, int x, int y, int z) {
    SearchStep step = nodes.addStepToNode(state, cost, stepFrom, x, y, z);
    if (step != null) {
      nodesToCheck.add(step);
    }
  }

  /**
   * get next note to check
   * 
   * @return
   */
  private SearchStep pop() {
    try {
      return nodesToCheck.pop();
    } catch (NoSuchElementException e) {
      return null;
    }
  }

}
