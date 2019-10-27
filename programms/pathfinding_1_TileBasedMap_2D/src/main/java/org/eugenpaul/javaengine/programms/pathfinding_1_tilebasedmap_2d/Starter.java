package org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d;

import org.eugenpaul.javaengine.core.clock.sample.SysNanoClock;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.controller.DefaultController;
import org.eugenpaul.javaengine.programms.pathfinding_1_tilebasedmap_2d.model.World;
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

    int maxSizeX = 50;
    int maxSizeY = 50;

    World model = new World(maxSizeX, maxSizeY);
    controller.setWorld(model);

    MainFrame mFrame = new MainFrame(controller, maxSizeX, maxSizeY);

    controller.addView(mFrame);
    model.fireCurrentMap();

    mFrame.setVisible(true);
  }
}
