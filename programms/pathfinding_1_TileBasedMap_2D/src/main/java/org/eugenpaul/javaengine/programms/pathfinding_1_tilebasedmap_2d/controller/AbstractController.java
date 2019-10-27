package org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.World;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.WorldElements;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.AbstractViewPanel;

/**
 * Controller for MCV.
 * 
 * @author Eugen Paul
 *
 */
public abstract class AbstractController implements PropertyChangeListener {

  private ArrayList<AbstractViewPanel> registeredViews;

  protected World world;
//  private ArrayList<AbstractModel> registeredModels;

  /**
   * set world (model).
   * 
   * @param world
   */
  public void setWorld(World world) {
    this.world = world;
    world.addPropertyChangeListener(this);
  }

  /**
   * C*tor
   */
  public AbstractController() {
    registeredViews = new ArrayList<>();
//    registeredModels = new ArrayList<AbstractModel>();
  }

//  public void addModel(AbstractModel model) {
//    registeredModels.add(model);
//    model.addPropertyChangeListener(this);
//  }
//
//  public void removeModel(AbstractModel model) {
//    registeredModels.remove(model);
//    model.removePropertyChangeListener(this);
//  }

  /**
   * add/register view to controller.
   * 
   * @param view
   */
  public void addView(AbstractViewPanel view) {
    registeredViews.add(view);
  }

  /**
   * remove vier from controller.
   * 
   * @param view
   */
  public void removeView(AbstractViewPanel view) {
    registeredViews.remove(view);
  }

  /**
   * Use this to observe property changes from registered models and propagate them on to all the views.
   */
  public void propertyChange(PropertyChangeEvent evt) {

    for (AbstractViewPanel view : registeredViews) {
      view.modelPropertyChange(evt);
    }
  }

//  /**
//   * This is a convenience method that subclasses can call upon to fire property changes back to the models. This method uses reflection to inspect each of the model classes to
//   * determine whether it is the owner of the property in question. If it isn't, a NoSuchMethodException is thrown, which the method ignores.
//   *
//   * @param propertyName = The name of the property.
//   * @param newValue     = An object that represents the new value of the property.
//   */
//  protected void setModelProperty(String propertyName, Object newValue) {
//
//    for (AbstractModel model : registeredModels) {
//      try {
//
//        Method method = model.getClass().getMethod("set" + propertyName, new Class[] { newValue.getClass() }
//
//        );
//        method.invoke(model, newValue);
//
//      } catch (Exception ex) {
//        // Handle exception.
//      }
//    }
//  }

  public void setWorldPoint(int x, int y, WorldElements element) {
    world.setPosition(x, y, element);
  }

}