package org.eugenpaul.javaengine.core.resource.nifty;

import de.lessvoid.nifty.screen.ScreenController;

public interface NiftyScreenController extends ScreenController {

  /**
   * This function will be called every time by update function. All screen modification must be made in this function.
   * 
   * @param tpf - the time, in seconds, between the last call and the current one.
   */
  public void update(float tpf);

  /**
   * This function will be called once after by pause, if the resource is loaded.
   */
  public void pause();

  /**
   * This function will be called once after by resume, if the resource is loaded.
   */
  public void resume();
}
