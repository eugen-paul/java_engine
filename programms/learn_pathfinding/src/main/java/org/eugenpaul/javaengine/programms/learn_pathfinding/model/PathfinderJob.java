package org.eugenpaul.javaengine.programms.learn_pathfinding.model;

import org.eugenpaul.javaengine.core.multithreading.scheduler.Job;

public class PathfinderJob implements Job {

  public static final String NAME = "AutoPathfinder";

  private World world;

  public PathfinderJob(World world) {
    this.world = world;
  }

  @Override
  public boolean execute() {
    return !world.doPathfindingStep();
  }

  @Override
  public String getName() {
    return NAME;
  }

}
