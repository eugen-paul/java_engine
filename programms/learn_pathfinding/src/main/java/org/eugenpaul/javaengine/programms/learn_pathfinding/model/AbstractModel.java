package org.eugenpaul.javaengine.programms.learn_pathfinding.model;

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