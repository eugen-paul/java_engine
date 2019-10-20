package org.eugenpaul.javaengine.core.clock.sample;

import org.eugenpaul.javaengine.core.clock.Clock;

import lombok.Getter;
import lombok.Setter;

/**
 * Clock for jUnits tests. The time-value must be sets manualy.
 * 
 * @author Eugen Paul
 *
 */
public class DebugClock implements Clock {

  @Setter
  @Getter
  private long nanos = 0L;

  @Override
  public long time() {
    return nanos;
  }

}
