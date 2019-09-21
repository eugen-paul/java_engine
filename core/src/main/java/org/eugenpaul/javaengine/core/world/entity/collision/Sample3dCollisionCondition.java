package org.eugenpaul.javaengine.core.world.entity.collision;

import java.util.Arrays;

import lombok.Getter;

public enum Sample3dCollisionCondition implements ICollisionCondition {
  NOT(0), // You can move through the object.
  GROUND(1), // You can move on it.
  BARRIER(2) // You can not move on or through the object.
  ;

  @Getter
  private int value;

  private static final Sample3dCollisionCondition[] somethingList = (Sample3dCollisionCondition[]) Arrays.asList(Sample3dCollisionCondition.values()).toArray();

  private Sample3dCollisionCondition(int value) {
    this.value = value;
  }

  public static Sample3dCollisionCondition fromInt(int value) {
    if (value < 0 || value >= somethingList.length) {
      return null;
    }
    return somethingList[value];
  }

  @Override
  public boolean equals(ICollisionCondition a) {
    if (!(a instanceof Sample3dCollisionCondition)) {
      return false;
    }

    Sample3dCollisionCondition testVar = (Sample3dCollisionCondition) a;
    return testVar == this;
  }
}
