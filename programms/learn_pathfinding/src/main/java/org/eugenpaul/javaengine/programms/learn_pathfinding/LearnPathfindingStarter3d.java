package org.eugenpaul.javaengine.programms.learn_pathfinding;

import org.eugenpaul.javaengine.programms.learn_pathfinding.controller.DefaultController;
import org.eugenpaul.javaengine.programms.learn_pathfinding.model.World;
import org.eugenpaul.javaengine.programms.learn_pathfinding.view.view3d.MainApplication;

public class LearnPathfindingStarter3d {

  public static void main(String[] args) {

    DefaultController controller = new DefaultController();

    int maxSizeX = 50;
    int maxSizeY = 50;

    World model = new World(maxSizeX, maxSizeY);
    controller.setWorld(model);

    MainApplication application = new MainApplication(controller, maxSizeX, maxSizeY);

    controller.addView(application);

    application.start();
  }
}
