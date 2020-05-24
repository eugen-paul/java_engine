package org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.map;

/** Constructor reference for all maps */
public interface IMapFactory {
  public IMapRepresentation generateMap(int sizeX, int sizeY);
}
