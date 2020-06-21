package org.eugenpaul.javaengine.core.algos.pathfinding.data;

import java.util.Arrays;

import org.eugenpaul.javaengine.core.world.entity.IMotionState;
import org.eugenpaul.javaengine.core.world.entity.Step;
import org.eugenpaul.javaengine.core.world.map.Immutable3dTilePoint;

import lombok.Getter;

/**
 * The set of nodes across the map.
 * 
 * @author Eugen Paul
 *
 */
public class MapBuffer {

  private Node[] nodes = null;

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

  /**
   * get a node on Position (x,y,z)
   * 
   * @param x x Position of the node
   * @param y y Position of the node
   * @param z z Position of the node
   * @return node
   */
  public Node getNode(int x, int y, int z) {
    return nodes[coordToElem(x, y, z)];
  }

  /**
   * get a node on Position point
   * 
   * @param point position of the node
   * @return node
   */
  public Node getNode(Immutable3dTilePoint point) {
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

  /**
   * Add a state to the node on position "position" if the new state is new or better
   * 
   * @param state    end state of the step
   * @param cost     full cost of the way
   * @param stepFrom step to reach this position
   * @param position position of the last step / way
   * @return not null - new state - new state is better or not in the node<br>
   *         null - new state is worse
   */
  public SearchStep addStepToNode(IMotionState state, long cost, Step stepFrom, Immutable3dTilePoint position) {
    Node checkNode = getOrCreateNode(position.getX(), position.getY(), position.getZ());

    return checkNode.chechAndAddNode(state, cost, stepFrom, position.getX(), position.getY(), position.getZ());
  }

  /**
   * remove all Node
   */
  public void reset() {
    Arrays.fill(nodes, null);
  }

}
