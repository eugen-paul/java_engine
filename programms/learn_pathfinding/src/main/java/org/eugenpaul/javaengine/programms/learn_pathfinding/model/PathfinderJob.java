package org.eugenpaul.javaengine.programms.learn_pathfinding.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eugenpaul.javaengine.core.multithreading.scheduler.Job;
import org.eugenpaul.javaengine.programms.learn_pathfinding.controller.DefaultController;

public class PathfinderJob implements Job, AbstractModel {

  private PropertyChangeSupport propertyChangeSupport;

  public static final String NAME = "AutoPathfinder";

  private World world;

  public PathfinderJob(World world) {
    this.world = world;
    propertyChangeSupport = new PropertyChangeSupport(this);
  }

  @Override
  public boolean execute() {
    boolean wayFound = world.doPathfindingStep();

    if (wayFound) {
      stopped();
    } else {
      propertyChangeSupport.firePropertyChange(DefaultController.ELEMENT_PATHWAYFINDING_RUNNING, null, Boolean.TRUE);
    }

    return !wayFound;
  }

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    propertyChangeSupport.addPropertyChangeListener(listener);
  }

  @Override
  public void removePropertyChangeListener(PropertyChangeListener listener) {
    propertyChangeSupport.removePropertyChangeListener(listener);
  }

  /**
   * set Job stopped
   */
  public void stopped() {
    propertyChangeSupport.firePropertyChange(DefaultController.ELEMENT_PATHWAYFINDING_RUNNING, null, Boolean.FALSE);
    PropertyChangeListener[] listeners = propertyChangeSupport.getPropertyChangeListeners();
    for (PropertyChangeListener oneListener : listeners) {
      propertyChangeSupport.removePropertyChangeListener(oneListener);
    }
  }

}
