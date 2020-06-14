package org.eugenpaul.javaengine.core.resource;

import com.jme3.app.Application;
import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.renderer.RenderManager;

public class AppStateToResourceAdapter implements AppState {

  private IResource resource = null;

  public AppStateToResourceAdapter(IResource resource) {
    this.resource = resource;
  }

  protected boolean initialized = false;

  @Override
  public void initialize(AppStateManager stateManager, Application app) {
    initialized = true;
    resource.init();
    resource.load();
    resource.firstShow();
  }

  @Override
  public boolean isInitialized() {
    return initialized;
  }

  @Override
  public void setEnabled(boolean enabled) {
    if (enabled) {
      resource.resume();
    } else {
      resource.pause();
    }
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public void stateAttached(AppStateManager stateManager) {
    // NIX
  }

  @Override
  public void stateDetached(AppStateManager stateManager) {
    // NIX
  }

  @Override
  public void update(float tpf) {
    resource.update(tpf);
  }

  @Override
  public void render(RenderManager rm) {
    // NIX
  }

  @Override
  public void postRender() {
    // NIX
  }

  @Override
  public void cleanup() {
    initialized = false;
    resource.unload();
  }

  @Override
  public String getId() {
    // TODO Auto-generated method stub
    return null;
  }
}
