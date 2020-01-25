package org.eugenpaul.javaengine.programms.pathfinding_2_tilebasedmap_3d;

import org.eugenpaul.javaengine.core.resource.nifty.NiftyXmlManager;
import org.eugenpaul.javaengine.programms.pathfinding_2_tilebasedmap_3d.controller.MainController;
import org.eugenpaul.javaengine.programms.pathfinding_2_tilebasedmap_3d.screens.OptionScreen;
import org.eugenpaul.javaengine.programms.pathfinding_2_tilebasedmap_3d.screens.StartScreen;

import com.jme3.app.SimpleApplication;

public class Application extends SimpleApplication {

  private MainController controller = null;
  private NiftyXmlManager screenManager = null;

  public Application(MainController controller) {
    this.controller = controller;
  }

  @Override
  public void simpleInitApp() {
    screenManager = new NiftyXmlManager(assetManager, inputManager, audioRenderer, guiViewPort);

    screenManager.loadStyle("nifty-default-styles.xml");
    screenManager.loadControll("nifty-default-controls.xml");

    screenManager.add("GUI/Nifty/StartScreen.xml", "StartMain", new StartScreen(screenManager));
    screenManager.add("GUI/Nifty/OptionScreen.xml", "OptionMain", new OptionScreen(screenManager));

    screenManager.enable("StartMain");

    screenManager.disableDoubleClick();

    flyCam.setEnabled(false);
  }

  @Override
  public void simpleUpdate(float tpf) {
    screenManager.updateEnabled(tpf);
  }

}
