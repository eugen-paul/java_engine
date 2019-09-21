package org.eugenpaul.javaengine.programms.learn_pathfinding.model;

import java.beans.PropertyChangeListener;

public interface AbstractModel {

  public void addPropertyChangeListener(PropertyChangeListener listener);

  public void removePropertyChangeListener(PropertyChangeListener listener);

}