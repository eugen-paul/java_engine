package org.eugenpaul.javaengine.programms.learn_pathfinding.controller;

import org.eugenpaul.javaengine.core.multithreading.scheduler.Job;
import org.eugenpaul.javaengine.core.multithreading.scheduler.JobScheduler;
import org.eugenpaul.javaengine.programms.learn_pathfinding.model.MoverTyp;
import org.eugenpaul.javaengine.programms.learn_pathfinding.model.PathfinderJob;
import org.eugenpaul.javaengine.programms.learn_pathfinding.model.PathfindingAlgo;
import org.eugenpaul.javaengine.programms.learn_pathfinding.model.WorldElements;
import org.eugenpaul.javaengine.programms.learn_pathfinding.view.view2d.MapElements;

public class DefaultController extends AbstractController {

  public static final String ELEMENT_MAP = "MAP";

  private JobScheduler scheduler;

  public DefaultController() {
    super();
    scheduler = new JobScheduler();
  }

  public void changeElementMap(int map[][]) {
    setModelProperty(ELEMENT_MAP, map);
  }

  public void setPointOnMap(int x, int y, MapElements value) {
    resetPathfinding();
    
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

    resetPathfinding();
  }

  public void setMover(MoverTyp mover) {
    world.setMover(mover);
  }

  public void setPathfindingAlgo(PathfindingAlgo algo) {
    world.setPathfindingAlgo(algo);
  }

  public void setAutoPathfinding(boolean autoPathfinding) {
    world.setAutoPathfinding(autoPathfinding);
  }

  public void doPathfindingStep() {
    world.doPathfindingStep();
  }

  public void resetPathfinding() {
    world.resetPathfinding();
  }

  public void startPathfinding(int millisProStep) {
    Job pathfinderJob = new PathfinderJob(world);

    scheduler.addJob(pathfinderJob, System.nanoTime(), millisProStep * 1_000_000L);
    scheduler.startScheduler();
  }

  public void stopPathfinding() {
    scheduler.removeJob(PathfinderJob.NAME);
    scheduler.stopScheduler();
  }

}
