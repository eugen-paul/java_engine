package org.eugenpaul.javaengine.programms.pathfinding_2_tilebasedmap_3d.screens;

import org.eugenpaul.javaengine.core.resource.IResource;
import org.eugenpaul.javaengine.core.resource.ResourceManager;
import org.eugenpaul.javaengine.core.resource.nifty.NiftyScreenController;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;

public class StartScreen implements NiftyScreenController {
  private ResourceManager<IResource> screenManager = null;
  private volatile String nextScreenName = null;

  public StartScreen(ResourceManager<IResource> screenManager) {
    this.screenManager = screenManager;
  }

  @Override
  public void bind(Nifty nifty, Screen screen) {

  }

  @Override
  public void onStartScreen() {

  }

  @Override
  public void onEndScreen() {

  }

  public void startButtonClick() {
    System.out.println("Go to Option");
//    res.load();
//    res.firstShow();
    nextScreenName = "Option";
  }

  @Override
  public void update(float tpf) {
//    System.out.println("update Start");
    if (null != nextScreenName) {
      nextScreenName = null;
      screenManager.disable("Start");
      IResource res = screenManager.getResource("Option");
      res.init();
      screenManager.enable("Option");
    }
  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

}