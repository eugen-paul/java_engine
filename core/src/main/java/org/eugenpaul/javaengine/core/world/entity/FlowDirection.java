package org.eugenpaul.javaengine.core.world.entity;

import lombok.Getter;

/**
 * Possible Move Direction from/to neighbor Nodes
 * 
 * @author Eugen Paul
 *
 */
public enum FlowDirection {
  X_NEGATIVE(0), //
  X_POSITIVE(1), //
  Y_NEGATIVE(2), //
  Y_POSITIVE(3), //
  Z_NEGATIVE(4), //
  Z_POSITIVE(5) //
  ;

  @Getter
  private int value;

  private static final FlowDirection[] somethingList = FlowDirection.values();

  private FlowDirection(int value) {
    this.value = value;
  }

  public static FlowDirection fromInt(int value) {
    if (value < 0 || value >= somethingList.length) {
      return null;
    }
    return somethingList[value];
  }
}
