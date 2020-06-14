package org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.view3d;

import java.beans.PropertyChangeEvent;

import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.controller.DefaultController;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.GridElement;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.AbstractViewPanel;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.MapElements;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import com.jme3.system.AppSettings;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.style.BaseStyles;

/**
 * Very simple application to test / learn / show path finding.
 * 
 * @author Eugen Paul
 *
 */
public class MainApplication extends SimpleApplication implements AbstractViewPanel {

  private DefaultController controller;

  private int mapSizeX;
  private int mapSizeZ;

  private Node collisionNode;

  private WorldItem[] worldItems = null;

  protected MapElements clickElement = null;

  private GuiController guiController = null;

  /**
   * C'tor
   * 
   * @param controller - controller for MCV
   * @param x          - x size of the map
   * @param y          - y size of the map
   */
  public MainApplication(DefaultController controller, int x, int y) {
    this.controller = controller;

    mapSizeX = x;
    mapSizeZ = y;

    worldItems = new WorldItem[x * y];

    AppSettings settings = new AppSettings(true);
    settings.setWidth(900);
    settings.setHeight(720);
//    settings.setResizable(true);

    this.setShowSettings(false);

    this.setSettings(settings);

    guiController = new GuiController(this.controller, this);
  }

  @Override
  public void simpleInitApp() {

    GuiGlobals.initialize(this);
    BaseStyles.loadGlassStyle();
    GuiGlobals.getInstance().getStyles().setDefaultStyle("glass");

    guiController.init();

//    BitmapText hudText = new BitmapText(guiFont, false);
//    hudText.setSize(guiFont.getCharSet().getRenderedSize());
//    hudText.setColor(ColorRGBA.White);
//    hudText.setText("You can write any string here and here and here and here and here and here and here");
//    hudText.setLocalTranslation(300, 500, 0);
//    hudText.setBox(new Rectangle(0, 0, 200, 50));
//    hudText.setLineWrapMode(LineWrapMode.Clip);
//    guiNode.attachChild(hudText);

// disable the fly cam
//    flyCam.setDragToRotate(true);
//    inputManager.setCursorVisible(true);
    flyCam.setEnabled(false);

    collisionNode = new Node("CollisionNode");
    Node nonCollisionNode = new Node("NonCollisionNode");

    rootNode.attachChild(collisionNode);
    rootNode.attachChild(nonCollisionNode);

    initGround();
    initElems();

    // look down
    int camPosX = mapSizeX / 2 - 5;
    int camPosZ = mapSizeZ / 2;

    getCamera().setLocation(new Vector3f(camPosX, 70, camPosZ));
    Quaternion lookDown = new Quaternion();
    lookDown.fromAngleNormalAxis(FastMath.HALF_PI, Vector3f.UNIT_X);
    getCamera().setRotation(getCamera().getRotation().multLocal(lookDown));

    initKeys();

  }

  @Override
  public void simpleUpdate(float tpf) {
    printMapData();
    guiController.update();
  }

  @Override
  public void destroy() {
    super.destroy();
    controller.stopPathfinding();
  }

  private void initElems() {
    for (int nr = 0; nr < worldItems.length; nr++) {
      int x = nr / mapSizeX;
      int z = nr % mapSizeX;

      worldItems[nr] = new WorldItem(rootNode, new Vector3f(x + 0.5f, 0.3f, z + 0.5f), assetManager);

      worldItems[nr].setElement(MapElements.NOPE);
    }
  }

  private void initGround() {
    Quad ground = new Quad(50, 50);
    Geometry geom = new Geometry("ground", ground);
    Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mat.setColor("Color", ColorRGBA.Gray);
    geom.setMaterial(mat);

    geom.setLocalTranslation(0, 0, mapSizeZ);

    Quaternion lookDown = new Quaternion();
    lookDown.fromAngleNormalAxis(-FastMath.HALF_PI, Vector3f.UNIT_X);
    geom.setLocalRotation(geom.getLocalRotation().multLocal(lookDown));

    collisionNode.attachChild(geom);
  }

  private void initKeys() {
    inputManager.addMapping("pick", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
    inputManager.addListener(analogListener, "pick");
  }

  private AnalogListener analogListener = new AnalogListener() {
    @Override
    public void onAnalog(String name, float value, float tpf) {
      if (name.equals("pick")) {
        mouseClickAction();
      }
    }

    private void mouseClickAction() {
      if (null != clickElement) {
        Vector2f coord = getMouseTarget();
        if (null != coord) {
          controller.setPointOnMap((int) coord.getX(), (int) coord.getY(), clickElement);
        }
      }
    }

    private Vector2f getMouseTarget() {
      // Reset results list.
      CollisionResults results = new CollisionResults();
      // Convert screen click to 3d position
      Vector2f click2d = inputManager.getCursorPosition();
      Vector3f click3d = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
      Vector3f dir = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();
      // Aim the ray from the clicked spot forwards.
      Ray ray = new Ray(click3d, dir);
      // Collect intersections between ray and all nodes in results list.
      collisionNode.collideWith(ray, results);

      CollisionResult coll = results.getClosestCollision();
      if (null == coll) {
        return null;
      } else {
        Vector3f pt = coll.getContactPoint();
        return new Vector2f((int) pt.getX(), (int) pt.getZ());
      }

    }
  };

  @Override
  public void modelPropertyChange(PropertyChangeEvent evt) {
    if (evt.getPropertyName().equals(DefaultController.ELEMENT_MAP)) {
      GridElement[][] newStringValue = (GridElement[][]) evt.getNewValue();
      updateMapData(newStringValue);
    }
  }

  private void updateMapData(GridElement[][] grid) {
    for (int x = 0; x < grid.length; x++) {
      for (int y = 0; y < grid[x].length; y++) {
        worldItems[x * mapSizeX + y].setElement(grid[x][y].getMapElement());
      }
    }
  }

  private void printMapData() {
    for (int x = 0; x < mapSizeX; x++) {
      for (int y = 0; y < mapSizeZ; y++) {
        worldItems[x * mapSizeX + y].render();
      }
    }
  }

}
