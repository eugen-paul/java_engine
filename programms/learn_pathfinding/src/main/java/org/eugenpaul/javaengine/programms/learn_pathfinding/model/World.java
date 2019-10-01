package org.eugenpaul.javaengine.programms.learn_pathfinding.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eugenpaul.javaengine.core.data.statistics.InfoPathfinding;
import org.eugenpaul.javaengine.core.data.statistics.InfoPathfindingMapStatus;
import org.eugenpaul.javaengine.core.interfaces.algos.Pathfinding;
import org.eugenpaul.javaengine.core.world.entity.AMotionState;
import org.eugenpaul.javaengine.core.world.entity.Step;
import org.eugenpaul.javaengine.core.world.entity.collision.ICollisionCondition;
import org.eugenpaul.javaengine.core.world.map.ITileBasedMap;
import org.eugenpaul.javaengine.core.world.map.Immutable3dPoint;
import org.eugenpaul.javaengine.core.world.moving.AMoving;
import org.eugenpaul.javaengine.core.world.moving.sample.SimpleMoving;
import org.eugenpaul.javaengine.programms.learn_pathfinding.controller.DefaultController;
import org.eugenpaul.javaengine.programms.learn_pathfinding.view.view2d.MapElements;

public class World implements ITileBasedMap, AbstractModel {

  private Immutable3dPoint start = null;
  private Immutable3dPoint end = null;
  private PropertyChangeSupport propertyChangeSupport;
  private AMoving mapMoving = null;

  private ICollisionCondition grid[][];
  private int sizeX;
  private int sizeY;

  private MoverTyp mover = null;
  private Pathfinding pathfinding = null;

  private boolean autoPathfinding = true;
  private boolean debugWayFound = false;

  /**
   * C'tor
   * 
   * @param sizeX
   * @param sizeY
   */
  public World(int sizeX, int sizeY) {
    grid = new WorldElements[sizeX][sizeY];
    for (ICollisionCondition[] is : grid) {
      Arrays.fill(is, WorldElements.NOPE);
    }
    this.sizeX = sizeX;
    this.sizeY = sizeY;

    start = new Immutable3dPoint(0, 0, 0);
    end = new Immutable3dPoint(0, 5, 0);
    propertyChangeSupport = new PropertyChangeSupport(this);
    mapMoving = new SimpleMoving();

    mover = MoverTyp.Simple2dMoverSlim;
    pathfinding = PathfindingAlgo.Lee.getNewPathfinding();
  }

  public WorldElements getPosition(int x, int y) {
    return (WorldElements) grid[x][y];
  }

  public WorldElements getPosition(Immutable3dPoint point) {
    return getPosition(point.getX(), point.getY());
  }

  public boolean setPosition(int x, int y, WorldElements element) {
    grid[x][y] = element;

    reset();

    propertyChangeSupport.firePropertyChange(DefaultController.ELEMENT_MAP, null, getMap());

    return true;
  }

  public boolean setPosition(Immutable3dPoint point, WorldElements element) {
    return setPosition(point.getX(), point.getY(), element);
  }

  private int[][] getMap() {
    final int[][] result = new int[grid.length][];
    for (int i = 0; i < grid.length; i++) {
      result[i] = new int[grid[i].length];
      for (int k = 0; k < grid[i].length; k++) {
        result[i][k] = ((WorldElements) grid[i][k]).getValue();
      }
    }

    if (!autoPathfinding) {
      addDebugInfo(result);
    } else {
      List<Immutable3dPoint> way = doPathfinding();
      if (null != way) {
        addWaypointsToMap(result, way);
      }
    }

    if (start != null) {
      result[start.getX()][start.getY()] = MapElements.START.getValue();
    }
    if (end != null) {
      result[end.getX()][end.getY()] = MapElements.END.getValue();
    }

    return result;
  }

  private void addWaypointsToMap(final int[][] map, List<Immutable3dPoint> way) {
    Iterator<Immutable3dPoint> iterator = way.iterator();
    while (iterator.hasNext()) {
      Immutable3dPoint step = iterator.next();
      map[step.getX()][step.getY()] = MapElements.WAY.getValue();
    }
  }

  private void addDebugInfo(final int[][] result) {
    InfoPathfinding info = pathfinding.getDebugInfo().getCurrentPathfindingInfo();
    if (null != info) {
      InfoPathfindingMapStatus infodata[][][] = info.getStepsmap();
      int xSize = infodata.length;
      int ySize = infodata[0].length;

      for (int x = 0; x < xSize; x++) {
        for (int y = 0; y < ySize; y++) {
          switch (infodata[x][y][0]) {
          case CHECKING_POINT:
            result[x][y] = MapElements.STEP_CHECKPOINT.getValue();
            break;
          case NEW_STEP:
            result[x][y] = MapElements.STEP_NEW.getValue();
            break;
          case CHECKED_POINT:
            result[x][y] = MapElements.STEP_OLD.getValue();
            break;
          case POINT_TO_CHECK:
            result[x][y] = MapElements.STEP_TO_CHECK.getValue();
            break;
          case CLEAR:
            break;
          }
        }
      }
    }

    if (debugWayFound) {
      List<Step> way = pathfinding.getDebugInfo().getStepsResult();
      LinkedList<Immutable3dPoint> waypoints = convertWayToPoints(way);
      addWaypointsToMap(result, waypoints);
    }
  }

