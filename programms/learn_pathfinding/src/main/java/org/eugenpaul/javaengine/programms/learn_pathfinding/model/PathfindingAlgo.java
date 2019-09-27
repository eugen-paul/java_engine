package org.eugenpaul.javaengine.programms.learn_pathfinding.model;

import org.eugenpaul.javaengine.core.interfaces.algos.Pathfinding;

import java.lang.reflect.InvocationTargetException;

import org.eugenpaul.javaengine.commons.algos.pathfinding.lee.LeeTileBasedMap;

import lombok.Getter;

public enum PathfindingAlgo {
  Lee(0, LeeTileBasedMap.class);

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
