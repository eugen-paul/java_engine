package org.eugenpaul.javaengine.programms.learn_pathfinding.view.view2d;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

public enum MapElements {

  NOPE(0), //
  WALL(1), //
  MUD(2), //
  START(3), //
  END(4), //
  WAY(5), //
  STEP_OLD(6), //
  STEP_NEW(7), //
  STEP_TO_CHECK(8), //
  STEP_CHECKPOINT(9) //
  ;

  private static final List<MapElements> somethingList = Arrays.asList(MapElements.values());

  @Getter
  private int value;

  private MapElements(int value) {
    this.value = value;
  }

  public static MapElements fromInt(int value) {
    if (value < 0 || value >= somethingList.size()) {
      return null;
    }
    return somethingList.get(value);
  }

}
