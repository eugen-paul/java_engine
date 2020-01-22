package org.eugenpaul.javaengine.core.resource.nifty;

import org.eugenpaul.javaengine.core.resource.IResource;

import de.lessvoid.nifty.Nifty;

public class NiftyXmlScreen implements IResource {

  private String xmlPath = null;
  private String screenName = null;
  private NiftyScreenController controller = null;
  private Nifty nifty = null;

  /**
   * 
   * @param path       - path to xml
   * @param screenName - unique name of main screen
   * @param nifty      - main nifty object
   */
  public NiftyXmlScreen(String path, String screenName, Nifty nifty) {
    this.xmlPath = path;
    this.screenName = screenName;
    this.nifty = nifty;
  }

  /**
   * 
   * @param path       - path to xml
   * @param screenName - unique name of main screen
   * @param nifty      - main nifty object
   * @param controller - controller for this screen
   */
  public NiftyXmlScreen(String path, String screenName, Nifty nifty, NiftyScreenController controller) {
    this(path, screenName, nifty);
    this.controller = controller;
  }

  @Override
  public void init() {
    if (null != controller) {
      nifty.registerScreenController(controller);
    }
    nifty.addXml(xmlPath);
  }

  @Override
  public void load() {
    // Nothing to do
  }

  @Override
  public void firstShow() {
    nifty.gotoScreen(screenName);
  }

  @Override
  public void update(float tpf) {
    if (null != controller) {
      controller.update(tpf);
    }
  }

  @Override
  public void unload() {
    // Nothing to do
    if (null != controller) {
      nifty.unregisterScreenController(controller);
    }
    nifty.removeScreen(screenName);
  }

  @Override
  public void pause() {
    if (null != controller) {
      controller.pause();
    }
  }

  @Override
  public void resume() {
    if (null != controller) {
      controller.resume();
    }
  }

}
