package org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model;

import org.eugenpaul.javaengine.commons.algos.pathfinding.LeeTileBasedMap;
import org.eugenpaul.javaengine.commons.algos.pathfinding.GreedyBestTileBasedMap;
import org.eugenpaul.javaengine.commons.algos.pathfinding.AStarTileBasedMap;
import org.eugenpaul.javaengine.core.interfaces.algos.Pathfinding;

import java.lang.reflect.InvocationTargetException;

import lombok.Getter;

public enum PathfindingAlgo {
  LEE(0, LeeTileBasedMap.class), //
  GREEDYBEST(1, GreedyBestTileBasedMap.class), //
  ASTAR(1, AStarTileBasedMap.class);

  @Getter
  private int value;

  @Getter
  private Class<? extends Pathfinding> pathfindingClass;

  private <T extends Pathfinding> PathfindingAlgo(int value, Class<T> algo) {
    this.value = value;
    pathfindingClass = algo;
  }

  public Pathfinding getNewPathfinding() {
    try {
      return pathfindingClass.getDeclaredConstructor().newInstance();
    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
      e.printStackTrace();
      return null;
    }
  }
}