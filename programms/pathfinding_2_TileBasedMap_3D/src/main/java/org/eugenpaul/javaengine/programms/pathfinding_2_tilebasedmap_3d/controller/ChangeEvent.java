package org.eugenpaul.javaengine.programms.pathfinding_2_tilebasedmap_3d.controller;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;

/**
 * Change events that can be fired from model to view.
 * 
 * @author Eugen Paul
 *
 */
public enum ChangeEvent {
  PRINT_PATHFINDING("PRINT_PATHFINDING"), //
  SET_MAP_ELEMENT("SET_MAP_ELEMENT"); //

  private static Map<String, ChangeEvent> dataMap = Arrays.asList(ChangeEvent.values()).stream().collect(Collectors.toMap(x -> x.getName(), x -> x));

  @Getter
  private String name;

  /**
   * C'tor
   * 
   * @param name
   */
  private ChangeEvent(String name) {
    this.name = name;
  }

  public static ChangeEvent fromString(String name) {
    return dataMap.get(name);
  }
}
