package org.eugenpaul.javaengine.programms.learn_pathfinding.view.view3d;

import org.eugenpaul.javaengine.programms.learn_pathfinding.view.MapElements;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 * 
 * @author Eugen Paul
 *
 */
public class WorldItem {
  private Node rootNode = null;
  private MapElements currentElement = MapElements.NOPE;
  Geometry geom;

  /**
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
    mat1.setColor("Color", ColorRGBA.Gray);
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
      rootNode.detachChild(geom);
      break;
    case END:
      geom.getMaterial().setColor("Color", ColorRGBA.Blue);
      break;
    case MUD:
      geom.getMaterial().setColor("Color", ColorRGBA.DarkGray);
      break;
    case WALL:
      geom.getMaterial().setColor("Color", ColorRGBA.Black);
      break;
    case START:
      geom.getMaterial().setColor("Color", ColorRGBA.Green);
      break;
    case WAY:
      geom.getMaterial().setColor("Color", ColorRGBA.Cyan);
      break;
    case STEP_OLD:
      geom.getMaterial().setColor("Color", ColorRGBA.LightGray);
      break;
    case STEP_NEW:
      geom.getMaterial().setColor("Color", ColorRGBA.Orange);
      break;
    case STEP_CHECKPOINT:
      geom.getMaterial().setColor("Color", ColorRGBA.Red);
      break;
    case STEP_TO_CHECK:
      geom.getMaterial().setColor("Color", ColorRGBA.Gray);
      break;
    }

    if (element != MapElements.NOPE && null == geom.getParent()) {
      rootNode.attachChild(geom);
    }
  }
}
