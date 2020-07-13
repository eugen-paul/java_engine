package org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.controller;

import java.util.ArrayList;
import java.util.List;

import org.eugenpaul.javaengine.core.clock.Clock;
import org.eugenpaul.javaengine.core.scheduler.job.JobSchedulerThreaded;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.PathfinderJob;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.PathfindingAlgo;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.WorldElements;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.map.IMapMover;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.MapElements;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.view2d.AlgoFactory;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.view2d.ClearanceBasedFactory;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.view2d.TileBasedFactory;

/**
 * Controller for path finding.
 * 
 * @author Eugen Paul
 *
 */
public class DefaultController extends AbstractController {

  public static final String ELEMENT_MAP = "MAP";
  public static final String ELEMENT_DEBUG_INFO = "DEBUG_INFO";
  public static final String ELEMENT_PATHWAYFINDING_RUNNING = "PATHWAYFINDING_RUNNING";
  public static final String ELEMENT_REBUILD = "REBUILD";

  private JobSchedulerThreaded scheduler;

  private final Clock clock;

  private List<AlgoFactory> factories2d;
  private AlgoFactory current2dFactory;

  /**
   * C*tor
   */
  public DefaultController(Clock clock) {
    super();
    scheduler = new JobSchedulerThreaded(clock);
    this.clock = clock;
    factories2d = new ArrayList<>();
    factories2d.add(new TileBasedFactory(this));
    factories2d.add(new ClearanceBasedFactory(this));
    
    current2dFactory = factories2d.get(0);
  }

  /**
   * Set point on the map.
   * 
   * @param x
   * @param y
   * @param value
   */
  public void setPointOnMap(int x, int y, MapElements value) {
    switch (value) {
    case START:
      world.setStart(x, y);
      break;
    case END:
      world.setEnd(x, y);
      break;
    case NOPE:
      world.setPosition(x, y, WorldElements.NOPE);
      break;
    case MUD:
      world.setPosition(x, y, WorldElements.DIRT);
      break;
    case WALL:
      world.setPosition(x, y, WorldElements.WALL);
      break;
    default:
      break;
    }

  }

  /**
   * set mover type on the map.
   * 
   * @param mover
   */
  public void setMover(IMapMover mover) {
    world.setMover(mover);
  }

  /**
   * set path finding algorithm.
   * 
   * @param algo
   */
  public void setPathfindingAlgo(PathfindingAlgo algo) {
    world.setPathfindingAlgo(algo);
  }

  /**
   * set auto path finding on/off.
   * 
   * @param autoPathfinding - true path finding will start automatically after each change on the map.<br>
   *                        false - to start path finding the functions step or start path finding should be called.
   */
  public void setAutoPathfinding(boolean autoPathfinding) {
    world.setAutoPathfinding(autoPathfinding);
    scheduler.removeJob(PathfinderJob.NAME);
    scheduler.stopScheduler();
  }

  /**
   * do one step of path finding if auto path finding is off.
   */
  public void doPathfindingStep() {
    world.doPathfindingStep();
  }

  /**
   * reset path finding if auto path finding is off.
   */
  public void resetPathfinding() {
    world.resetPathfinding();
  }

  /**
   * start path finding if auto path finding is off.
   * 
   * @param millisProStep - sleep time between the path finding steps.
   */
  public void startPathfinding(int millisProStep) {
    PathfinderJob pathfinderJob = new PathfinderJob(world);

    pathfinderJob.addPropertyChangeListener(this);
    scheduler.addJob(pathfinderJob, clock.time(), millisProStep * 1_000_000L);
    scheduler.startScheduler();
  }

  /**
   * stop path finding if auto path finding is off and started.
   */
  public void stopPathfinding() {
    scheduler.removeJob(PathfinderJob.NAME);
    scheduler.stopScheduler();
  }

  public List<AlgoFactory> get2dFactories() {
    return factories2d;
  }

  public AlgoFactory getCurrent2dFactory() {
    return current2dFactory;
  }

  public void set2dFactory(String name) {
    for (AlgoFactory factory : factories2d) {
      if (name.equals(factory.getName())) {
        current2dFactory = factory;
        world.reinit(factory.getMap(), factory.getDefaultMapMover());
//        world.init(factory.getMap(), factory.getDefaultMapMover());
        return;
      }
    }
  }
}
