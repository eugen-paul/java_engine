package org.eugenpaul.javaengine.programms.pathfinding_2_tilebasedmap_3d.screens;

import org.eugenpaul.javaengine.core.resource.nifty.NiftyScreenController;
import org.eugenpaul.javaengine.core.resource.nifty.NiftyXmlManager;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;

public class OptionScreen implements NiftyScreenController {
  private NiftyXmlManager screenManager = null;

  private volatile String nextScreenName = null;

  public OptionScreen(NiftyXmlManager screenManager2) {
    this.screenManager = screenManager2;
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
    nextScreenName = "StartMain";
  }

  @Override
  public void update(float tpf) {
//    System.out.println("update Option");
    if (null != nextScreenName) {
      nextScreenName = null;
      screenManager.disable("OptionMain");
      screenManager.enable("StartMain");
    }
  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

}
