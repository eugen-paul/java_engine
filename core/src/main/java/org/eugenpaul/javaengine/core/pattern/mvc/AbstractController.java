package org.eugenpaul.javaengine.core.pattern.mvc;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Controller for MCV.<br>
 * 
 * @author Eugen Paul
 *
 */
public abstract class AbstractController implements PropertyChangeListener {

  private ArrayList<IView> registeredViews;

  private ArrayList<AModel> registeredModels;

  /**
   * C*tor
   */
  public AbstractController() {
    registeredViews = new ArrayList<>();
    registeredModels = new ArrayList<>();
  }

  public void addModel(AModel model) {
    registeredModels.add(model);
    model.addPropertyChangeListener(this);
  }

  public void removeModel(AModel model) {
    registeredModels.remove(model);
    model.removePropertyChangeListener(this);
  }

  /**
   * add/register view to controller.
   * 
   * @param view
   */
  public void addView(IView view) {
    registeredViews.add(view);
  }

  /**
   * remove vier from controller.
   * 
   * @param view
   */
  public void removeView(IView view) {
    registeredViews.remove(view);
  }

  @Override
  /**
   * Use this to observe property changes from registered models and propagate them on to all the views.
   */
  public void propertyChange(PropertyChangeEvent evt) {
    for (IView view : registeredViews) {
      view.modelPropertyChange(evt);
    }
  }

  /**
   * This is a convenience method that subclasses can call upon to fire property changes back to the models. This method uses reflection to inspect each of the model classes to
   * determine whether it is the owner of the property in question. If it isn't, a NoSuchMethodException is thrown, which the method ignores.
   *
   * @param propertyName = The name of the property.
   * @param newValue     = An object that represents the new value of the property.
   */
  protected void setModelProperty(String propertyName, Object newValue) {
    for (AModel model : registeredModels) {
      try {
        Method method = model.getClass().getMethod("set" + propertyName, new Class[] { newValue.getClass() });
        method.invoke(model, newValue);
      } catch (Exception ex) {
        // Handle exception.
      }
    }
  }

  /**
   * This is a convenience method that subclasses can call upon to fire property changes back to the models. This method uses reflection to inspect each of the model classes to
   * determine whether it is the owner of the property in question. If it isn't, a NoSuchMethodException is thrown, which the method ignores.
   *
   * @param propertyName = The name of the property.
   * @param newValue1    = An object that represents the new value of the property.
   * @param newValue2    = An object that represents the new value of the property.
   */
  protected void setModelProperty(String propertyName, Object newValue1, Object newValue2) {
    for (AModel model : registeredModels) {
      try {
        Method method = model.getClass().getMethod("set" + propertyName, new Class[] { newValue1.getClass(), newValue2.getClass() });
        method.invoke(model, newValue1, newValue2);
      } catch (Exception ex) {
        // Handle exception.
      }
    }
  }

  /**
   * This is a convenience method that subclasses can call upon to fire property changes back to the models. This method uses reflection to inspect each of the model classes to
   * determine whether it is the owner of the property in question. If it isn't, a NoSuchMethodException is thrown, which the method ignores.
   *
   * @param propertyName = The name of the property.
   * @param newValue     = An array of objects that represents the new values of the property.
   */
  protected void setModelProperty(String propertyName, Object... newValue) {
    for (AModel model : registeredModels) {
      try {
        // TODO to test
        Class<?>[] params = new Class[newValue.length];

        for (int i = 0; i < newValue.length; i++) {
          params[i] = newValue[i].getClass();
        }

        Method method = model.getClass().getMethod("set" + propertyName, params);
        method.invoke(model, newValue);
      } catch (Exception ex) {
        // Handle exception.
      }
    }
  }

}