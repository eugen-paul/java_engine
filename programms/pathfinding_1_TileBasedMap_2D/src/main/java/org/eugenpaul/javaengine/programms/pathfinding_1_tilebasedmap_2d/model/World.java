package org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model;

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
import org.eugenpaul.javaengine.core.world.entity.IMotionState;
import org.eugenpaul.javaengine.core.world.entity.Step;
import org.eugenpaul.javaengine.core.world.entity.collision.ICollisionCondition;
import org.eugenpaul.javaengine.core.world.map.ITileBasedMap;
import org.eugenpaul.javaengine.core.world.map.Immutable3dPoint;
import org.eugenpaul.javaengine.core.world.moving.IMoving;
import org.eugenpaul.javaengine.core.world.moving.sample.SimpleMoving;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.controller.DefaultController;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.MapElements;

/**
 * Model for MCV.<br>
 * In this example all model functions are synchronized because there is are JobThread that calls are Model-Functions too.
 * 
 * @author Eugen Paul
 *
 */
public class World implements ITileBasedMap, AbstractModel {

  private Immutable3dPoint start = null;
  private Immutable3dPoint end = null;
  private PropertyChangeSupport propertyChangeSupport;
  private IMoving<Step> mapMoving = null;

  private ICollisionCondition[][] grid;
  private int sizeX;
  private int sizeY;

  private MoverTyp mover = null;
  private Pathfinding<Step> pathfinding = null;

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

