package org.eugenpaul.javaengine.programms.learn_pathfinding.model;

import java.util.Arrays;
import java.util.List;

import org.eugenpaul.javaengine.core.world.entity.collision.ICollisionCondition;

import lombok.Getter;

public enum WorldElements implements ICollisionCondition {

  NOPE(0, false, 1.0), //
  WALL(1, true, 0), //
  DIRT(2, false, 0.8)//
  ;

  private static final List<WorldElements> somethingList = Arrays.asList(WorldElements.values());

  @Getter
  private int value;
  @Getter
  private double speedModifier;

  private WorldElements(int value, boolean isCollision, double speedModifier) {
    this.value = value;
    this.speedModifier = speedModifier;
  }

  public static WorldElements fromInt(int value) {
    if (value < 0 || value >= somethingList.size()) {
      return null;
    }
    return somethingList.get(value);
  }

  @Override
  public boolean equals(ICollisionCondition a) {
    if (!(a instanceof WorldElements)) {
      return false;
    }

    WorldElements testVar = (WorldElements) a;
    return testVar == this;
  }

}
