package org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model;

import java.util.Arrays;
import java.util.List;

import org.eugenpaul.javaengine.core.world.entity.collision.ICollisionCondition;

import lombok.Getter;

/**
 * Elements on the map.
 * 
 * @author Eugen Paul
 *
 */
public enum WorldElements implements ICollisionCondition {

  NOPE(0, false, 1.0), //
  WALL(1, true, 0), //
  DIRT(2, false, 1.5)//
  ;

  private static final List<WorldElements> somethingList = Arrays.asList(WorldElements.values());

  @Getter
  private int value;
  @Getter
  private double speedModifier;
  @Getter
  private boolean collision;

  private WorldElements(int value, boolean isCollision, double speedModifier) {
    this.value = value;
    this.speedModifier = speedModifier;
    this.collision = isCollision;
  }

  public static WorldElements fromInt(int value) {
    if (value < 0 || value >= somethingList.size()) {
      return null;
    }
    return somethingList.get(value);
  }

  @Override
  public boolean isSame(ICollisionCondition a) {
    if (!(a instanceof WorldElements)) {
      return false;
    }

    WorldElements testVar = (WorldElements) a;

    switch (testVar) {
    case NOPE:
    case DIRT:
      return (NOPE == this) || (DIRT == this);
    case WALL:
      return WALL == this;
    }

    return false;
  }

}