  public boolean setStart(int x, int y) {
    start = new Immutable3dPoint(x, y, 0);

    reset();

    propertyChangeSupport.firePropertyChange(DefaultController.ELEMENT_MAP, null, getMap());
    return true;
  }

  public boolean setEnd(int x, int y) {
    end = new Immutable3dPoint(x, y, 0);

    reset();

    propertyChangeSupport.firePropertyChange(DefaultController.ELEMENT_MAP, null, getMap());
    return true;
  }

  @Override
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    propertyChangeSupport.addPropertyChangeListener(listener);
  }

  @Override
  public void removePropertyChangeListener(PropertyChangeListener listener) {
    propertyChangeSupport.removePropertyChangeListener(listener);
  }

  protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
    propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
  }

  private List<Immutable3dPoint> doPathfinding() {
    if (null == start || null == end) {
      return Collections.emptyList();
    }

    if (pathfinding == null) {
      return Collections.emptyList();
    }

    pathfinding.init(this, mapMoving);

    AMotionState fromStep = mover.getStartState();
    Immutable3dPoint fromPoint = new Immutable3dPoint(start);

    AMotionState toStep = mover.getEndState();
    Immutable3dPoint toPoint = new Immutable3dPoint(end);

    // do path finding
    List<Step> way = pathfinding.getPath(mover.getMover(), fromStep, fromPoint, toStep, toPoint);

    // Initial variables for path finding
    LinkedList<Immutable3dPoint> respose = null;
    // convert way to map points
    if (way != null) {
      respose = convertWayToPoints(way);
    }

    return respose;
  }

  private LinkedList<Immutable3dPoint> convertWayToPoints(List<Step> way) {
    // Initial variables for path finding
    LinkedList<Immutable3dPoint> respose = new LinkedList<Immutable3dPoint>();

    Immutable3dPoint lastPoint = start;
    for (Step stepWay : way) {
      // add all Points from StepWay to response.
      for (Immutable3dPoint step : stepWay.getMovingWay()) {
        Immutable3dPoint point = new Immutable3dPoint(//
            lastPoint.getX() + step.getX(), //
            lastPoint.getY() + step.getY(), //
            lastPoint.getZ() + step.getZ() //
        );
        respose.add(point);
      }
      lastPoint = stepWay.getMovingWay().get(stepWay.getMovingWay().size() - 1).add(lastPoint);
    }

    return respose;
  }

  @Override
  public Immutable3dPoint getPathfinderSize() {
    return new Immutable3dPoint(sizeX, sizeY, 1);
  }

  @Override
  public List<ICollisionCondition> getCollisionCondition(int x, int y, int z) {
    return Arrays.asList(grid[x][y]);
  }

  @Override
  public boolean isCollisionCondition(int x, int y, int z, ICollisionCondition condition) {
    if (x < 0 || sizeX <= x) {
      return false;
    }
    if (y < 0 || sizeY <= y) {
      return false;
    }
    if (z != 0) {
      return false;
    }
    return grid[x][y].equals(condition);
  }

  public void setMover(MoverTyp mover) {
    this.mover = mover;
    reset();

    propertyChangeSupport.firePropertyChange(DefaultController.ELEMENT_MAP, null, getMap());
  }

  public void setPathfindingAlgo(PathfindingAlgo algo) {
    pathfinding = algo.getNewPathfinding();
    pathfinding.init(this, mapMoving);
    reset();

    propertyChangeSupport.firePropertyChange(DefaultController.ELEMENT_MAP, null, getMap());
  }

  public void setAutoPathfinding(boolean autoPathfinding) {
    this.autoPathfinding = autoPathfinding;
    reset();

    propertyChangeSupport.firePropertyChange(DefaultController.ELEMENT_MAP, null, getMap());
  }

  public boolean doPathfindingStep() {
    if (!debugWayFound) {
      debugWayFound = pathfinding.getDebugInfo().doOneStep();
      propertyChangeSupport.firePropertyChange(DefaultController.ELEMENT_MAP, null, getMap());

      propertyChangeSupport.firePropertyChange(DefaultController.ELEMENT_DEBUG_INFO, null, pathfinding.getDebugInfo().getCurrentPathfindingInfo().getStepDescription());
    }

    return debugWayFound;
  }

  public void resetPathfinding() {
    reset();

    propertyChangeSupport.firePropertyChange(DefaultController.ELEMENT_MAP, null, getMap());
  }

  private void reset() {
    debugWayFound = false;

    if (!autoPathfinding) {
      pathfinding.getDebugInfo().setDebugMode(true);

      AMotionState fromStep = mover.getStartState();
      Immutable3dPoint fromPoint = new Immutable3dPoint(start);

      AMotionState toStep = mover.getEndState();
      Immutable3dPoint toPoint = new Immutable3dPoint(end);

      pathfinding.getDebugInfo().restartPathfinding(mover.getMover(), fromStep, fromPoint, toStep, toPoint);
    }
  }

}
