package org.eugenpaul.javaengine.core.world.entity.collision;

/**
 * Interface for all Collision Condition.<br>
 * While path finding this class will be used to check if the mover/actor/entity can move on tile/triangle/...<br>
 * 
 * Example:<br>
 * A collision condition could be none or a barrier. The mover can walk throw none {@link Sample2dCollisionCondition}}.
 * 
 * @author Eugen Paul
 *
 */
public interface ICollisionCondition {

  /**
   * This function check if the CollisionCondition {@code a} is the same as this or a part of this.<br>
   * 
   * @param a
   * @return
   */
  public boolean isSame(ICollisionCondition a);
}
