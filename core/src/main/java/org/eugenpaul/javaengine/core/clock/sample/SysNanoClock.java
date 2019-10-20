package org.eugenpaul.javaengine.core.clock.sample;

import org.eugenpaul.javaengine.core.clock.Clock;

/**
 * Return {@link java.lang.System#nanoTime()} as a time.
 * 
 * @author Eugen Paul
 *
 */
public class SysNanoClock implements Clock {

  @Override
  public long time() {
    return System.nanoTime();
  }

}
