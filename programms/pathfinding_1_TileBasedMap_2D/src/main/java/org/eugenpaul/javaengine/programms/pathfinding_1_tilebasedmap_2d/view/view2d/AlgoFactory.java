package org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.view2d;

import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.map.IMapRepresentation;

import lombok.Getter;

/**
 * Abstract Factory class to create all required view components.
 * 
 * @author Eugen Paul
 *
 */
public abstract class AlgoFactory {

  @Getter
  private String name;

  protected AlgoFactory(String name) {
    this.name = name;
  }

  public abstract APaintPanel createPaintPanel();

  public abstract AControlPanel createControlPanel();

  public abstract ASettingPanel createSettingsPanel();
  
  public abstract IMapRepresentation getMap();
}