    mover = MoverTyp.SIMPLE_2D_MOVER_SLIM;
    pathfinding = PathfindingAlgo.LEE.getNewPathfinding();
  }

  public synchronized WorldElements getPosition(int x, int y) {
    return (WorldElements) grid[x][y];
  }

  public WorldElements getPosition(Immutable3dPoint point) {
    return getPosition(point.getX(), point.getY());
  }

  public synchronized boolean setPosition(int x, int y, WorldElements element) {
    grid[x][y] = element;

    reset();

    propertyChangeSupport.firePropertyChange(DefaultController.ELEMENT_MAP, null, getMap());

    return true;
  }

  public boolean setPosition(Immutable3dPoint point, WorldElements element) {
    return setPosition(point.getX(), point.getY(), element);
  }

  private GridElement[][] getMap() {
    final GridElement[][] result = getCurrentMap();

    if (!autoPathfinding) {
      addDebugInfo(result);
    } else {
      List<Immutable3dPoint> way = doPathfinding();
      if (null != way) {
        addWaypointsToMap(result, way);
      }
    }

    if (start != null) {
      result[start.getX()][start.getY()].setMapElement(MapElements.START);
    }
    if (end != null) {
      result[end.getX()][end.getY()].setMapElement(MapElements.END);
    }

    return result;
  }

  /**
   * fire CurrentMap with path finding to view
   */
  public void fireCurrentMap() {
    propertyChangeSupport.firePropertyChange(DefaultController.ELEMENT_MAP, null, getMap());
  }

  /**
   * get current map without doing a path finding
   * 
   * @return
   */
  private GridElement[][] getCurrentMap() {
    final GridElement[][] result = new GridElement[grid.length][];
    for (int i = 0; i < grid.length; i++) {
      result[i] = new GridElement[grid[i].length];
      for (int k = 0; k < grid[i].length; k++) {
        GridElement elem = new GridElement();
        result[i][k] = elem;
        WorldElements worldElem = (WorldElements) grid[i][k];
        switch (worldElem) {
        case NOPE:
          elem.setMapElement(MapElements.NOPE);
          break;
        case WALL:
          elem.setMapElement(MapElements.WALL);
          break;
        case DIRT:
          elem.setMapElement(MapElements.MUD);
          break;
        }

        elem.setClearanceValue(1);
      }
    }

    return result;
  }

  private void addWaypointsToMap(final GridElement[][] map, List<Immutable3dPoint> way) {
    Iterator<Immutable3dPoint> iterator = way.iterator();
    while (iterator.hasNext()) {
      Immutable3dPoint step = iterator.next();
      map[step.getX()][step.getY()].setMapElement(MapElements.WAY);
    }
  }

  private void addDebugInfo(final GridElement[][] result) {
    InfoPathfinding info = pathfinding.getDebugInfo().getCurrentPathfindingInfo();
    if (null != info) {
      InfoPathfindingMapStatus[][][] infodata = info.getInfoMap();
      int xSize = infodata.length;
      int ySize = infodata[0].length;

      for (int x = 0; x < xSize; x++) {
        for (int y = 0; y < ySize; y++) {
          switch (infodata[x][y][0]) {
          case CHECKING_POINT:
            result[x][y].setMapElement(MapElements.STEP_CHECKPOINT);
            break;
          case NEW_STEP:
            result[x][y].setMapElement(MapElements.STEP_NEW);
            break;
          case CHECKED_POINT:
            result[x][y].setMapElement(MapElements.STEP_OLD);
            break;
          case POINT_TO_CHECK:
            result[x][y].setMapElement(MapElements.STEP_TO_CHECK);
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

  public synchronized boolean setStart(int x, int y) {
    start = new Immutable3dPoint(x, y, 0);

    reset();

    propertyChangeSupport.firePropertyChange(DefaultController.ELEMENT_MAP, null, getMap());
    return true;
  }

  public synchronized boolean setEnd(int x, int y) {
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

    pathfinding.init(this, mapMoving, true);

    IMotionState fromStep = mover.getStartState();
    Immutable3dPoint fromPoint = new Immutable3dPoint(start);

    IMotionState toStep = mover.getEndState();
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
    LinkedList<Immutable3dPoint> respose = new LinkedList<>();

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
  public List<ICollisionCondition> getCollisionCondition(Immutable3dPoint position) {
    return getCollisionCondition(position.getX(), position.getY(), position.getZ());
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
    return grid[x][y].isSame(condition);
  }

  public synchronized void setMover(MoverTyp mover) {
    this.mover = mover;
    reset();

    propertyChangeSupport.firePropertyChange(DefaultController.ELEMENT_MAP, null, getMap());
  }

  public synchronized void setPathfindingAlgo(PathfindingAlgo algo) {
    pathfinding = algo.getNewPathfinding();
    pathfinding.init(this, mapMoving, true);
    reset();

    propertyChangeSupport.firePropertyChange(DefaultController.ELEMENT_MAP, null, getMap());
  }

  public synchronized void setAutoPathfinding(boolean autoPathfinding) {
    this.autoPathfinding = autoPathfinding;
    pathfinding.init(this, mapMoving, true);
    reset();

    propertyChangeSupport.firePropertyChange(DefaultController.ELEMENT_MAP, null, getMap());
  }

  public synchronized boolean doPathfindingStep() {
    if (!debugWayFound) {
      debugWayFound = pathfinding.getDebugInfo().doOneStep();
      propertyChangeSupport.firePropertyChange(DefaultController.ELEMENT_MAP, null, getMap());

      propertyChangeSupport.firePropertyChange(DefaultController.ELEMENT_DEBUG_INFO, null, pathfinding.getDebugInfo().getCurrentPathfindingInfo().getStepDescription());
    }

    return debugWayFound;
  }

  public synchronized void resetPathfinding() {
    reset();

    propertyChangeSupport.firePropertyChange(DefaultController.ELEMENT_MAP, null, getMap());
  }

  private void reset() {
    debugWayFound = false;

    if (!autoPathfinding) {
      pathfinding.getDebugInfo().setDebugMode(true);

      IMotionState fromStep = mover.getStartState();
      Immutable3dPoint fromPoint = new Immutable3dPoint(start);

      IMotionState toStep = mover.getEndState();
      Immutable3dPoint toPoint = new Immutable3dPoint(end);

      pathfinding.getDebugInfo().restartPathfinding(mover.getMover(), fromStep, fromPoint, toStep, toPoint);
    }
  }

}
