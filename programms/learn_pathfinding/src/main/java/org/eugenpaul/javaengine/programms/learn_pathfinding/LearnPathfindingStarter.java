package org.eugenpaul.javaengine.programms.learn_pathfinding;

import org.eugenpaul.javaengine.programms.learn_pathfinding.controller.DefaultController;
import org.eugenpaul.javaengine.programms.learn_pathfinding.model.World;
import org.eugenpaul.javaengine.programms.learn_pathfinding.view.view2d.MainFrame;

public class LearnPathfindingStarter {

  public static void main(String[] args) {

    DefaultController controller = new DefaultController();

    int maxSizeX = 50;
    int maxSizeY = 50;

    MainFrame mFrame = new MainFrame(controller, maxSizeX, maxSizeY);

    controller.addView(mFrame);

    World model = new World(maxSizeX, maxSizeY);

    controller.setWorld(model);
//    controller.addModel(model);

    mFrame.setVisible(true);
  }
}
