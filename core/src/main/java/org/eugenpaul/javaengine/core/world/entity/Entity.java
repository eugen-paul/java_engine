package org.eugenpaul.javaengine.core.world.entity;

import org.eugenpaul.javaengine.core.world.map.Immutable3dPoint;

import lombok.Getter;

/**
 * Abstract entity in the world.
 * 
 * @author Eugen Paul
 *
 */
@Getter
public abstract class Entity {
  protected Immutable3dPoint position = null;
}
