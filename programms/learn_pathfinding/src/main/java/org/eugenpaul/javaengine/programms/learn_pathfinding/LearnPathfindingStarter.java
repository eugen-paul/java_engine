package org.eugenpaul.javaengine.programms.learn_pathfinding;

import org.eugenpaul.javaengine.core.clock.sample.SysNanoClock;
import org.eugenpaul.javaengine.programms.learn_pathfinding.controller.DefaultController;
import org.eugenpaul.javaengine.programms.learn_pathfinding.model.World;
import org.eugenpaul.javaengine.programms.learn_pathfinding.view.view2d.MainFrame;

/**
 * 2D program to check path finding.
 * 
 * @author Eugen Paul
 *
 */
public class LearnPathfindingStarter {

  public static void main(String[] args) {

    DefaultController controller = new DefaultController(new SysNanoClock());

    int maxSizeX = 50;
    int maxSizeY = 50;

    World model = new World(maxSizeX, maxSizeY);
    controller.setWorld(model);

    MainFrame mFrame = new MainFrame(controller, maxSizeX, maxSizeY);

    controller.addView(mFrame);

    mFrame.setVisible(true);
  }
}
