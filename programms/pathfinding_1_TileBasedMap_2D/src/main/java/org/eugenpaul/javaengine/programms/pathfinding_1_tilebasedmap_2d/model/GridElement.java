package org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model;

import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.MapElements;

import lombok.Getter;
import lombok.Setter;

/**
 * One Element on the Grid
 * 
 * @author Eugen Paul
 */
@Getter
@Setter
public class GridElement {
  private MapElements mapElement;
  private int clearanceValue;

  public GridElement() {
    mapElement = MapElements.NOPE;
    clearanceValue = 0;
  }
}
