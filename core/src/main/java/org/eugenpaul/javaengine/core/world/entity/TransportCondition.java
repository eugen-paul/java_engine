package org.eugenpaul.javaengine.core.world.entity;

import lombok.Getter;

public enum TransportCondition {
  EMPTY(0), // No collisions with the object.
  LIFT(1), // You can move up or down to another LIFT Object.
  TELEPORT(3), // Teleport to another Position.
  STAIRS(4), // A stairs to move up or down.
  ;

  @Getter
  private int value;

  private static final TransportCondition[] somethingList = TransportCondition.values();

  private TransportCondition(int value) {
    this.value = value;
  }

  public static TransportCondition fromInt(int value) {
    if (value < 0 || value >= somethingList.length) {
      return null;
    }
    return somethingList[value];
  }
}
