package org.eugenpaul.javaengine.core.world.entity.collision;

import java.util.Arrays;

import lombok.Getter;

/**
 * Implementation of ICollisionCondition for simple map. Each node of the map can be {@code NOT} or {@code BARRIER}.
 * 
 * @author Eugen Paul
 *
 */
public enum Sample2dCollisionCondition implements ICollisionCondition {
  NOT(0), // You can move through/on the object.
  BARRIER(1) // You cann't through/on it.
  ;

  @Getter
  private int value;

  private static final Sample2dCollisionCondition[] somethingList = Arrays.asList(Sample2dCollisionCondition.values()).toArray(new Sample2dCollisionCondition[0]);

  private Sample2dCollisionCondition(int value) {
    this.value = value;
  }

  public static Sample2dCollisionCondition fromInt(int value) {
    if (value < 0 || somethingList.length <= value) {
      return null;
    }
    return somethingList[value];
  }

  @Override
  public boolean isSame(ICollisionCondition a) {
    if (!(a instanceof Sample2dCollisionCondition)) {
      return false;
    }

    Sample2dCollisionCondition testVar = (Sample2dCollisionCondition) a;
    return testVar == this;
  }
}
