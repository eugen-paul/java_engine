package org.eugenpaul.javaengine.programms.pathfinding_2_tilebasedmap_3d.view;

import java.beans.PropertyChangeEvent;

import org.eugenpaul.javaengine.core.pattern.mvc.IView;
import org.eugenpaul.javaengine.core.resource.IResource;
import org.eugenpaul.javaengine.programms.pathfinding_2_tilebasedmap_3d.controller.ChangeEvent;

/**
 * Nifty GUI
 * 
 * @author Eugen Paul
 *
 */
public class Gui implements IView, IResource {

  @Override
  public void modelPropertyChange(PropertyChangeEvent evt) {
    ChangeEvent event = ChangeEvent.fromString(evt.getPropertyName());
    if (null == event) {
      return;
    }
    switch (event) {
    case PRINT_PATHFINDING:
      System.out.println("PRINT_PATHFINDING");
      break;
    case SET_MAP_ELEMENT:
      System.out.println("SET_MAP_ELEMENT");
      break;
    default:
      break;
    }
  }

  @Override
  public void init() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void load() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void firstShow() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void update(float tpf) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void unload() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void pause() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void resume() {
    // TODO Auto-generated method stub
    
  }


}
