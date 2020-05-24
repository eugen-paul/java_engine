package org.eugenpaul.javaengine.core.world.entity.collision;

import lombok.Getter;

/**
 * ICollisionCondition for Clearance map. Each traversable node of the map has value greater than 0.
 * 
 * @author Eugen Paul
 *
 */
public class Clearance2dCollisionCondition implements ICollisionCondition {
  @Getter
  private int value;

  /**
   * C'tor for Object.
   * 
   * @param value - min clearance value to be able to travel over a map.
   */
  public Clearance2dCollisionCondition(int value) {
    this.value = value;
  }

  @Override
  public boolean isSame(ICollisionCondition a) {
    if (!(a instanceof Clearance2dCollisionCondition)) {
      return false;
    }

    Clearance2dCollisionCondition testVar = (Clearance2dCollisionCondition) a;
    return testVar.getValue() <= value;
  }

  /**
   * like {@link #isSame(ICollisionCondition)}.
   * 
   * @param a
   * @return
   */
  public boolean isSame(int a) {
    return a <= value;
  }
}
