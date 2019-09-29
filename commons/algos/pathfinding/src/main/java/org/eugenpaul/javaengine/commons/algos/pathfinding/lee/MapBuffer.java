package org.eugenpaul.javaengine.commons.algos.pathfinding.lee;

import java.util.Arrays;

import org.eugenpaul.javaengine.core.world.entity.AMotionState;
import org.eugenpaul.javaengine.core.world.entity.Step;
import org.eugenpaul.javaengine.core.world.map.Immutable3dPoint;

import lombok.Getter;

/**
 * The set of nodes across the map.
 * 
 * @author Eugen Paul
 *
 */
public class MapBuffer {

  Node[] nodes = null;

  @Getter
  int sizeX;
  @Getter
  int sizeY;
  @Getter
  int sizeZ;

  /**
   * C'tor
   * 
   * @param x x-Size of map
   * @param y y-Size of map
   * @param z z-Size of map
   */
  public MapBuffer(int x, int y, int z) {
    if (x <= 0 || y <= 0 || z <= 0) {
      throw new IllegalArgumentException("x, y or z <= 0");
    }

    nodes = new Node[x * y * z];
    this.sizeX = x;
    this.sizeY = y;
    this.sizeZ = z;

    reset();
  }

  private int coordToElem(int x, int y, int z) {
    return (sizeY * x) + (sizeZ * y) + z;
  }

  public Node getNode(int x, int y, int z) {
    return nodes[coordToElem(x, y, z)];
  }

  public Node getNode(Immutable3dPoint point) {
    return getNode(point.getX(), point.getY(), point.getZ());
  }

  private Node initNode(int x, int y, int z) {
    Node response = new Node();
    nodes[coordToElem(x, y, z)] = response;
    return response;
  }

  private Node getOrCreateNode(int x, int y, int z) {
    Node checkNode = getNode(x, y, z);
    if (checkNode == null) {
      checkNode = initNode(x, y, z);
    }
    return checkNode;
  }

  public SearchStep addStepToNode(AMotionState state, long cost, Step stepFrom, int x, int y, int z) {
    Node checkNode = getOrCreateNode(x, y, z);

    return checkNode.chechAndAddNode(state, cost, stepFrom, x, y, z);
  }

  public void reset() {
    Arrays.fill(nodes, null);
  }

}
