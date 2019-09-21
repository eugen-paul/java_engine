package org.eugenpaul.javaengine.core.world.entity.collision;

import java.util.Arrays;

import lombok.Getter;

public enum Sample2dCollisionCondition implements ICollisionCondition {
  NOT(0), // You can move through/on the object.
  BARRIER(1) // You cann't through/on it.
  ;

  @Getter
  private int value;

  private static final Sample2dCollisionCondition[] somethingList = (Sample2dCollisionCondition[]) Arrays.asList(Sample2dCollisionCondition.values()).toArray();

  private Sample2dCollisionCondition(int value) {
    this.value = value;
  }

  public static Sample2dCollisionCondition fromInt(int value) {
    if (value < 0 || value >= somethingList.length) {
      return null;
    }
    return somethingList[value];
  }

  @Override
  public boolean equals(ICollisionCondition a) {
    if (!(a instanceof Sample2dCollisionCondition)) {
      return false;
    }

    Sample2dCollisionCondition testVar = (Sample2dCollisionCondition) a;
    return testVar == this;
  }
}
