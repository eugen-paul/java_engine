package org.eugenpaul.javaengine.core.world.entity;

import java.util.List;

import org.eugenpaul.javaengine.core.world.entity.collision.ICollisionCondition;

/**
 * Modify step cost
 * 
 * @author Eugen Paul
 *
 */
public interface StepCostModifier {
  public long getDynamicMultiCost(long cost, List<List<ICollisionCondition>> collisions);

  public long getDynamicSingleCost(long cost, List<ICollisionCondition> collisions);
}
