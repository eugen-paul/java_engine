package org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d;

import org.eugenpaul.javaengine.core.clock.sample.SysNanoClock;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.controller.DefaultController;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.World;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.view2d.AlgoFactory;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.view.view2d.MainFrame;

/**
 * 2D program to check path finding.
 * 
 * @author Eugen Paul
 *
 */
public class Starter {

  public static void main(String[] args) {

    DefaultController controller = new DefaultController(new SysNanoClock());

    World model = new World();
    AlgoFactory factory = controller.getCurrent2dFactory();
    model.init(factory.getMap(), factory.getDefaultMapMover());

    controller.setWorld(model);

    MainFrame mFrame = new MainFrame(controller);

    controller.addView(mFrame);

    mFrame.setVisible(true);
  }
}
