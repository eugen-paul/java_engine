package org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model;

import java.beans.PropertyChangeListener;

/**
 * Model for MCV
 * 
 * @author Eugen Paul
 *
 */
public interface AbstractModel {

  /**
   * set PropertyChangeListener to fire changes in the model.
   * 
   * @param listener
   */
  public void addPropertyChangeListener(PropertyChangeListener listener);

  /**
   * remove PropertyChangeListener.
   * 
   * @param listener
   */
  public void removePropertyChangeListener(PropertyChangeListener listener);

}