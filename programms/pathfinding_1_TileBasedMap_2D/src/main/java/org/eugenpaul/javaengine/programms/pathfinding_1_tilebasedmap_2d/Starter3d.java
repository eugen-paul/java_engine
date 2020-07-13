package org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d;

import org.eugenpaul.javaengine.core.clock.sample.SysNanoClock;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.controller.DefaultController;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.World;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.view2d.AlgoFactory;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.view3d.MainApplication;

/**
 * 3D program to check path finding.
 * 
 * @author Eugen Paul
 *
 */
public class Starter3d {

  public static void main(String[] args) {

    DefaultController controller = new DefaultController(new SysNanoClock());

    int maxSizeX = 50;
    int maxSizeY = 50;

    World model = new World();
    AlgoFactory factory = controller.getCurrent2dFactory();
    model.init(factory.getMap(), factory.getDefaultMapMover());
    
    controller.setWorld(model);

    MainApplication application = new MainApplication(controller, maxSizeX, maxSizeY);

//    controller.addView(application);
    
    application.start();
  }
}
