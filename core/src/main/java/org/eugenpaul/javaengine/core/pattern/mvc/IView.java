package org.eugenpaul.javaengine.core.pattern.mvc;

import java.beans.PropertyChangeEvent;

/**
 * View of MCV.<br>
 * 
 * @author Eugen Paul
 *
 */
public interface IView {

  /**
   * callback function, that will be called by changes in model.
   * 
   * @param evt
   */
  public void modelPropertyChange(final PropertyChangeEvent evt);

}
