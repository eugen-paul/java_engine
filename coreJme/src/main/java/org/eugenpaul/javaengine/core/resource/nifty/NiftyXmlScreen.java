package org.eugenpaul.javaengine.core.resource.nifty;

import org.eugenpaul.javaengine.core.resource.IResource;

import de.lessvoid.nifty.Nifty;

/**
 * 
 * @author Eugen Paul
 *
 */
public class NiftyXmlScreen implements IResource {

  private String xmlPath = null;
  private String screenName = null;
  private NiftyScreenController controller = null;
  private Nifty nifty = null;
  private boolean enabled = false;

  /**
   * Constructor
   * 
   * @param path       - path to xml
   * @param screenName - unique name of main screen
   * @param nifty      - main nifty object
   */
  public NiftyXmlScreen(String path, String screenName, Nifty nifty) {
    this(path, screenName, nifty, null);
  }

  /**
   * Constructor
   * 
   * @param path       - path to xml
   * @param screenName - unique name of main screen
   * @param nifty      - main nifty object
   * @param controller - controller for this screen
   */
  public NiftyXmlScreen(String path, String screenName, Nifty nifty, NiftyScreenController controller) {
    this.xmlPath = path;
    this.screenName = screenName;
    this.nifty = nifty;
    this.controller = controller;
  }

  @Override
  public void init() {
    // Nothing to do
  }

  @Override
  public void load() {
    if (null != controller) {
      nifty.registerScreenController(controller);
    }
    nifty.addXml(xmlPath);
    enabled = true;
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
    if (!enabled) {
      // The screen are not enabled
      return;
    }
    if (null != controller) {
      nifty.unregisterScreenController(controller);
    }
    nifty.removeScreen(screenName);
    enabled = false;
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

  @Override
  public void destroy() {
    unload();
  }

}
