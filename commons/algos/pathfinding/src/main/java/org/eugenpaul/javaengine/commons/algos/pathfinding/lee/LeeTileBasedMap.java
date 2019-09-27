package org.eugenpaul.javaengine.commons.algos.pathfinding.lee;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.eugenpaul.javaengine.core.data.SimpleSortedList;
import org.eugenpaul.javaengine.core.data.statistics.InfoPathfinding;
import org.eugenpaul.javaengine.core.data.statistics.InfoPathfindingMapStatus;
import org.eugenpaul.javaengine.core.interfaces.algos.Pathfinding;
import org.eugenpaul.javaengine.core.interfaces.algos.PathfindingDebug;
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
public class LeeTileBasedMap implements Pathfinding, PathfindingDebug {

  /// The complete set of nodes across the map
  private MapBuffer nodes = null;

  private ITileBasedMap mapCopy = null;

  private SimpleSortedList<SearchStep> nodesToCheck = null;

  private AMoving movingTester = null;

  private boolean debugMode = false;

  private InfoPathfinding debugData = null;

  private AMover debugMover = null;
  private AMotionState debugTo = null;
  private Immutable3dPoint debugToPoint = null;

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
      if (oneStep(checkNode, mover, to, toPoint)) {
        break;
      }
      checkNode = pop();
    }

    return getPath(to, toPoint);
  }

  /**
   * One step of Pathfinding.
   * 
   * @return true - found<br>
   *         false - try more
   */
  private boolean oneStep(SearchStep checkNode, AMover mover, AMotionState to, Immutable3dPoint toPoint) {
    if (checkNode.getX() == toPoint.getX()//
        && checkNode.getY() == toPoint.getY()//
        && checkNode.getZ() == toPoint.getZ()//
        && checkNode.getState().checkState(to)//
    ) {
      return true;
    }

    // check next steps
    List<Step> stepsList = movingTester.checkPossibleSteps(mover.getNextSteps(checkNode.getState()), checkNode.getX(), checkNode.getY(), checkNode.getZ(), mapCopy);

    for (Step step : stepsList) {
      int xLastPonit = checkNode.getX() + step.getLastPosition().getX();
      int yLastPonit = checkNode.getY() + step.getLastPosition().getY();
      int zLastPonit = checkNode.getZ() + step.getLastPosition().getZ();

      if (push(//
          step.getLastState(), //
          checkNode.getCost() + step.getCost(), //
          step, //
          xLastPonit, //
          yLastPonit, //
          zLastPonit //
      )) {
        if ((xLastPonit == toPoint.getX())//
            && (yLastPonit == toPoint.getY())//
            && (zLastPonit == toPoint.getZ())//
            && step.getLastState().checkState(to)//
        ) {
          return true;
        }

        if (debugMode && null != debugData) {
          for (Immutable3dPoint stepPoint : step.getMovingWay()) {
            debugData.setPoint(checkNode.getX() + stepPoint.getX(), //
                checkNode.getY() + stepPoint.getY(), //
                checkNode.getZ() + stepPoint.getZ(), //
                InfoPathfindingMapStatus.NEW_STEP);
          }
        }
      }
    }
    return false;
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
   * @param debugFromPoint
   * @return true - added<br>
   *         false - old node
   */
  private boolean push(AMotionState state, long cost, Step stepFrom, int x, int y, int z) {
    SearchStep step = nodes.addStepToNode(state, cost, stepFrom, x, y, z);
    if (step != null) {
      nodesToCheck.add(step);
      return true;
    }
    return false;
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

  @Override
  public boolean setDebugMode(boolean mode) {
    if (null == nodes) {
      return false;
    }

    if (!debugMode && null == debugData) {
      debugData = new InfoPathfinding(nodes.getSizeX(), nodes.getSizeY(), nodes.getSizeZ());
    }
    debugMode = mode;
    return true;
  }

  @Override
  public boolean restartPathfinding(AMover mover, AMotionState from, Immutable3dPoint fromPoint, AMotionState to, Immutable3dPoint toPoint) {
    debugData.resetInfo();

    this.nodes.reset();
    this.nodesToCheck.clear();

    this.debugMover = mover;
    this.debugTo = to;
    this.debugToPoint = toPoint;

    // add Startpoint to nodesToCheck
    push(from, 0, null, fromPoint.getX(), fromPoint.getY(), fromPoint.getZ());

    return false;
  }

  @Override
  public boolean doOneStep() {
    SearchStep checkNode = pop();
    if (null == checkNode) {
      return false;
    }

    debugData.setStepDescription("Checking x = " + checkNode.getX() + ", y = " + checkNode.getY() + ", z = " + checkNode.getZ() + ". Cost = " + checkNode.getCost());
    System.out.println(debugData.getStepDescription());

    debugData.nextStep();

    boolean result = oneStep(checkNode, debugMover, debugTo, debugToPoint);

    debugData.setPoint(checkNode.getX(), checkNode.getY(), checkNode.getZ(), InfoPathfindingMapStatus.CHECKING_POINT);

    return result;
  }

  @Override
  public List<Step> getStepsResult() {
    return getPath(debugTo, debugToPoint);
  }

  @Override
  public InfoPathfinding getCurrentPathfindingInfo() {
    return debugData;
  }

  @Override
  public PathfindingDebug getDebugInfo() {
    return this;
  }

}
