package org.eugenpaul.javaengine.programms.learn_pathfinding.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eugenpaul.javaengine.commons.algos.pathfinding.lee.LeeTileBasedMap;
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
  }

  public WorldElements getPosition(int x, int y) {
    return (WorldElements) grid[x][y];
  }

  public WorldElements getPosition(Immutable3dPoint point) {
    return getPosition(point.getX(), point.getY());
  }

  public boolean setPosition(int x, int y, WorldElements element) {
    grid[x][y] = element;

    propertyChangeSupport.firePropertyChange(DefaultController.ELEMENT_MAP, null, getMap());

    return true;
  }

  public boolean setPosition(Immutable3dPoint point, WorldElements element) {
    return setPosition(point.getX(), point.getY(), element);
  }

  public int[][] getMap() {
    final int[][] result = new int[grid.length][];
    for (int i = 0; i < grid.length; i++) {
      result[i] = new int[grid[i].length];
      for (int k = 0; k < grid[i].length; k++) {
        result[i][k] = ((WorldElements) grid[i][k]).getValue();
      }
    }

    if (start != null) {
      result[start.getX()][start.getY()] = MapElements.START.getValue();
    }
    if (end != null) {
      result[end.getX()][end.getY()] = MapElements.END.getValue();
    }

    List<Immutable3dPoint> way = doPathfinding();
    Iterator<Immutable3dPoint> iterator = way.iterator();
    while (iterator.hasNext()) {
      Immutable3dPoint step = iterator.next();
      result[step.getX()][step.getY()] = MapElements.STEP.getValue();
    }

    return result;
  }

  public boolean setStart(int x, int y) {
    start = new Immutable3dPoint(x, y, 0);

    propertyChangeSupport.firePropertyChange(DefaultController.ELEMENT_MAP, null, getMap());
    return true;
  }

  public boolean setEnd(int x, int y) {
    end = new Immutable3dPoint(x, y, 0);

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

    // Initial variables for path finding
    LinkedList<Immutable3dPoint> respose = new LinkedList<Immutable3dPoint>();

    LeeTileBasedMap pathfinding = new LeeTileBasedMap();
    pathfinding.init(this, mapMoving);

    AMotionState fromStep = mover.getStartState();
    Immutable3dPoint fromPoint = new Immutable3dPoint(start);

    AMotionState toStep = mover.getEndState();
    Immutable3dPoint toPoint = new Immutable3dPoint(end);

    // do path finding
    List<Step> way = pathfinding.getPath(mover.getMover(), fromStep, fromPoint, toStep, toPoint);

    // print way
    if (way != null) {
      Iterator<Step> iterator = way.iterator();
      Immutable3dPoint lastPoint = start;
      while (iterator.hasNext()) {
        Step stepWay = iterator.next();
        Iterator<Immutable3dPoint> stepPoints = stepWay.getMovingWay().iterator();
        // add all Points from StepWay to response.
        while (stepPoints.hasNext()) {
          Immutable3dPoint step = stepPoints.next();
          Immutable3dPoint point = new Immutable3dPoint(//
              lastPoint.getX() + step.getX(), //
              lastPoint.getY() + step.getY(), //
              lastPoint.getZ() + step.getZ() //
          );
          respose.add(point);
          lastPoint = point;
        }
      }
    }

    return respose;
  }

//  private List<Immutable3dPoint> doVectorPathfinding() {
//    if (start == null || end == null) {
//      return new LinkedList<Immutable3dPoint>();
//    }
//
//    LinkedList<Immutable3dPoint> respose = new LinkedList<Immutable3dPoint>();
//
//    Lee pathfinding = new Lee();
//    pathfinding.init(this);
//
//    AMover mover = new SimpleVectorMover();
//
//    AMotionState fromStep = new SimpleVectorStepPoint(0, 0, 0, 1, 0, 0);
//    Immutable3dPoint fromPoint = new Immutable3dPoint(start);
//    AMotionState toStep = new SimpleVectorStepPoint(0, 0, 0, 1, 0, 0);
//    Immutable3dPoint toPoint = new Immutable3dPoint(end);
//
//    List<Step> way = pathfinding.getPath(mover, fromStep, fromPoint, toStep, toPoint);
//
//    if (way != null) {
//      Iterator<Step> iterator = way.iterator();
//      Immutable3dPoint lastPoint = start;
//      while (iterator.hasNext()) {
//        Step stepWay = iterator.next();
//        Iterator<AMotionState> stepPoints = stepWay.getMovingWay().iterator();
//        // add all Points from StepWay to response.
//        while (stepPoints.hasNext()) {
//          AMotionState step = stepPoints.next();
//          Immutable3dPoint point = new Immutable3dPoint(//
//              lastPoint.getX() + step.getMoving().getX(), //
//              lastPoint.getY() + step.getMoving().getY(), //
//              lastPoint.getZ() + step.getMoving().getZ() //
//          );
//          respose.add(point);
//          lastPoint = point;
//        }
//      }
//    }
//
//    return respose;
//  }

//  protected boolean checkWay(Step way, Immutable3dPoint from) {
//    Immutable3dPoint last = from;
//    List<AMotionState> wayPoints = way.getMovingWay();
//    for (AMotionState step : wayPoints) {
//      int newX = last.getX() + step.getMoving().getX();
//      if (newX < 0 || newX > sizeX - 1) {
//        return false;
//      }
//      int newY = last.getY() + step.getMoving().getY();
//      if (newY < 0 || newY > sizeY - 1) {
//        return false;
//      }
//      int newZ = last.getZ() + step.getMoving().getZ();
//      if (newZ < 0 || newZ > 0) {
//        return false;
//      }
//
//      if (grid[newX][newY] != WorldElements.NOPE.getValue()) {
//        return false;
//      }
//
//      last = new Immutable3dPoint(newX, newY, newZ);
//    }
//    return true;
//  }

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

    propertyChangeSupport.firePropertyChange(DefaultController.ELEMENT_MAP, null, getMap());
  }

}
