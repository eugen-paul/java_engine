package org.eugenpaul.javaengine.core.resource.nifty;

import java.util.Properties;

import org.eugenpaul.javaengine.core.resource.ResourceManager;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;

import de.lessvoid.nifty.Nifty;
import lombok.Getter;

/**
 * Manager for Nifty-XML-Resources.
 * 
 * @author Eugen Paul
 *
 */
public class NiftyXmlManager extends ResourceManager<NiftyXmlScreen> {

  @Getter
  private NiftyJmeDisplay niftyDisplay = null;
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

    niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(assetManager, inputManager, audioRenderer, viewport);
    nifty = niftyDisplay.getNifty();
    viewport.addProcessor(niftyDisplay);
  }

  /**
   * Disable double click on all Nifty elements.
   */
  public void disableDoubleClick() {
    Properties data = nifty.getGlobalProperties();
    if (null == data) {
      data = new Properties();
      nifty.setGlobalProperties(data);
    }
    data.setProperty("MULTI_CLICK_TIME", "0");
  }

  /**
   * Load separate XML-Style file.
   * 
   * @param styleFile - path to xml
   * @return true - success
   */
  public boolean loadStyle(String styleFile) {
    nifty.loadStyleFile(styleFile);
    return true;
  }

  /**
   * Load separate XML-Control file.
   * 
   * @param controlFile - path to xml
   * @return true - success
   */
  public boolean loadControll(String controlFile) {
    nifty.loadControlFile(controlFile);
    return true;
  }

  /**
   * Add a new screen from xml-file to manager. The new screen will be initialized.
   * 
   * @param xmlPath    - path to xml
   * @param screenName - unique screenname
   * @param controller - screencontroller
   * @return true - success
   */
  public boolean add(String xmlPath, String screenName, NiftyScreenController controller) {
    NiftyXmlScreen screen = new NiftyXmlScreen(xmlPath, screenName, nifty, controller);

    screen.init();

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
   * Add a new screen from xml-file to manager.
   * 
   * @param xmlPath    - path to xml
   * @param screenName - unique screenname
   */
  public boolean add(String xmlPath, String screenName) {
    return add(xmlPath, screenName, null);
  }
}
