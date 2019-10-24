package org.eugenpaul.javaengine.programms.learn_pathfinding.model;

import java.util.List;

import org.eugenpaul.javaengine.core.world.entity.StepCostModifier;
import org.eugenpaul.javaengine.core.world.entity.collision.ICollisionCondition;

/**
 * Class to compute step cost for dynamic-mover.
 * 
 * @author Eugen Paul
 *
 */
public class DynamicStepModifier implements StepCostModifier {
  @Override
  public long getDynamicSingleCost(long cost, List<ICollisionCondition> collisions) {
    int anzahl = 0;
    double wert = 0;
    for (ICollisionCondition cond : collisions) {
      if (cond instanceof WorldElements) {
        WorldElements worldCond = (WorldElements) cond;
        anzahl++;
        wert += worldCond.getSpeedModifier();
      }
    }
    if (anzahl != 0) {
      return (long) (wert / anzahl) * cost;
    } else {
      return cost;
    }
  }

  @Override
  public long getDynamicMultiCost(long cost, List<List<ICollisionCondition>> collisions) {
    int anzahl = 0;
    double wert = 0;
    for (List<ICollisionCondition> collisionList : collisions) {
      for (ICollisionCondition cond : collisionList) {
        if (cond instanceof WorldElements) {
          WorldElements worldCond = (WorldElements) cond;
          anzahl++;
          wert += worldCond.getSpeedModifier();
        }
      }
    }

    if (anzahl != 0) {
      return (long) ((wert / anzahl) * cost);
    } else {
      return cost;
    }
  }
}
