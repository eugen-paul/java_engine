package org.eugenpaul.javaengine.core.pattern.mvc;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Model for MCV.<br>
 * 
 * @author Eugen Paul
 *
 */
public abstract class AModel {

  /**
   * 
   */
  private PropertyChangeSupport propertyChangeSupport;

  /**
   * C'tor
   */
  public AModel() {
    propertyChangeSupport = new PropertyChangeSupport(this);
  }

  /**
   * set PropertyChangeListener to fire changes in the model.
   * 
   * @param listener
   */
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    propertyChangeSupport.addPropertyChangeListener(listener);
  }

  /**
   * remove PropertyChangeListener.
   * 
   * @param listener
   */
  public void removePropertyChangeListener(PropertyChangeListener listener) {
    propertyChangeSupport.removePropertyChangeListener(listener);
  }

  /**
   * fire property change to
   * 
   * @param propertyName
   * @param oldValue
   * @param newValue
   */
  protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
    propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
  }

}