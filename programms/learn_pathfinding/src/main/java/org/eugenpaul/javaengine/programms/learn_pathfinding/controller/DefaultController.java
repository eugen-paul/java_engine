package org.eugenpaul.javaengine.programms.learn_pathfinding.controller;

import org.eugenpaul.javaengine.core.multithreading.scheduler.Job;
import org.eugenpaul.javaengine.core.multithreading.scheduler.JobElement;
import org.eugenpaul.javaengine.core.multithreading.scheduler.JobScheduler;
import org.eugenpaul.javaengine.programms.learn_pathfinding.model.MoverTyp;
import org.eugenpaul.javaengine.programms.learn_pathfinding.model.PathfinderJob;
import org.eugenpaul.javaengine.programms.learn_pathfinding.model.PathfindingAlgo;
import org.eugenpaul.javaengine.programms.learn_pathfinding.model.WorldElements;
import org.eugenpaul.javaengine.programms.learn_pathfinding.view.view2d.MapElements;

public class DefaultController extends AbstractController {

  public static final String ELEMENT_MAP = "MAP";
  public static final String ELEMENT_DEBUG_INFO = "DEBUG_INFO";
  public static final String ELEMENT_PATHWAYFINDING_RUNNING = "PATHWAYFINDING_RUNNING";

  private JobScheduler scheduler;

  public DefaultController() {
    super();
    scheduler = new JobScheduler();
  }

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

  public void setMover(MoverTyp mover) {
    world.setMover(mover);
  }

  public void setPathfindingAlgo(PathfindingAlgo algo) {
    world.setPathfindingAlgo(algo);
  }

  public void setAutoPathfinding(boolean autoPathfinding) {
    world.setAutoPathfinding(autoPathfinding);
    scheduler.removeJob(PathfinderJob.NAME);
    scheduler.stopScheduler();
  }

  public void doPathfindingStep() {
    world.doPathfindingStep();
  }

  public void resetPathfinding() {
    world.resetPathfinding();
  }

  public void startPathfinding(int millisProStep) {
    PathfinderJob pathfinderJob = new PathfinderJob(world);

    pathfinderJob.addPropertyChangeListener(this);
    scheduler.addJob(pathfinderJob, System.nanoTime(), millisProStep * 1_000_000L);
    scheduler.startScheduler();
  }

  public void stopPathfinding() {
    JobElement element = scheduler.removeJob(PathfinderJob.NAME);
    if(null != element) {
      Job job = element.getJob();
      if(null != job && (job instanceof PathfinderJob)) {
        PathfinderJob pJob = (PathfinderJob) job;
        pJob.stopped();
      }
    }
    scheduler.stopScheduler();
  }

}
