package org.eugenpaul.javaengine.programms.learn_pathfinding.view.view3d;

import java.beans.PropertyChangeEvent;

import org.eugenpaul.javaengine.programms.learn_pathfinding.controller.DefaultController;
import org.eugenpaul.javaengine.programms.learn_pathfinding.model.MoverTyp;
import org.eugenpaul.javaengine.programms.learn_pathfinding.model.PathfindingAlgo;
import org.eugenpaul.javaengine.programms.learn_pathfinding.view.AbstractViewPanel;
import org.eugenpaul.javaengine.programms.learn_pathfinding.view.MapElements;

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
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import com.jme3.system.AppSettings;

import de.lessvoid.nifty.Nifty;

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
  private Node nonCollisionNode;

  private Quad ground = null;

  private WorldItem worldItems[] = null;

  private Nifty nifty;

  protected MapElements clickElement = null;
  private MoverTyp movertyp = MoverTyp.Simple2dMoverSlim;
  private PathfindingAlgo algo = PathfindingAlgo.Lee;

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
    settings.setWidth(1280);
    settings.setHeight(720);
//    settings.setResizable(true);

    this.setShowSettings(false);

    this.setSettings(settings);

    guiController = new GuiController(this);
  }

  @Override
  public void simpleInitApp() {
    flyCam.setEnabled(false);

    collisionNode = new Node("CollisionNode");
    nonCollisionNode = new Node("NonCollisionNode");

    rootNode.attachChild(collisionNode);
    rootNode.attachChild(nonCollisionNode);

    initGround();
    initElems();

//    System.out.println("Rotation = " + getCamera().getRotation());
    // look down
    getCamera().setLocation(new Vector3f(mapSizeX / 2 - 10, 70, mapSizeZ / 2));
//    getCamera().lookAt(new Vector3f(mapSizeX / 2, 0, mapSizeZ / 2), Vector3f.UNIT_Y);
    Quaternion lookDown = new Quaternion();
    lookDown.fromAngleNormalAxis(FastMath.HALF_PI, Vector3f.UNIT_X);
    getCamera().setRotation(getCamera().getRotation().multLocal(lookDown));

//    System.out.println("Rotation = " + getCamera().getRotation());

    initKeys();

    NiftyJmeDisplay niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
    nifty = niftyDisplay.getNifty();
    try {
      nifty.validateXml("GUI/Nifty/HelloJme.xml");
    } catch (Exception e) {
      e.printStackTrace();
    }
    nifty.fromXml("GUI/Nifty/HelloJme.xml", "start", guiController);

    nifty.setIgnoreKeyboardEvents(true);

// attach the nifty display to the gui view port as a processor
    guiViewPort.addProcessor(niftyDisplay);

// disable the fly cam
//    flyCam.setEnabled(false);
//    flyCam.setDragToRotate(true);
//    inputManager.setCursorVisible(true);
  }

  @Override
  public void simpleUpdate(float tpf) {
    printMapData();
  }

  private void initElems() {
    for (int nr = 0; nr < worldItems.length; nr++) {
      float x = nr / mapSizeX + 0.5f;
      float z = nr % mapSizeX + 0.5f;

      worldItems[nr] = new WorldItem(rootNode, new Vector3f(x, 0.3f, z), assetManager);

      worldItems[nr].setElement(MapElements.NOPE);
    }
  }

  private void initGround() {
    ground = new Quad(50, 50);
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
  };

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

    // (Print the results so we see what is going on:)
//    for (int i = 0; i < results.size(); i++) {
//      // (For each “hit”, we know distance, impact point, geometry.)
//      float dist = results.getCollision(i).getDistance();
//      Vector3f pt = results.getCollision(i).getContactPoint();
//      String target = results.getCollision(i).getGeometry().getName();
//      System.out.println("Selection #" + i + ": " + target + " at " + pt + ", " + dist + " WU away.");
//    }
  }

  @Override
  public void modelPropertyChange(PropertyChangeEvent evt) {
    if (evt.getPropertyName().equals(DefaultController.ELEMENT_MAP)) {
      int[][] newStringValue = (int[][]) evt.getNewValue();
      updateMapData(newStringValue);
    }
  }

  private void updateMapData(int grid[][]) {
    for (int x = 0; x < grid.length; x++) {
      for (int y = 0; y < grid[x].length; y++) {
        worldItems[x * mapSizeX + y].setElement(MapElements.fromInt(grid[x][y]));
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

  protected void setMoverTyp(MoverTyp typ) {
    movertyp = typ;
    controller.setMover(movertyp);
  }

  protected void setAlgo(PathfindingAlgo algo) {
    this.algo = algo;
    controller.setPathfindingAlgo(this.algo);
  }

  protected void setAutoPathfinding(boolean autoPathfinding) {
    controller.setAutoPathfinding(autoPathfinding);
  }

  protected void doStep() {
    controller.doPathfindingStep();
  }

  protected void doStart() {
    controller.startPathfinding(20);
  }

  protected void doStop() {
    controller.stopPathfinding();
  }
  
  protected void doReset() {
    controller.resetPathfinding();
  }

}
