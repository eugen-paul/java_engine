package org.eugenpaul.javaengine.core.resource.nifty;

import org.eugenpaul.javaengine.core.resource.ResourceManager;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;

import de.lessvoid.nifty.Nifty;

public class NiftyXmlManager extends ResourceManager<NiftyXmlScreen> {

  private Nifty nifty = null;

  /**
   * 
   * @param assetManager  jME AssetManager
   * @param inputManager  jME InputManager
   * @param audioRenderer jME AudioRenderer
   * @param viewport      Viewport to use
   */
  public NiftyXmlManager(final AssetManager assetManager, final InputManager inputManager, final AudioRenderer audioRenderer, final ViewPort viewport) {
    super();

    NiftyJmeDisplay niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(assetManager, inputManager, audioRenderer, viewport);
    nifty = niftyDisplay.getNifty();
  }

  /**
   * add a new screen from xml-file to manager.
   * 
   * @param xmlPath    - path to xml
   * @param screenName - unique screenname
   * @param controller - screencontroller
   */
  public boolean add(String xmlPath, String screenName, NiftyScreenController controller) {
    NiftyXmlScreen screen = new NiftyXmlScreen(xmlPath, screenName, nifty, controller);

    try {
      nifty.validateXml(xmlPath);
    } catch (Exception e) {
      e.printStackTrace(); // TODO
      return false;
    }

    add(screenName, screen);
    return true;
  }

  /**
   * add a new screen from xml-file to manager.
   * 
   * @param xmlPath    - path to xml
   * @param screenName - unique screenname
   */
  public boolean add(String xmlPath, String screenName) {
    NiftyXmlScreen screen = new NiftyXmlScreen(xmlPath, screenName, nifty);

    try {
      nifty.validateXml(xmlPath);
    } catch (Exception e) {
      e.printStackTrace(); // TODO
      return false;
    }

    add(screenName, screen);
    return true;
  }
}
