package org.eugenpaul.javaengine.core.world.entity.vectormover;

import java.util.List;

import org.eugenpaul.javaengine.core.world.entity.Step;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MovingParams {
  private IMovingVector vector;
  private List<Step> steps;
}
