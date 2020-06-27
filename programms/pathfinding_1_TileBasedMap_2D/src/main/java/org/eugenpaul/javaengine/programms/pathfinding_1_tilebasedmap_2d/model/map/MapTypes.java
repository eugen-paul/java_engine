package org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.map;

import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.map.clearance.ClearanceMap;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.map.tile.TileMap;

import lombok.Getter;

/** List of Maptypes */
public enum MapTypes {
  TILE_MAP(TileMap::new, "TileMap"), //
  CLEARANCE_MAP(ClearanceMap::new, "ClearanceMap");

  @Getter
  private IMapFactory factory;
  @Getter
  private String text;

  private MapTypes(IMapFactory factory, String text) {
    this.factory = factory;
    this.text = text;
  }
}
