package org.eugenpaul.javaengine.core.clock;

/**
 * Interface to get current program/game time. <br>
 * It is necessary for better time control.
 * 
 * @author Eugen Paul
 *
 */
public interface Clock {

  /**
   * get program time in nanoseconds
   * 
   * @return
   */
  public long time();
}
