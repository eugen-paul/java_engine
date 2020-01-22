package org.eugenpaul.javaengine.core.resource;

/**
 * Resource that can be init/load/...
 * 
 * @author Eugen Paul
 *
 */
public interface IResource {
  /**
   * This function will be called once (after Object create).
   */
  public void init();

  /**
   * This function will be called every time while loading stage.
   */
  public void load();

  /**
   * This function will be called once after every load by first show.
   */
  public void firstShow();

  /**
   * This function will be called every time by update function.
   * 
   * @param tpf - the time, in seconds, between the last call and the current one.
   */
  public void update(float tpf);

  /**
   * This function will be called once after every unload.
   */
  public void unload();

  /**
   * This function will be called once after by pause, if the resource is loaded.
   */
  public void pause();

  /**
   * This function will be called once after by resume, if the resource is loaded.
   */
  public void resume();
}
