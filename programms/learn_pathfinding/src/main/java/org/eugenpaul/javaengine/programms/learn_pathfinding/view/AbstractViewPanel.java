package org.eugenpaul.javaengine.programms.learn_pathfinding.view;

import java.beans.PropertyChangeEvent;

/**
 * View of MCV
 * 
 * @author Eugen Paul
 *
 */
public interface AbstractViewPanel {

  /**
   * callback function, that will be called by changes in model.
   * 
   * @param evt
   */
  public void modelPropertyChange(final PropertyChangeEvent evt);
}
