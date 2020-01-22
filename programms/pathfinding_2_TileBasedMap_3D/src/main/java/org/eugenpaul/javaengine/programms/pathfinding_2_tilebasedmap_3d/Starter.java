package org.eugenpaul.javaengine.programms.pathfinding_2_tilebasedmap_3d;

import org.eugenpaul.javaengine.programms.pathfinding_2_tilebasedmap_3d.controller.MainController;

/**
 * Main class to start the program.
 * 
 * @author Eugen Paul
 *
 */
public class Starter {

  public static void main(String[] args) {
    MainController mc = new MainController();
    Application appl = new Application(mc);
    appl.start();
  }
}
