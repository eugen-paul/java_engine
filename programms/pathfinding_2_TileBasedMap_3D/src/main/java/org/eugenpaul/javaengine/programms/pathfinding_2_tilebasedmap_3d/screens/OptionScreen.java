package org.eugenpaul.javaengine.programms.pathfinding_2_tilebasedmap_3d.screens;

import org.eugenpaul.javaengine.core.resource.IResource;
import org.eugenpaul.javaengine.core.resource.ResourceManager;
import org.eugenpaul.javaengine.core.resource.nifty.NiftyScreenController;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;

public class OptionScreen implements NiftyScreenController {
  private ResourceManager<IResource> screenManager = null;

  private volatile String nextScreenName = null;

  public OptionScreen(ResourceManager<IResource> screenManager) {
    this.screenManager = screenManager;
  }

  @Override
  public void bind(Nifty nifty, Screen screen) {

  }

  @Override
  public void onStartScreen() {
    System.out.println("Start of Option");
  }

  @Override
  public void onEndScreen() {
    System.out.println("End of Option");
  }

  public void startButtonClick() {
    System.out.println("Go to Start");
//    res.load();
//    res.firstShow();
    nextScreenName = "start";
  }

  @Override
  public void update(float tpf) {
//    System.out.println("update Option");
    if (null != nextScreenName) {
      nextScreenName = null;
      screenManager.disable("Option");
      IResource res = screenManager.getResource("Start");
      res.init();
      screenManager.enable("Start");
    }
  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

}
