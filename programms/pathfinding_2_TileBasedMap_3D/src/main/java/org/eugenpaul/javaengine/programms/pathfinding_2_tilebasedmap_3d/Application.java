package org.eugenpaul.javaengine.programms.pathfinding_2_tilebasedmap_3d;

import java.util.Properties;

import org.eugenpaul.javaengine.core.resource.IResource;
import org.eugenpaul.javaengine.core.resource.ResourceManager;
import org.eugenpaul.javaengine.core.resource.nifty.NiftyXmlScreen;
import org.eugenpaul.javaengine.programms.pathfinding_2_tilebasedmap_3d.controller.MainController;
import org.eugenpaul.javaengine.programms.pathfinding_2_tilebasedmap_3d.screens.OptionScreen;
import org.eugenpaul.javaengine.programms.pathfinding_2_tilebasedmap_3d.screens.StartScreen;

import com.jme3.app.SimpleApplication;
import com.jme3.niftygui.NiftyJmeDisplay;

import de.lessvoid.nifty.Nifty;

public class Application extends SimpleApplication {

  private MainController controller = null;
  private ResourceManager<IResource> screenManager = null;

  public Application(MainController controller) {
    this.controller = controller;
  }

  @Override
  public void simpleInitApp() {
    NiftyJmeDisplay niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
    Nifty nifty = niftyDisplay.getNifty();

    screenManager = new ResourceManager<>();

    IResource startScreen = new NiftyXmlScreen("GUI/Nifty/StartScreen.xml", "StartMain", nifty, new StartScreen(screenManager));
    IResource optionScreen = new NiftyXmlScreen("GUI/Nifty/OptionScreen.xml", "OptionMain", nifty, new OptionScreen(screenManager));

    screenManager.add("Start", startScreen);
    screenManager.add("Option", optionScreen);
    
    startScreen.init();
    screenManager.enable("Start");

    // disable double clicks
    Properties data = nifty.getGlobalProperties();
    if (null == data) {
      data = new Properties();
      nifty.setGlobalProperties(data);
    }
    data.setProperty("MULTI_CLICK_TIME", "0");

    guiViewPort.addProcessor(niftyDisplay);

    flyCam.setEnabled(false);

//    startScreen.load();
//    startScreen.firstShow();
  }

  @Override
  public void simpleUpdate(float tpf) {
    screenManager.updateEnabled(tpf);
  }

}
