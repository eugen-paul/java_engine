package org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.view3d;

import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.MapElements;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 * Nodes on map
 * 
 * @author Eugen Paul
 *
 */
public class WorldItem {
  private static final String COLOR_NAME = "Color";

  private Node rootNode = null;
  private MapElements currentElement = MapElements.NOPE;
  private Geometry geom;
  private boolean edited = true;

  /**
   * C'tor
   * 
   * @param rootNode
   * @param position
   * @param assetManager
   */
  public WorldItem(Node rootNode, Vector3f position, AssetManager assetManager) {
    this.rootNode = rootNode;

    Box box1 = new Box(0.25f, 0.25f, 0.25f);
    geom = new Geometry("Box", box1);
    geom.setLocalTranslation(position);
    Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mat1.setColor(COLOR_NAME, ColorRGBA.Gray);
    geom.setMaterial(mat1);
  }

  /**
   * 
   * @param element
   */
  public void setElement(MapElements element) {
    if (element == currentElement) {
      return;
    }

    currentElement = element;
    switch (element) {
    case NOPE:
      break;
    case END:
      geom.getMaterial().setColor(COLOR_NAME, ColorRGBA.Blue);
      break;
    case MUD:
      geom.getMaterial().setColor(COLOR_NAME, ColorRGBA.DarkGray);
      break;
    case WALL:
      geom.getMaterial().setColor(COLOR_NAME, ColorRGBA.Black);
      break;
    case START:
      geom.getMaterial().setColor(COLOR_NAME, ColorRGBA.Green);
      break;
    case WAY:
      geom.getMaterial().setColor(COLOR_NAME, ColorRGBA.Cyan);
      break;
    case STEP_OLD:
      geom.getMaterial().setColor(COLOR_NAME, ColorRGBA.LightGray);
      break;
    case STEP_NEW:
      geom.getMaterial().setColor(COLOR_NAME, ColorRGBA.Orange);
      break;
    case STEP_CHECKPOINT:
      geom.getMaterial().setColor(COLOR_NAME, ColorRGBA.Red);
      break;
    case STEP_TO_CHECK:
      geom.getMaterial().setColor(COLOR_NAME, ColorRGBA.Gray);
      break;
    }

    edited = true;
  }

  /**
   * Update rootNode if necessary
   */
  protected void render() {
    if (edited) {
      if (MapElements.NOPE == currentElement) {
        rootNode.detachChild(geom);
      } else {
        rootNode.attachChild(geom);
      }
      edited = false;
    }
  }
}
