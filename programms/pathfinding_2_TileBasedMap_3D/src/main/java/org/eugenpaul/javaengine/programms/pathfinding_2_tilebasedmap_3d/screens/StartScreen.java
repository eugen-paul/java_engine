package org.eugenpaul.javaengine.programms.pathfinding_2_tilebasedmap_3d.screens;

import org.eugenpaul.javaengine.core.resource.nifty.NiftyScreenController;
import org.eugenpaul.javaengine.core.resource.nifty.NiftyXmlManager;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;

public class StartScreen implements NiftyScreenController {
  private NiftyXmlManager screenManager = null;
  private volatile String nextScreenName = null;

  public StartScreen(NiftyXmlManager screenManager2) {
    this.screenManager = screenManager2;
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
    nextScreenName = "OptionMain";
  }

  @Override
  public void update(float tpf) {
    if (null != nextScreenName) {
      nextScreenName = null;
      screenManager.disable("StartMain"); // TODO edit double disable of current screen
      screenManager.enable("OptionMain");
    }
  }

  @Override
  public void pause() {
    // nix
  }

  @Override
  public void resume() {
    // nix
  }

}
